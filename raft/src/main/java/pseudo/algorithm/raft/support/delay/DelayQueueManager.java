package pseudo.algorithm.raft.support.delay;

import cn.hutool.core.thread.ExecutorBuilder;
import com.dfocus.fam.framework.common.enums.YesNoEnum;
import com.dfocus.fm.common.entity.news.SummaryMessageSend;
import com.dfocus.fm.common.enums.news.SummaryMessageType;
import com.dfocus.fm.common.enums.pm.SendStatus;
import com.dfocus.fm.integration.share.EmployeeFeign;
import com.dfocus.fm.integration.workflow.WorkflowFeign;
import com.dfocus.fm.service.atom.EmployeeExtService;
import com.dfocus.fm.service.atom.cmb.CMBNoticeService;
import com.dfocus.fm.service.atom.common.CurrentService;
import com.dfocus.fm.service.atom.news.SummaryMessageSendService;
import com.dfocus.share.facade.model.employee.EmployeeInfoModel;
import com.dfocus.workflow.facade.enums.WorkflowProcInstType;
import com.dfocus.workflow.facade.model.req.WorkflowProcInstListReq;
import com.dfocus.workflow.facade.model.rsp.WorkflowProcInstRsp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.DelayQueue;
import java.util.stream.Collectors;

/**
 * 延迟队列的消费者定义类
 *
 * @author molax
 * @date 2021-02-04 18:16
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DelayQueueManager implements CommandLineRunner {

	private final DelayQueue<DelayMessage> delayQueue = new DelayQueue<>();

	private final RaftComposeService raftComposeService;

	/**
	 * 加入到延时队列中
	 * @param message
	 */
	public void put(DelayMessage message) {
		delayQueue.put(message);
	}

	/**
	 * 加入到延时队列中
	 * @param send
	 */
	public void putSummaryMessage(SummaryMessageSend send) {
		if (Objects.nonNull(send)) {
			DelayMessage message = new DelayMessage<SummaryMessageSend>();
			message.setData(send);
			message.setMessageType(SummaryMessageType.APPROVE_EMAIL);
			message.setTtl(send.getSendTime().atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli());
			put(message);
		}
	}

	/**
	 * 取消延时任务
	 * @param message
	 * @return
	 */
	public boolean remove(DelayMessage message) {
		return delayQueue.remove(message);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("summary email queue init...");
		ExecutorBuilder.create().setCorePoolSize(1).setMaxPoolSize(1).setKeepAliveTime(0).build()
				.execute(new Thread(this::consumerThread));
		List<SummaryMessageSend> sends = summaryMessageSendService.unSendSummaryEmail();
		sends.forEach(v -> {
			putSummaryMessage(v);
		});
	}

	/**
	 * 延时任务执行线程
	 */
	private void consumerThread() {
		while (true) {
			try {
				// 从延迟队列的头部获取已经过期的消息
				DelayMessage message = delayQueue.take();
				currentService.setCurrentUser(1L, null);
				sendMessage(message);
			}
			catch (InterruptedException e) {
				break;
			}
			finally {
				currentService.removeCurrentUser();
			}
		}
	}

	/**
	 * 内部执行延时任务
	 * @param message
	 */
	private void sendMessage(DelayMessage message) {
		log.info("Consumer received message: {}", message.getMessageType());
		// 发送汇总邮件
		if (Objects.equals(message.getMessageType(), SummaryMessageType.APPROVE_EMAIL)) {
			log.error(String.format("begin handle delay message %s", message.getData()));
			SummaryMessageSend send = (SummaryMessageSend) message.getData();
			if (employeeExtService.checkSend(send, message.getTtl())) {
				// 查询待发送数据
				List<WorkflowProcInstRsp> data = workflowFeign
						.findAll(new WorkflowProcInstListReq().setType(WorkflowProcInstType.WAIT_APPROVE)
								.setQueryAllPartition(YesNoEnum.YES).setEmployeeId(send.getEmployeeId()));
				if (CollectionUtils.isNotEmpty(data)) {
					Map<String, Object> params = new HashMap<>(4);
					EmployeeInfoModel employeeInfo = employeeFeign.detail(send.getEmployeeId());
					// 招行用户发送
					if (Objects.nonNull(employeeInfo) && StringUtils.isNotBlank(employeeInfo.getYstId())) {
						params.put("destAddress", employeeInfo.getYstId());
						params.put("subject", String.format("提醒：您有%s个待办任务", data.size()));
						params.put("body", generateBodyTable(data));
						cmbNoticeService.sendEmail(params);
					}
					// 其他用户发送
				}
			}
			send.setSendStatus(SendStatus.SENT);
			summaryMessageSendService.updateById(send);
		}
	}

	private String generateBodyTable(List<WorkflowProcInstRsp> data) {
		if (data.isEmpty()) {
			return "";
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append(
					"<table border='1' cellspacing='0' cellpadding='0'><tr><td>编号</td><td>审批标题</td><td>发起人</td></tr>");
			data.forEach(v -> {
				sb.append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>", v.getCode(), String
						.format("<a href='%s'>【%s】%s</a>", v.getSponsorEmailJumpUrl(), v.getWfTypeName(), v.getTitle()),
						v.getApproverName()));
			});
			sb.append("</table>");
			return sb.toString();
		}
	}

	public List<DelayMessage> allData() {
		return delayQueue.stream().collect(Collectors.toList());
	}

}
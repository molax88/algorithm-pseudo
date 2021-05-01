package pseudo.algorithm.raft.support.delay;

import com.dfocus.fm.common.enums.news.SummaryMessageType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pseudo.algorithm.raft.enums.HandleType;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟消息实体类
 *
 * @author molax
 * @date 2021-02-04 16:57
 */
@Data
@NoArgsConstructor
public class DelayMessage<T> implements Delayed {

	@ApiModelProperty(value = "业务数据")
	private T data;

	@ApiModelProperty(value = "延迟处理类型")
	private HandleType handleType;

	@ApiModelProperty(value = "延迟消息到期时间（过期时间）")
	private long ttl;

	/**
	 * 构造函数
	 * @param data 业务数据
	 * @param handleType 消息类型
	 * @param ttl 延迟时间，单位毫秒
	 */
	public DelayMessage(T data, HandleType handleType, long ttl) {
		setData(data);
		setHandleType(handleType);
		setTtl(ttl);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		// 计算该延迟消息距离过期还剩多少时间
		long remaining = ttl - System.currentTimeMillis();
		return unit.convert(remaining, TimeUnit.MILLISECONDS);
	}

	@Override
	public int compareTo(Delayed o) {
		// 比较、排序：对消息的延时大小进行排序，用于实现将延时时间最小的消息放到队列头部
		return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
	}

}
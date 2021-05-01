package pseudo.algorithm.raft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pseudo.algorithm.raft.domain.entity.LogEntity;
import pseudo.algorithm.raft.domain.request.LogReq;
import pseudo.algorithm.raft.domain.request.VoteReq;
import pseudo.algorithm.raft.domain.response.LogRsp;
import pseudo.algorithm.raft.domain.response.RaftLogRsp;
import pseudo.algorithm.raft.domain.response.RaftVoteRsp;
import pseudo.algorithm.raft.enums.RaftRole;
import pseudo.algorithm.raft.service.RaftComposeService;
import pseudo.algorithm.raft.service.RaftCoreService;
import pseudo.algorithm.raft.service.RaftNodeService;
import pseudo.algorithm.raft.support.RaftNode;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * ┌─┐       ┌─┐
 * ┌──┘ ┴───────┘ ┴──┐
 * │                 │
 * │       ───       │
 * │  ─┬┘       └┬─  │
 * │                 │
 * │       ─┴─       │
 * │                 │
 * └───┐         ┌───┘
 * │         │
 * │         │
 * │         │
 * │         └──────────────┐
 * │                        │
 * │                        ├─┐
 * │                        ┌─┘
 * │                        │
 * └─┐  ┐  ┌───────┬──┐  ┌──┘
 * │ ─┤ ─┤       │ ─┤ ─┤
 * └──┴──┘       └──┴──┘
 * 神兽保佑
 * 代码无BUG!
 *
 * @author molax
 * @date 2021/4/26 11:41
 */
@Service
@RequiredArgsConstructor
public class RaftComposeServiceImpl implements RaftComposeService {

    private final RaftNodeService nodeService;

    private final RaftCoreService coreService;

    @Override
    public RaftVoteRsp election(VoteReq voteReq) {
        return coreService.election(voteReq);
    }

    @Override
    public RaftLogRsp appendLog(LogReq logReq) {
        return coreService.appendLog(logReq);
    }

    @Override
    public RaftRole getRaftRole() {
        return nodeService.getRaftRole();
    }

    @Override
    public void addNode(RaftNode node) {
        nodeService.addNode(node);
    }

    @Override
    public Mono<LogRsp> handleLog(String logData) {
        if(Objects.equals(nodeService.getRaftRole(), RaftRole.LEADER)) {
            LogEntity entity = LogEntity.of(logData);
            coreService.applyLog(entity);
            return Mono.create(sink -> sink.success(new LogRsp().setResult(true).setLogIndex(entity.getIndex())));
        }
        // 转发LEADER处理
        return null;
    }
}

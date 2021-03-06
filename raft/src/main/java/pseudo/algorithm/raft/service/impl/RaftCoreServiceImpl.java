package pseudo.algorithm.raft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pseudo.algorithm.raft.domain.entity.LogEntity;
import pseudo.algorithm.raft.domain.request.LogReq;
import pseudo.algorithm.raft.domain.request.VoteReq;
import pseudo.algorithm.raft.domain.response.RaftLogRsp;
import pseudo.algorithm.raft.domain.response.RaftVoteRsp;
import pseudo.algorithm.raft.service.RaftCoreService;
import pseudo.algorithm.raft.support.RaftNode;

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
 * @date 2021/4/25 15:10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RaftCoreServiceImpl implements RaftCoreService {

    private final RaftNode node;

    @Override
    public RaftVoteRsp election(VoteReq voteReq) {
        return null;
    }

    @Override
    public RaftLogRsp appendLog(LogReq logReq) {
        return null;
    }

    @Override
    public LogEntity applyLog(LogEntity entity) {
        entity.setPreIndex(node.getLastIndex()).setIndex(node.getLastIndex()+1);
        // TODO: 2021/4/27  持久化日志
        log.info(entity.toString());
        return entity;
    }
}

package pseudo.algorithm.raft.service;

import pseudo.algorithm.raft.domain.entity.LogEntity;
import pseudo.algorithm.raft.domain.request.LogReq;
import pseudo.algorithm.raft.domain.request.VoteReq;
import pseudo.algorithm.raft.domain.response.RaftLogRsp;
import pseudo.algorithm.raft.domain.response.RaftVoteRsp;

/**
 * @author molax
 * @date 2021/4/21 17:47
 */
public interface RaftCoreService {

    /**
     * 选举请求
     * @param voteReq
     * @return
     */
    RaftVoteRsp election(VoteReq voteReq);

    /**
     * 日志复制
     * @param logReq
     * @return
     */
    RaftLogRsp appendLog(LogReq logReq);

    /** 保存日志
     * @param entity
     * @return
     */
    LogEntity applyLog(LogEntity entity);
}

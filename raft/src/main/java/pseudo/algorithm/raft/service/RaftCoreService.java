package pseudo.algorithm.raft.service;

import pseudo.algorithm.raft.domain.request.LogReq;
import pseudo.algorithm.raft.domain.request.VoteReq;
import pseudo.algorithm.raft.domain.response.LogRsp;
import pseudo.algorithm.raft.domain.response.VoteRsp;
import pseudo.algorithm.raft.enums.RaftRole;

/**
 * @author molax
 * @date 2021/4/21 17:47
 */
public interface RaftCoreService {

    /**
     * 当前节点角色
     * @return
     */
    RaftRole getRaftRole();

    /**
     * 选举请求
     * @param voteReq
     * @return
     */
    VoteRsp election(VoteReq voteReq);

    /**
     * 日志复制
     * @param logReq
     * @return
     */
    LogRsp appendLog(LogReq logReq);
}

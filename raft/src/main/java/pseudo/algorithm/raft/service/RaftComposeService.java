package pseudo.algorithm.raft.service;

import pseudo.algorithm.raft.domain.request.LogReq;
import pseudo.algorithm.raft.domain.request.VoteReq;
import pseudo.algorithm.raft.domain.response.LogRsp;
import pseudo.algorithm.raft.domain.response.RaftLogRsp;
import pseudo.algorithm.raft.domain.response.RaftVoteRsp;
import pseudo.algorithm.raft.enums.RaftRole;
import pseudo.algorithm.raft.support.RaftNode;
import reactor.core.publisher.Mono;

/**
 * @author molax
 * @date 2021/4/26 11:39
 */
public interface RaftComposeService {
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

    /**
     * 当前节点角色
     * @return
     */
    RaftRole getRaftRole();

    /**
     * 节点接入
     */
    void addNode(RaftNode node);

    /**
     * 接收&处理日志
     */
     Mono<LogRsp> handleLog(String logData);
}

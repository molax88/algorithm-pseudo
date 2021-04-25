package pseudo.algorithm.raft.service.impl;

import pseudo.algorithm.raft.domain.request.LogReq;
import pseudo.algorithm.raft.domain.request.VoteReq;
import pseudo.algorithm.raft.domain.response.LogRsp;
import pseudo.algorithm.raft.domain.response.VoteRsp;
import pseudo.algorithm.raft.enums.RaftRole;
import pseudo.algorithm.raft.service.RaftCoreService;

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
public class RaftCoreServiceImpl implements RaftCoreService {
    @Override
    public RaftRole getRaftRole() {
        return null;
    }

    @Override
    public VoteRsp election(VoteReq voteReq) {
        return null;
    }

    @Override
    public LogRsp appendLog(LogReq logReq) {
        return null;
    }
}

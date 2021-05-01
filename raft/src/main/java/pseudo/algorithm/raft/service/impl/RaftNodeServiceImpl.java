package pseudo.algorithm.raft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pseudo.algorithm.raft.enums.RaftRole;
import pseudo.algorithm.raft.service.RaftNodeService;
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
 * @date 2021/4/26 11:40
 */
@Service
@RequiredArgsConstructor
public class RaftNodeServiceImpl implements RaftNodeService {

    private final RaftNode node;

    @Override
    public RaftRole getRaftRole() {
        return node.getRole();
    }

    @Override
    public void addNode(RaftNode node) {
        this.node.getClusterNodes().put(node.getNodeId(), node);
    }
}

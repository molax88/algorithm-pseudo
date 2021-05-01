package pseudo.algorithm.raft.service;

import pseudo.algorithm.raft.enums.RaftRole;
import pseudo.algorithm.raft.support.RaftNode;

/**
 * @author molax
 * @date 2021/4/26 10:56
 */
public interface RaftNodeService {
    /**
     * 当前节点角色
     * @return
     */
    RaftRole getRaftRole();

    /**
     * 节点接入
     */
    void addNode(RaftNode node);


}

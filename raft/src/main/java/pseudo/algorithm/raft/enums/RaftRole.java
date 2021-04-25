package pseudo.algorithm.raft.enums;

import lombok.Getter;

/**
 * @author molax
 * @date 2021/4/22 11:22
 */
public enum RaftRole {

    FOLLOWER(1, "Follower"), CANDIDATE(2, "Candidate"), LEADER(3, "Leader");

    @Getter
    private int role;

    @Getter
    private String describe;

    RaftRole(int role, String describe) {
        this.role = role;
        this.describe = describe;
    }
}

package pseudo.algorithm.raft.enums;

import lombok.Getter;

/**
 * @author molax
 * @date 2021/4/22 11:22
 */
public enum HandleType {

    ELECTION(1, "Election"), APPEND(2, "Append");

    @Getter
    private int role;

    @Getter
    private String describe;

    HandleType(int role, String describe) {
        this.role = role;
        this.describe = describe;
    }
}

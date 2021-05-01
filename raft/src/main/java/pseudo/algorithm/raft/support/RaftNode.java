package pseudo.algorithm.raft.support;

import cn.hutool.core.util.RandomUtil;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;
import pseudo.algorithm.raft.enums.RaftRole;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
 * @date 2021/4/25 16:50
 */
@Data
public class RaftNode {

    public static RaftNode instance=null;
    private RaftNode(){}
    public synchronized static RaftNode getInstance(){
        if(instance==null){
            instance=new RaftNode();
        }
        return instance;
    }
    /**
     * 任期
     */
    private int term;
    /**
     * 节点最后的日志
      */
    private long lastIndex = 0;
    /**
     * 节点角色
      */
    private RaftRole role = RaftRole.FOLLOWER;
    /**
     * 心跳间隔时间
      */
    private long heartTimeout = 5000;
    /**
     * 过期时间
      */
    private long timeout = RandomUtil.randomLong(5500, 6500);
    /**
     * 节点ID
     */
    private String nodeId= UUID.randomUUID().toString();
    /**
     * 投票记录
     */
    private Map<Integer,String> voted;
    /**
     * 集群ID
     */
    private Set<String> clusterIds;
    /**
     * 集群节点
     */
    private Map<String, RaftNode> clusterNodes;

}

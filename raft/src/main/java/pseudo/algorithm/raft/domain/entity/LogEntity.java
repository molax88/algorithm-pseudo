package pseudo.algorithm.raft.domain.entity;

import lombok.Data;
import lombok.ToString;

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
 * @date 2021/4/22 10:58
 */
@Data
public class LogEntity {

    private long preIndex;

    private long index;

    private String data;

    private boolean committed;

    public static LogEntity of(String data){
        return new LogEntity().setData(data).setCommitted(false);
    }

    @Override
    public String toString(){
        return String.format("logEntity:{index:%s,data:%s,preIndex:%s,committed:%s}", this.index, this.data, this.preIndex,this.committed);
    }
}

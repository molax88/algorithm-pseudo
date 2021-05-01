package pseudo.algorithm.raft.controller;

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
 * @date 2021/4/21 15:26
 */

import com.alipay.sofa.rpc.config.ConsumerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pseudo.algorithm.raft.domain.response.LogRsp;
import pseudo.algorithm.raft.service.HelloService;
import pseudo.algorithm.raft.service.RaftComposeService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/raft")
@RequiredArgsConstructor
public class RaftController {

    private final RaftComposeService raftComposeService;

    @RequestMapping("/hello")
    public Mono<LogRsp> hello(String string){
        return raftComposeService.handleLog(string);
    }

}

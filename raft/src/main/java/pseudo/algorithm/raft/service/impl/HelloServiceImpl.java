package pseudo.algorithm.raft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pseudo.algorithm.raft.service.HelloService;
import reactor.core.publisher.Mono;

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
 * @date 2021/4/21 14:52
 */
@Service
@RequiredArgsConstructor
public class HelloServiceImpl implements HelloService {

    @Override
    public Mono<String> sayHello(String string) {
        return Mono.create(sink -> sink.success(String.format("hello, %s", string)));
    }

    @Override
    public String directSayHello(String string) {
        return String.format("hello, %s", string);
    }
}

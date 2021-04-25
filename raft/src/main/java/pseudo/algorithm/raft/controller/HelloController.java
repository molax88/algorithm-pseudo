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
import pseudo.algorithm.raft.service.HelloService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/index")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService helloService;

    @RequestMapping("/hello")
    public Mono<String> hello(String string){
        return helloService.sayHello(string);
    }

    @RequestMapping("/raft")
    public void hello(){
        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName()) // Specify the interface
                .setProtocol("bolt") // Specify the protocol.setDirectUrl
                .setDirectUrl("bolt://127.0.0.1:12200"); // Specify the direct connection address
        // Generate the proxy class
        HelloService helloService = consumerConfig.refer();
        while (true) {
            System.out.println(helloService.directSayHello("raft"));
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}

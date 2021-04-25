package pseudo.algorithm.raft.runner;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pseudo.algorithm.raft.service.HelloService;
import pseudo.algorithm.raft.service.impl.HelloServiceImpl;

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
 * @date 2021/4/21 17:30
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RpcRunner implements CommandLineRunner {

    private final HelloService helloService;

    @Override
    public void run(String... args) throws Exception {
        log.info("RpcRunner init...");
        ServerConfig serverConfig = new ServerConfig()
                // Set a protocol, which is bolt by default
                .setProtocol("bolt")
                // set a port, which is 12200 by default
                .setPort(12200)
                // non-daemon thread
                .setDaemon(false);

        ProviderConfig<HelloService> providerConfig = new ProviderConfig<HelloService>()
                // Specify the interface
                .setInterfaceId(HelloService.class.getName())
                // Specify the implementation
                .setRef(helloService)
                // Specify the server
                .setServer(serverConfig);

        providerConfig.export(); // Publish service
    }
}

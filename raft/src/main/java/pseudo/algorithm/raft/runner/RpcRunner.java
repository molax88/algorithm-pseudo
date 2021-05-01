package pseudo.algorithm.raft.runner;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pseudo.algorithm.raft.service.HelloService;
import pseudo.algorithm.raft.service.RaftComposeService;
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

    private final RaftComposeService raftComposeService;

    @Override
    public void run(String... args) {
        log.info("RpcRunner init...");
        ServerConfig serverConfig = new ServerConfig()
                // Set a protocol, which is bolt by default
                .setProtocol("bolt")
                // set a port, which is 12200 by default
                .setPort(12200)
                // non-daemon thread
                .setDaemon(false);

        ProviderConfig<RaftComposeService> providerConfig = new ProviderConfig<RaftComposeService>()
                // Specify the interface
                .setInterfaceId(RaftComposeService.class.getName())
                // Specify the implementation
                .setRef(raftComposeService)
                // Specify the server
                .setServer(serverConfig);

        // Publish service
        providerConfig.export();
    }
}

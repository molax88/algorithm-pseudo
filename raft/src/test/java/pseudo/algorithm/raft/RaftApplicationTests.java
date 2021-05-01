package pseudo.algorithm.raft;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.ParameterConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import pseudo.algorithm.raft.service.HelloService;
import pseudo.algorithm.raft.service.RaftComposeService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RaftApplicationTests {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                String.format("http://127.0.0.1:%s/index/hello?string=raft", port), String.class);
        System.out.println(String.format("测试结果为：%s", response.getBody()));
        ConsumerConfig<RaftComposeService> consumerConfig = new ConsumerConfig<RaftComposeService>()
                .setInterfaceId(RaftComposeService.class.getName()) // Specify the interface
                .setProtocol("bolt") // Specify the protocol.setDirectUrl
                .setDirectUrl("bolt://127.0.0.1:12200"); // Specify the direct connection address
        // Generate the proxy class
        RaftComposeService raftComposeService = consumerConfig.refer();
        while (true) {
            System.out.println(raftComposeService.getRaftRole().getDescribe());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }

    public static void main(String[] args) {
        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName()) // Specify the interface
                .setProtocol("bolt") // Specify the protocol.setDirectUrl
                .setDirectUrl("bolt://127.0.0.1:12200"); // Specify the direct connection address
        // Generate the proxy class
        HelloService helloService = consumerConfig.refer();
        while (true) {
            System.out.println(helloService.sayHello("raft"));
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}

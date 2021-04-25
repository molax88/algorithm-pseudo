package pseudo.algorithm.raft;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import pseudo.algorithm.raft.config.WebFluxConfig;
import pseudo.algorithm.raft.service.HelloService;
import pseudo.algorithm.raft.service.impl.HelloServiceImpl;
import reactor.netty.http.server.HttpServer;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

@SpringBootApplication
public class RaftApplication {

    public static void main(String[] args) {

//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(WebFluxConfig.class);
//        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(applicationContext).build();
//        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
//        HttpServer.create().host("localhost").port(8080).handle(adapter).bind().block();

        SpringApplication.run(RaftApplication.class, args);

    }

}

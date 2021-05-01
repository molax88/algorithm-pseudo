package pseudo.algorithm.raft.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebHandler;
import pseudo.algorithm.raft.support.RaftNode;

@Configuration
@ComponentScan
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

//    @Bean
//    public WebHandler webHandler(ApplicationContext applicationContext) {
//        DispatcherHandler dispatcherHandler = new DispatcherHandler(applicationContext);
//        return dispatcherHandler;
//    }


    @Bean
    @ConditionalOnMissingBean(RaftNode.class)
    public RaftNode raftNode(){
        return RaftNode.getInstance();
    }
}
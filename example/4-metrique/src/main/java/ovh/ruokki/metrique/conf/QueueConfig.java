package ovh.ruokki.metrique.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
@Configuration
public class QueueConfig {
    @Bean
    ThreadPoolTaskExecutor bitcoinQueue() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(3);
        return threadPoolTaskExecutor;
    }
    
}

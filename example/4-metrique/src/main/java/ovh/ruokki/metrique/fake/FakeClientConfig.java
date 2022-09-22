package ovh.ruokki.metrique.fake;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class FakeClientConfig {
    public FakeClientConfig() {
    }
    
    @Bean
    ThreadPoolTaskExecutor fakeClientExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(50);
        return threadPoolTaskExecutor;
    }
}

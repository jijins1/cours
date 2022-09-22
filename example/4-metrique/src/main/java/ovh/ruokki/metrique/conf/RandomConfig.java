package ovh.ruokki.metrique.conf;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class RandomConfig {
    @Bean
    Random random() {
        return new Random();
    }
    
}

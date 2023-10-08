package ovh.ruokki.amqp.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
    
    public static final String TEMPERATURE_QUEUE = "temperatureQueue";
    public static final String TWELVE_QUEUE = "twelveQueue";
    
    @Bean
    public Queue temperatureQueue() {
        return QueueBuilder
                .durable(TEMPERATURE_QUEUE)
                .quorum()
                .build();
    }
    @Bean
    public Queue temperatureTwelve() {
        return QueueBuilder
                .nonDurable(TWELVE_QUEUE)
                .build();
    }
    
    @Bean
    TopicExchange appExchange() {
        return new TopicExchange("exchange", true, false);
    }
    
    @Bean
    public Binding temperatureBinding(
            Queue temperatureQueue,
            TopicExchange appExchange
    ) {
        return BindingBuilder.bind(temperatureQueue).to(appExchange).with("sensors.temp.#");
    }
    @Bean
    public Binding twelveBinding(
            Queue temperatureTwelve,
            TopicExchange appExchange
    ) {
        return BindingBuilder.bind(temperatureTwelve).to(appExchange).with("#.12");
    }
}

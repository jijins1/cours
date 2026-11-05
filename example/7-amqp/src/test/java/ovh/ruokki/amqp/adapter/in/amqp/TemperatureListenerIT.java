package ovh.ruokki.amqp.adapter.in.amqp;

import ovh.ruokki.amqp.conf.QueueConfiguration;
import ovh.ruokki.amqp.service.TemperatureService;

import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.rabbitmq.RabbitMQContainer;

@Testcontainers
@SpringBootTest
class TemperatureListenerIT {
    @Autowired
    TemperatureService temperatureService;
    @Autowired
    RabbitTemplate template;
    @Container
    @ServiceConnection
    static RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:4-management");
    
    @BeforeEach
    void setUp() {
        temperatureService.clear();
    }
    
    @Test
    void itShouldReceiverMessage() {
        template.convertAndSend("exchange","sensors.temp.12", "45");
        template.convertAndSend("exchange","sensors.temp.3", "48");
        Awaitility.await().untilAsserted(() -> {
            Assertions.assertThat(temperatureService.getCall()).contains("48");
            Assertions.assertThat(temperatureService.getCall()).contains("45");
        });
        
    }
}
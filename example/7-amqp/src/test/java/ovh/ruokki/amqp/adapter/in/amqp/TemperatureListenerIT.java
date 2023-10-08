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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
class TemperatureListenerIT {
    @Autowired
    TemperatureService temperatureService;
    @Autowired
    RabbitTemplate template;
    @Container
    static GenericContainer rabbit = new GenericContainer(DockerImageName.parse("rabbitmq:3.9.28-management"))
            .withExposedPorts(5672);
    
    
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        rabbit.start();
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.port", rabbit::getFirstMappedPort);
    }
    
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
package ovh.ruokki.amqp.adapter.in.amqp;

import static ovh.ruokki.amqp.conf.QueueConfiguration.TEMPERATURE_QUEUE;
import static ovh.ruokki.amqp.conf.QueueConfiguration.TWELVE_QUEUE;

import ovh.ruokki.amqp.service.TemperatureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TemperatureListener {
    
    Logger log = LoggerFactory.getLogger(TemperatureListener.class);
    
    private final TemperatureService temperatureService;
    
    public TemperatureListener(final TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }
    
    @RabbitListener(queues = TEMPERATURE_QUEUE)
    public void temperatureChange(String temperature) {
        log.info("temperature {}", temperature);
        temperatureService.receive(temperature);
    }
    @RabbitListener(queues = TWELVE_QUEUE)
    public void twelveEvent(String temperature) {
        log.info("Evenement pour le numero 12 {}", temperature);
        temperatureService.receive(temperature);
        throw new RuntimeException("rere");
    }
}

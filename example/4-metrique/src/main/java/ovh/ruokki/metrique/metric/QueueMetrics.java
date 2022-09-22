package ovh.ruokki.metrique.metric;

import io.micrometer.core.instrument.MeterRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class QueueMetrics implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(QueueMetrics.class);
    
    private final MeterRegistry meterRegistry;
    private final ThreadPoolTaskExecutor bitcoinQueue;
    
    @Autowired
    public QueueMetrics(MeterRegistry meterRegistry, ThreadPoolTaskExecutor bitcoinQueue) {
        
        this.meterRegistry = meterRegistry;
        this.bitcoinQueue = bitcoinQueue;
    }
    
    @Override
    public void afterPropertiesSet()
            throws Exception {
        log.info("Start metrics queue");
        meterRegistry.gauge("queue.active.thread", bitcoinQueue, ThreadPoolTaskExecutor::getActiveCount);
        meterRegistry.gauge("queue.total.thread", bitcoinQueue, ThreadPoolTaskExecutor::getPoolSize);
        meterRegistry.gauge("queue.size", bitcoinQueue, ThreadPoolTaskExecutor::getQueueSize);
        
    }
}

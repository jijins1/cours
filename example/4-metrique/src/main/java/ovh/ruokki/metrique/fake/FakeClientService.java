package ovh.ruokki.metrique.fake;

import java.util.Random;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import ovh.ruokki.metrique.service.BitcoinService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public
class FakeClientService implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(FakeClientService.class);
    
    private final BitcoinService bitcoinService;
    private final ThreadPoolTaskExecutor fakeClientExecutor;
    private final Random random;
    private final Tracer tracer;
    
    public FakeClientService(final BitcoinService bitcoinService, @Qualifier("fakeClientExecutor") ThreadPoolTaskExecutor fakeClientExecutor, Random random, final Tracer tracer) {
        this.bitcoinService = bitcoinService;
        this.fakeClientExecutor = fakeClientExecutor;
        this.random = random;
        this.tracer = tracer;
    }
    
    @Override
    public void run(final String... args)
            throws Exception {
        while (true) {
            if (this.fakeClientExecutor.getActiveCount() < this.fakeClientExecutor.getCorePoolSize()) {
                log.info("Demarrage d'un client");
                this.fakeClientExecutor.execute(this::addMiningClient);
            } else {
                log.info("Attendre que les requetes termine un peu");
                Thread.sleep(200000);
            }
            
        }
    }
    
    private void addMiningClient() {
        int minerNumber = new Random().nextInt();
        Span miner = this.tracer.nextSpan().name("Miner"+minerNumber);
        int succes = 0;
        Span mining = miner;
        try (Tracer.SpanInScope ws = this.tracer.withSpan(mining.start())) {
            log.info("Add mining client {}", minerNumber);
            
            while (this.random.nextBoolean()) {
                mining.end();
                mining = this.tracer.nextSpan(mining).name("mining"+succes);
                try (Tracer.SpanInScope ws2 = this.tracer.withSpan(mining.start())) {
                    
                    log.info("Heho heho {}", succes);
                    succes++;
                    bitcoinService.miner();
                }
                
            }
            log.info("Client termine");
        } finally {
            mining.end();
        }
        
    }
}

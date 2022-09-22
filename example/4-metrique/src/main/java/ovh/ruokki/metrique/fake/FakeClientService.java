package ovh.ruokki.metrique.fake;


import java.util.Random;

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
    
    public FakeClientService(final BitcoinService bitcoinService,@Qualifier("fakeClientExecutor") ThreadPoolTaskExecutor fakeClientExecutor, Random random) {
        this.bitcoinService = bitcoinService;
        this.fakeClientExecutor = fakeClientExecutor;
        this.random = random;
    }
    
    @Override
    public void run(final String... args)
            throws Exception {
        while (true){
            if(this.fakeClientExecutor.getActiveCount()< this.fakeClientExecutor.getCorePoolSize()){
                log.info("Demarrage d'un client");
                this.fakeClientExecutor.execute(this::addMiningClient);
            }else {
                log.info("Attendre que les requetes termine un peu");
                Thread.sleep(200000);
            }
            
        }
    }
    
    private void addMiningClient() {
        while (this.random.nextBoolean()) {
            log.info("Heho heho");
            bitcoinService.miner();
            
        }
        log.info("Client termine");
    }
}

package ovh.ruokki.metrique.service;

import java.util.Random;
import java.util.UUID;

import ovh.ruokki.metrique.model.BitCoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BitcoinServiceRandom implements BitcoinService {
    private static final Logger log = LoggerFactory.getLogger(BitcoinServiceRandom.class);
    
    private final Random random;
    @Autowired
    public BitcoinServiceRandom(Random random) {
        this.random = random;
    }
    
    @Override
    public BitCoin miner() {
        long sleep = random.nextLong(100, 10000);
        try {
            Thread.sleep(sleep);
            BitCoin bitCoin = new BitCoin(UUID.randomUUID());
            log.info("Bitcoin mined {}", bitCoin);
            return bitCoin;
        } catch (InterruptedException e) {
            log.error("Interruption", e);
            throw new RuntimeException(e);
        }
        
    }
}

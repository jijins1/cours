package ovh.ruokki.metrique.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import ovh.ruokki.metrique.model.BitCoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BitCoinServiceQueued implements BitcoinService {
    private static final Logger log = LoggerFactory.getLogger(BitCoinServiceQueued.class);
    
    private final BitcoinService bitcoinService;
    private final Executor executor;
    
    @Autowired
    public BitCoinServiceQueued(BitcoinService bitcoinService,@Qualifier("bitcoinQueue") Executor executor) {
        
        this.bitcoinService = bitcoinService;
        this.executor = executor;
    }
    
    @Override
    public BitCoin miner() {
        try {
            log.info("Add request to the queue");
            return CompletableFuture.supplyAsync(this.bitcoinService::miner, executor).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

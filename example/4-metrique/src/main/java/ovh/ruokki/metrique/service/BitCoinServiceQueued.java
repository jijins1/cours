package ovh.ruokki.metrique.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
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
    private final Tracer tracer;
    
    @Autowired
    public BitCoinServiceQueued(BitcoinService bitcoinService,@Qualifier("bitcoinQueue") Executor executor, final Tracer tracer) {
        
        this.bitcoinService = bitcoinService;
        this.executor = executor;
        this.tracer = tracer;
    }
    
    @Override
    public BitCoin miner() {
        try {
            
            log.info("Add request to the queue");
            Span span = this.tracer.currentSpan();
            return CompletableFuture.supplyAsync(() -> {
                try(var scopedSpan = this.tracer.withSpan(span)){
                    return this.bitcoinService.miner();
                }
            }, executor).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

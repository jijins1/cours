package ovh.ruokki.metrique.metric;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MetricsBitcoinAspect {
    
    private final MeterRegistry registry;
    
    @Autowired
    public MetricsBitcoinAspect(MeterRegistry registry) {
        this.registry = registry;
    }
    
    @AfterReturning("execution(public * ovh.ruokki.metrique.service.BitcoinService.miner())")
    public void succes(JoinPoint joinPoint) {
        Tag tag = Tag.of("miner-impl", joinPoint.getTarget().getClass().getSimpleName());
        registry.counter("bitcoin-mined", List.of(tag)).increment();
    }
    
    @AfterThrowing("execution(public * ovh.ruokki.metrique.service.BitcoinService.miner())")
    public void fail(JoinPoint joinPoint) {
        Tag tag = Tag.of("miner-impl", joinPoint.getTarget().getClass().getSimpleName());
        registry.counter("bitcoin-failed", List.of(tag)).increment();
    }
    
    @Around("execution(public * ovh.ruokki.metrique.service.BitcoinService.miner())")
    public Object duration(ProceedingJoinPoint joinPoint)
            throws Throwable {
        Tag tag = Tag.of("miner-impl", joinPoint.getTarget().getClass().getSimpleName());
        Timer timer = registry.timer("bitcoin-duration", List.of(tag));
        Instant startTime = Instant.now();
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } finally {
            timer.record(Duration.between(startTime, Instant.now()));
        }
        
    }
    
}

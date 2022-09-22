package ovh.ruokki.metrique.model;

import java.util.UUID;

public class BitCoin {
    
    private final UUID hash;
    
    public BitCoin(final UUID hash) {
        this.hash = hash;
    }
    
    @Override
    public String toString() {
        return "BitCoin{" +
                "hash=" + hash +
                '}';
    }
}

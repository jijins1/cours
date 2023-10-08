package ovh.ruokki.amqp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TemperatureService {
    
    private final List<String> call;
    
    public TemperatureService() {
        call = new ArrayList<>();
    }
    
    public void receive(String s) {
        call.add(s);
    }
    
    public void clear() {
        call.clear();
    }
    
    public List<String> getCall() {
        return call;
    }
    
}

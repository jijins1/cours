package ovh.ruokki.example.basic.controller;

import java.util.List;
import java.util.Optional;

import ovh.ruokki.example.basic.controller.dto.Biere;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {
    
    @GetMapping()
    public ResponseEntity<Void> login() {
        return ResponseEntity.ok().build();
    }
    
    
}

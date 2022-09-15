package ovh.ruokki.example.openid.controller;

import java.util.ArrayList;
import java.util.List;

import ovh.ruokki.example.openid.controller.dto.Biere;
import ovh.ruokki.example.openid.controller.dto.UtilisateurDto;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {


    public UserController() {
    }

    @GetMapping()
    public UtilisateurDto bieres() {
        return new UtilisateurDto(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}

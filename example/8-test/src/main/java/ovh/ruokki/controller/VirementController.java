package ovh.ruokki.controller;

import ovh.ruokki.service.VirementService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VirementController {
    @PostMapping(path="/virements")
    public void post(@RequestBody VirementDto virementDto) {
        virementService.validationVirement(virementDto.getSource(), virementDto.getDestination(), virementDto.getMontant());
    }
    
    public VirementController(final VirementService virementService) {
        this.virementService = virementService;
    }
    
    private final VirementService virementService;
}

package ovh.ruokki.controller;

import ovh.ruokki.service.VirementService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class VirementControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private VirementService virementService;
    @Test
    void itShouldGetCompte()
            throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/virements").content("""
                {
                    "source":"source",
                    "destination":"destination",
                    "montant":20
                }
                """).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        Mockito.verify(virementService).applyVirement("source", "destination", 20);
    }
}

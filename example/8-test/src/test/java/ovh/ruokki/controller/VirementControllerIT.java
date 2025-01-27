package ovh.ruokki.controller;

import ovh.ruokki.service.VirementService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class VirementControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
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
        Mockito.verify(virementService).validationVirement("source", "destination", 20);
    }
}

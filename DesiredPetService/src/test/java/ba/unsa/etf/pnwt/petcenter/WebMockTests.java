package ba.unsa.etf.pnwt.petcenter;


import ba.unsa.etf.pnwt.petcenter.Repositories.DesiredPetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebMockTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DesiredPetRepository desiredPetRepository;

    @BeforeEach
    void  prepare(){
        desiredPetRepository.deleteAll();
    }
    @Test
    void addDesiredPet() throws Exception {
        mockMvc.perform(post("/desired-pet")
                .content("{" +"\"species\": \"DOG\",\"age\": \"Junior\","+
                        "\"race\":\"labrador\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.race").value("labrador"));
    }

    @Test
    void badRequestSpecies() throws Exception {
        mockMvc.perform(post("/desired-pet")
                        .content("{" +"\"species\": \"nessto\",\"age\": \"Junior\","+
                                "\"race\":\"labrador\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void badRequestAge() throws Exception {
        mockMvc.perform(post("/desired-pet")
                        .content("{" +"\"species\": \"DOG\","+
                                "\"race\":\"labrador\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

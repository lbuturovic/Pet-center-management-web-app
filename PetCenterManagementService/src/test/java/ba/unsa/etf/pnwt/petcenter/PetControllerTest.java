package ba.unsa.etf.pnwt.petcenter;


import ba.unsa.etf.pnwt.petcenter.Exception.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Models.*;
import ba.unsa.etf.pnwt.petcenter.Repositories.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.PetRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetControllerTest {

   /* @Autowired
    MockMvc mockMvc;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private PetRepository petRepository;


    Center c;
    Pet p1, p2,p3;

    @BeforeAll
    void setup() {
        c = new Center(15, "Kolodvorska 35", "Sarajevo", "033454000");
        centerRepository.save(c);
        p1 = new Pet(Species.DOG, "Njemacki ovcar", "Reks", Age.JUNIOR, 30F, "Potpuno zdrav", Gender.MALE, "reks25.png", Status.ABANDONED, Category.BIG, null, c);
        petRepository.save(p1);
        p2 = new Pet(Species.DOG, "Angora", "Ketty", Age.JUNIOR, 7F, "Potpuno zdrava", Gender.FEMALE, "ketty26.png", Status.ABANDONED, Category.SMALL, null, c);
        petRepository.save(p2);
        p3 = new Pet(Species.DOG, "Njemacki ovcar", "Aron", Age.JUNIOR, 35F, "Potpuno zdrav", Gender.MALE, "aron25.png", Status.ABANDONED, Category.BIG, null, c);
        petRepository.save(p3);

    }

    @Test
    void getAllPets() throws Exception {
        petRepository.deleteAll();
        setup();
        mockMvc.perform(get("/api/pets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Reks"))
                .andExpect(jsonPath("$.[1].name").value("Ketty"));
    }

   @Test
    void addPetSuccessfully() throws Exception {
        int centerId = centerRepository.findById(c.getId()).get().getId();
        mockMvc.perform(post("/api/pet")
                .content(String.format("{ " +
                        "\"species\": \"DOG\"," +
                        "\"race\": \"Belgijski ovcar\"," +
                        "\"name\": \"Rea\"," +
                        "\"age\": \"JUNIOR\","+
                        "\"weight\": 33.0," +
                        "\"healthCondition\": \"Potpuno zdrav\","+
                        "\"gender\": \"FEMALE\","+
                        "\"image\": \"rea27.png\","+
                        "\"status\": \"ABANDONED\","+
                        "\"category\": \"BIG\","+
                        "\"center\": { " +
                            "\"id\":  %d " +
                                "}" +
                        "}", centerId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Pet successfully added!"));
    }

    @Test
    void getPet() throws Exception {
        mockMvc.perform(get(String.format("/api/pet/%d", p3.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aron"));
    }

    @Test
    void deletePet() throws Exception {
        int id = p2.getId();
        mockMvc.perform(delete(String.format("/api/pet/%d", id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Pet with id = " + id +" successfully deleted!"));
    }

    @Test
    void deletePetNotFound() throws Exception {
        int id = 1000;
        mockMvc.perform(delete(String.format("/api/pet/%d", id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }


    @Test
    void updatePet() throws Exception {
        int id = p1.getId();
        int centerId = centerRepository.findById(c.getId()).get().getId();
        mockMvc.perform(put(String.format("/api/pet/%d", id))
                .content(String.format("{ " +
                        "\"species\": \"DOG\"," +
                        "\"race\": \"Belgijski ovcar\"," +
                        "\"name\": \"Rea\"," +
                        "\"age\": \"JUNIOR\","+
                        "\"weight\": 35.0," +
                        "\"healthCondition\": \"Potpuno zdrav\","+
                        "\"gender\": \"MALE\","+
                        "\"image\": \"rea27.png\","+
                        "\"status\": \"ABANDONED\","+
                        "\"category\": \"BIG\","+
                        "\"center\": { " +
                        "\"id\":  %d " +
                        "}" +
                        "}", centerId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Pet with id = " + id +" successfully updated!"));
    }

    @Test
    void addPetWithInvalidWeight() throws Exception {
        int centerId = centerRepository.findById(c.getId()).get().getId();
        mockMvc.perform(post("/api/pet")
                .content(String.format("{ " +
                        "\"species\": \"DOG\"," +
                        "\"race\": \"Belgijski ovcar\"," +
                        "\"name\": \"Rea\"," +
                        "\"age\": \"JUNIOR\","+
                        "\"weight\": -33.0," +
                        "\"healthCondition\": \"Potpuno zdrav\","+
                        "\"gender\": \"MALE\","+
                        "\"image\": \"rea27.png\","+
                        "\"status\": \"ABANDONED\","+
                        "\"category\": \"BIG\","+
                        "\"center\": { " +
                        "\"id\":  %d " +
                        "}" +
                        "}", centerId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid value for weight!"));
    }

    @Test
    void addPetWithWithoutName() throws Exception {
        int centerId = centerRepository.findById(c.getId()).get().getId();
        mockMvc.perform(post("/api/pet")
                .content(String.format("{ " +
                        "\"species\": \"DOG\"," +
                        "\"race\": \"Belgijski ovcar\"," +
                        "\"age\": \"JUNIOR\","+
                        "\"weight\": 33.0," +
                        "\"healthCondition\": \"Potpuno zdrav\","+
                        "\"gender\": \"MALE\","+
                        "\"image\": \"rea27.png\","+
                        "\"status\": \"ABANDONED\","+
                        "\"category\": \"BIG\","+
                        "\"center\": { " +
                        "\"id\":  %d " +
                        "}" +
                        "}", centerId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name is mandatory!"));
    }*/
}

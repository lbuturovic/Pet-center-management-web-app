package ba.unsa.etf.pnwt.petcenter;

import ba.unsa.etf.pnwt.petcenter.Exception.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Repositories.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.PetRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.ServiceRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
class CenterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CenterRepository centerRepository;


    Center c1, c2, c3;


    @BeforeAll
    void setup() {
        c1 = new Center(15, "Nahorevska 21", "Sarajevo", "033454000");
        c2 = new Center(15, "Josanicka 21", "Sarajevo", "033454747");
        c3 = new Center(25, "Kolodvorska 35", "Sarajevo", "033420747");
        centerRepository.save(c1);
        centerRepository.save(c2);
        centerRepository.save(c3);
    }

    @Test
    void getAllCenters() throws Exception {
        centerRepository.deleteAll();
        setup();
        mockMvc.perform(get("/api/centers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].address").value("Nahorevska 21"))
                .andExpect(jsonPath("$.[1].address").value("Josanicka 21"))
                .andExpect(jsonPath("$.[2].address").value("Kolodvorska 35"));
    }

    @Test
    void addCenterSuccessfully() throws Exception {
        mockMvc.perform(post("/api/center")
                .content("{\n" +
                        "\"capacity\": 17," +
                        "\"address\": \"Asima Ferhatovica 111\"," +
                        "\"city\": \"Sarajevo\"," +
                        "\"phoneNumber\": \"033547896\""+
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Center successfully added!"));
    }

    @Test
    void addCenterWithInvalidPhoneNUmber() throws Exception {
        mockMvc.perform(post("/api/center")
                .content("{\n" +
                        "\"capacity\": 17," +
                        "\"address\": \"Asima Ferhatovica 111\"," +
                        "\"city\": \"Sarajevo\"," +
                        "\"phoneNumber\": \"0337896\""+
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid format for phone number!"));
    }

    @Test
    void getCenter() throws Exception {
        mockMvc.perform(get(String.format("/api/center/%d", c3.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("Kolodvorska 35"))
                .andExpect(jsonPath("$.phoneNumber").value("033420747"));
    }

    @Test
    void deleteCenter() throws Exception {
        int id = c1.getId();
        mockMvc.perform(delete(String.format("/api/center/%d", id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Center with id = " + id +" successfully deleted!"));

    }

    @Test
    void deleteCenterNotFound() throws Exception {
        int id = 1000;
        mockMvc.perform(delete(String.format("/api/center/%d", id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }


    @Test
    void updateCenter() throws Exception {
        int id = c2.getId();
        mockMvc.perform(put(String.format("/api/center/%d", id))
                .content("{\n" +
                        "\"capacity\": 17," +
                        "\"petNo\": 0," +
                        "\"address\": \"Marcela Snajdera 111\"," +
                        "\"city\": \"Sarajevo\"," +
                        "\"phoneNumber\": \"033417896\""+
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Center with id = " + id +" successfully updated!"));
    }

    @Test
    void addCenterNegativeCapacity() throws Exception {
        mockMvc.perform(post(String.format("/api/center"))
                .content("{\n" +
                        "\"capacity\": -1," +
                        "\"address\": \"Marcela Snajdera 111\"," +
                        "\"city\": \"Sarajevo\"," +
                        "\"phoneNumber\": \"033417896\""+
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Min value for capacity is 0!"));
    }


}
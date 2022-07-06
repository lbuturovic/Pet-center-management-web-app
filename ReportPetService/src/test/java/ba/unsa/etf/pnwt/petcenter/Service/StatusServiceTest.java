/*package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Exceptions.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Model.Status;
import ba.unsa.etf.pnwt.petcenter.Repository.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.FormRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.ReportRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.StatusRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatusServiceTest {

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    MockMvc mockMvc;

    private Status s1, s2, s3;

    @BeforeAll
    void setup() {
        s1 = new Status("Zahtjev u obradi.");
        s2 = new Status("Ekipa poslana na naznačenu lokaciju.");
        s3 = new Status("Ekipa se nalazi na naznačenoj lokaciji.");
        s1 = statusRepo.save(s1);
        s2 = statusRepo.save(s2);
        s3 = statusRepo.save(s3);
    }

    @Test
    @Order(1)
    void getAllStatuses() throws Exception {
        mockMvc.perform(get("/api/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Zahtjev u obradi."))
                .andExpect(jsonPath("$.[1].name").value("Ekipa poslana na naznačenu lokaciju."))
                .andExpect(jsonPath("$.[2].name").value("Ekipa se nalazi na naznačenoj lokaciji."));
    }

    @Test
    @Order(2)
    void addStatusWithInvalidName() throws Exception {
        mockMvc.perform(post("/api/status")
                        .content("{\n" +
                                "    \"name\": \"\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name is mandatory; "));
    }

    @Test
    @Order(3)
    void addStatusSuccessfully() throws Exception {
        mockMvc.perform(post("/api/status")
                        .content("{\n" +
                                "    \"name\": \"Ljubimac nije nađen na naznačenoj lokaciji.\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ljubimac nije nađen na naznačenoj lokaciji."));
    }

    @Test
    @Order(4)
    void getStatus() throws Exception {
        s1 = new Status("Zahtjev u obradi.");
        statusRepo.save(s1);
        mockMvc.perform(get(String.format("/api/status/%d", s1.getStatusID()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Zahtjev u obradi."));
    }

    @Test
    @Order(5)
    void getStatusInvalidArgument() throws Exception {
        mockMvc.perform(get(String.format("/api/status/kk"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Method Argument Type Mismatch Exception"));
    }

    @Test
    @Order(6)
    void getStatusInvalidId() throws Exception {
        mockMvc.perform(get(String.format("/api/status/%d", s3.getStatusID() + 110))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    @Order(7)
    void updateStatus() throws Exception {
        Integer id = s2.getStatusID();
        mockMvc.perform(put(String.format("/api/status/%d", id))
                        .content("{\n" +
                                "    \"name\": \"Ekipa poslana na lokaciju naznačenu u prijavi.\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ekipa poslana na lokaciju naznačenu u prijavi."));


    }

    @Test
    @Order(8)
    void updateStatusInvalidId() throws Exception {
        mockMvc.perform(put(String.format("/api/status/%d", s3.getStatusID()+100))
                        .content("{\n" +
                                "    \"name\": \"Ekipa poslana na lokaciju naznačenu u prijavi.\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    @Order(9)
    void deleteStatus() throws Exception {
        Integer id = s3.getStatusID();
        mockMvc.perform(delete(String.format("/api/status/%d", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @Order(10)
    void deleteStatusInvalidId() throws Exception {
        Integer id = s3.getStatusID()+1034;
        mockMvc.perform(delete(String.format("/api/status/%d", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(jsonPath("$.message").value("Status with id = " + id + " does not exist!"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

    @Test
    @Order(11)
    void deleteAllStatus() throws Exception {
        mockMvc.perform(delete(String.format("/api/status"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}*/

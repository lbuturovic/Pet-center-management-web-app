package ba.unsa.etf.pnwt.petcenter;

import ba.unsa.etf.pnwt.petcenter.Exception.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Models.Service;
import ba.unsa.etf.pnwt.petcenter.Repositories.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.ServiceRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    Center c1, c2;
    Service s1,s2, s3;



    @BeforeAll
    void setup() {
        c1 = new Center(15, "Nahorevska 21", "Sarajevo", "033454000");
        c2 = new Center(25, "Josanicka 21", "Sarajevo", "033454747");
        centerRepository.save(c1);
        centerRepository.save(c2);
        Set<Center> centers = new HashSet<>();
        centers.add(c1);
        centers.add(c2);
        s1 = new Service("Kupanje malog psa", 25.0, 20, centers);
        serviceRepository.save(s1);
        Set<Center> centers1 = new HashSet<>();
        centers1.add(c1);
        s2 = new Service("Kupanje velikog psa", 30.0, 20, centers1);
        serviceRepository.save(s2);
        Set<Center> centers2 = new HashSet<>();
        centers.add(c2);
        s3 = new Service("Sisanje velikog psa", 20.0, 20, centers2);
        serviceRepository.save(s3);
    }

    @Test
    void getAllServices() throws Exception {
        centerRepository.deleteAll();
        serviceRepository.deleteAll();
        setup();
        mockMvc.perform(get("/api/services")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Kupanje malog psa"))
                .andExpect(jsonPath("$.[1].name").value("Kupanje velikog psa"));
    }

    @Test
    void addServiceSuccessfully() throws Exception {
        mockMvc.perform(post("/api/service")
                .content("{\n" +
                        "\"name\": \"Sisanje malog psa\"," +
                        "\"price\": 20, " +
                        "\"duration\": 30,"+
                        "\"type\": \"ONETIME\","+
                        "\"centers\": " +
                            "[{"+
                                "\"id\": 1 " +
                                "}, {" +
                                "\"id\": 2" +
                             "}]"+
                            "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Service successfully added!"));
    }

    @Test
    void addServiceWithNegativeDuration() throws Exception {
        mockMvc.perform(post("/api/service")
                .content("{\n" +
                        "\"name\": \"Sisanje malog psa\"," +
                        "\"price\": 20, " +
                        "\"duration\": -30,"+
                        "\"type\": \"ONETIME\","+
                        "\"centers\": " +
                        "[{"+
                        "\"id\": 1 " +
                        "}, {" +
                        "\"id\": 2" +
                        "}]"+
                        "}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid value for duration!"));
    }

    @Test
    void getService() throws Exception {
        mockMvc.perform(get(String.format("/api/service/%d", s1.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kupanje malog psa"))
                .andExpect(jsonPath("$.price").value(25));
    }

    @Test
    void deleteService() throws Exception {
        int id = s2.getId();
        mockMvc.perform(delete(String.format("/api/service/%d", id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Service with id = " + id +" successfully deleted!"));

    }

    @Test
    void deleteServiceNotFound() throws Exception {
        int id = 1000;
        mockMvc.perform(delete(String.format("/api/service/%d", id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }


   @Test
    void updateService() throws Exception {
        int id = s3.getId();
        mockMvc.perform(put(String.format("/api/service/%d", id))
                .content("{\n" +
                        "\"name\": \"Sisanje velikog psa\"," +
                        "\"price\": 15, " +
                        "\"duration\": 30,"+
                        "\"type\": \"ONETIME\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Service with id = " + id +" successfully updated!"));
    }

}

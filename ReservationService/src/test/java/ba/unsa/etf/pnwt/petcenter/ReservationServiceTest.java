package ba.unsa.etf.pnwt.petcenter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Models.Service;
import ba.unsa.etf.pnwt.petcenter.Models.User;
import ba.unsa.etf.pnwt.petcenter.Repositories.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.ServiceRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.UserRepository;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    Center c;
    Service s;
    User u;


    @Test
    public void test1() {

        try {
            mockMvc.perform(post("/reservation")
                            .content("{" +"\"startDate\": \"1998-12-12 12:12:12\","+
                                    "\"center\":{\"id\": 1},\"service\": {\"id\": 1},\"user\": {\"id\": 1}}")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.endDate").value("1998-12-12 12:47:12"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test2() {

        try {
            mockMvc.perform(post("/reservation")
                            .content("{" +"\"startDate\": \"1998-12-12 00:00:00\","+
                                    "\"center\":{\"id\": 1},\"service\": {\"id\": 1},\"user\": {\"id\": 1}}")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.endDate").value("1998-12-12 00:35:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test3() {

        try {
            mockMvc.perform(post("/reservation")
                            .content("{" + "\"startDate\": \"1998-12-12 00:00:00\"," + "\"endDate\": \"2000-12-12 00:00:00\"," +
                                    "\"center\":{\"id\": 1},\"service\": {\"id\": 1},\"user\": {\"id\": 1}}")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.endDate").value("2000-12-12 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {

        try {
            mockMvc.perform(post("/reservation")
                            .content("{" +"\"startDate\": \"1998-12-12 00:00:00\","+
                                    "\"center\":{\"id\": 1},\"service\": {\"id\": 100},\"user\": {\"id\": 1}}")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.error").value("Not found error"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test5() {

        try {
            mockMvc.perform(post("/reservation")
                            .content("{" +"\"startDate\": \"1998-12-12 00:00:00\","+
                                    "\"center\":{\"id\": 1},\"service\": {\"id\": 100},\"user\": {\"id\": 1}}")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Service with id = 100 does not exist!"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

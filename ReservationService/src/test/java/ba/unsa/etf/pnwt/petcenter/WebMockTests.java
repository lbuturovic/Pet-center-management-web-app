package ba.unsa.etf.pnwt.petcenter;

import ba.unsa.etf.pnwt.petcenter.Repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebMockTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void prepare(){
        reservationRepository.deleteAll();
    }

    @Test
    void addReservation() throws Exception {
        mockMvc.perform(post("/reservation")
                        .content("{" +"\"startDate\": \"1998-12-12 12:12:12\",\"endDate\": \"1999-12-12 12:12:12\","+
                                "\"center\":{\"id\": 95},\"service\": {\"id\": 96},\"user\": {\"id\": 97}}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startDate").value("1998-12-12 12:12:12"));
    }

    @Test
    void notValidDate() throws Exception {
        mockMvc.perform(post("/reservation")
                        .content("{" +"\"startDate\": \"1998-12-12 12:12:12\",\"endDate\": \"1997-12-12 12:12:12\","+
                                "\"center\":{\"id\": 95},\"service\": {\"id\": 96},\"user\": {\"id\": 97}}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}

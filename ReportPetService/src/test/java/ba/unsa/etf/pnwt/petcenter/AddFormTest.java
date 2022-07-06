/*package ba.unsa.etf.pnwt.petcenter;

import ba.unsa.etf.pnwt.petcenter.Model.Center;
import ba.unsa.etf.pnwt.petcenter.Model.Report;
import ba.unsa.etf.pnwt.petcenter.Model.Status;
import ba.unsa.etf.pnwt.petcenter.Repository.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.ReportRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.StatusRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddFormTest {
    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private ReportRepository reportRepo;

    @Autowired
    private CenterRepository centerRepo;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Status s1;
    private Report r1, r3;
    private Center c1, c2, c3;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @BeforeAll
    void setup() throws IOException {
        s1 = new Status("Zahtjev u obradi.");
        statusRepo.save(s1);
        r1 = new Report("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=1.00xw:0.669xh;0,0.190xh&resize=1200:*", 43.653735, 41.263736, "Napušteni pas u blizini dječijeg igrališta.", null, s1);
        r1 = reportRepo.save(r1);
        r3 = new Report("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=1.00xw:0.669xh;0,0.190xh&resize=1200:*", 35.0, 35.0, null, null, s1);
        r3 = reportRepo.save(r3);
        c1 = new Center("Sarajevo", 1); //znamo da u bazi imamo centar sa id = 1
        centerRepo.save(c1);
        c2 = new Center("Zenica", 2); //znamo da u bazi imamo centar sa id = 2
        centerRepo.save(c2);
        c3 = new Center("Tuzla", 9999); //nepostojeci id
        centerRepo.save(c3);
    }

    @Test
    @Order(1)
    public void testAddForm1() throws Exception {
        mockMvc.perform(post(String.format("/api/center/%d/report/%s/forms",c1.getCenterID(), r1.getReportID()))
                        .content("{\n" +
                                "    \"type\": \"pas\",\n" +
                                "    \"description\": \"zuti pas srednje velicine primljen u nas centar u dobrom stanju\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("pas"));
        }

    @Test
    @Order(2)
    public void testAddForm2() throws Exception { //nema mjesta
        mockMvc.perform(post(String.format("/api/center/%d/report/%s/forms",c2.getCenterID(), r3.getReportID()))
                        .content("{\n" +
                                "    \"type\": \"pas\",\n" +
                                "    \"description\": \"zuti pas srednje velicine primljen u nas centar u dobrom stanju\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Not allowed"));
    }

    @Test
    @Order(3)
    public void testAddForm3() throws Exception { //nepostojeci id u center servisu
        mockMvc.perform(post(String.format("/api/center/%d/report/%s/forms",c3.getCenterID(), r3.getReportID()))
                        .content("{\n" +
                                "    \"type\": \"pas\",\n" +
                                "    \"description\": \"zuti pas srednje velicine primljen u nas centar u dobrom stanju\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found")) //poruka poslana iz center servisa
                .andExpect(jsonPath("$.message").value("Center with id = " + c3.getId() + " does not exist!"));
    }


    @Test
    @Order(4)
    public void testCheckCapacityRestTemplate() throws URISyntaxException {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://pet-center-management-service/api/center/free/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                      .contentType(MediaType.APPLICATION_JSON));

        mockServer.verify();
    }

    }*/


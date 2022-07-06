/*package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Model.Center;
import ba.unsa.etf.pnwt.petcenter.Model.Report;
import ba.unsa.etf.pnwt.petcenter.Model.Status;
import ba.unsa.etf.pnwt.petcenter.Repository.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.FormRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.ReportRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.StatusRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FormServiceTest {

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private CenterRepository centerRepo;

    @Autowired
    private ReportRepository reportRepo;

    @Autowired
    private FormRepository formRepo;

    @Autowired
    MockMvc mockMvc;

    private Status s1, s2, s3;
    private Report r1, r3;
    private Center c1;


    @BeforeAll
    void setup() {
        s1 = new Status("Zahtjev u obradi.");
        s1 = statusRepo.save(s1);
        s2 = new Status("Ekipa poslana na naznačenu lokaciju.");
        s2 = statusRepo.save(s2);
        s3 = new Status("Ekipa se nalazi na naznačenoj lokaciji.");
        s3 = statusRepo.save(s3);
        r1 = new Report("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=1.00xw:0.669xh;0,0.190xh&resize=1200:*", 43.653735, 41.263736, "Napušteni pas u blizini dječijeg igrališta.", null, s1);
        r1 = reportRepo.save(r1);
        r3 = new Report("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=1.00xw:0.669xh;0,0.190xh&resize=1200:*", 35.0, 35.0, null, null, s3);
        r3 = reportRepo.save(r3);
        c1 = new Center("Sarajevo",15);

    }

  /*  @Test
    @Order(1)
    void addReportSuccessfully() throws Exception {
        mockMvc.perform(post(String.format("/api/center/%d/report/%s/forms",c1.getCenterID(), r1.getReportID()))
                        .content("{\n" +
                                "    \"type\": \"pas\",\n" +
                                "    \"description\": \"zuti pas srednje velicine primljen u nas centar u dobrom stanju\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("pas"));
    }

}*/

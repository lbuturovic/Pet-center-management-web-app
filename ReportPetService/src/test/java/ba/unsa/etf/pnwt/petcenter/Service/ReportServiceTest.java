/*package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Exceptions.NotFoundException;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReportServiceTest {

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
    }

   /* @Test
    void getAllReports() throws Exception {
        mockMvc.perform(get("/api/reports")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].longitude").value(43.653735))
                .andExpect(jsonPath("$.[0].latitude").value(72.363523));
    }

    @Test
    @Order(1)
    void getAllReportsByStatusId() throws Exception {
        mockMvc.perform(get(String.format("/api/status/%d/reports", s1.getStatusID()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].longitude").value(43.653735))
                .andExpect(jsonPath("$.[0].latitude").value(41.263736));
    }

    @Test
    @Order(2)
    void getAllReportsByStatusIdNotExistingInReports() throws Exception {
        mockMvc.perform(get(String.format("/api/status/%d/reports", s2.getStatusID()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(3)
    void addReportSuccessfully() throws Exception {
        mockMvc.perform(post(String.format("/api/status/%d/reports", s1.getStatusID()))
                        .content("{\n" +
                                "    \"description\": \"Pas veseo, hrane ga stanari obližnje zgrade\",\n" +
                                "    \"imagePath\": \"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=1.00xw:0.669xh;0,0.190xh&resize=1200:*\",\n" +
                                "    \"latitude\": 41,\n" +
                                "    \"longitude\": 38\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.latitude").value(41.0));
    }

    @Test
    @Order(4)
    void addReportWithInvalidLatitude() throws Exception {
        mockMvc.perform(post(String.format("/api/status/%d/reports", s1.getStatusID()))
                        .content("{\n" +
                                "    \"description\": \"Pas veseo, hrane ga stanari obližnje zgrade\",\n" +
                                "    \"imagePath\": \"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=1.00xw:0.669xh;0,0.190xh&resize=1200:*\",\n" +
                                "    \"latitude\": 876.987,\n" +
                                "    \"longitude\": 38\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Latitude value out of range! Should be between -90 and 90; "));
    }

    @Test
    @Order(5)
    void addReportWithInvalidImagePath() throws Exception {
        mockMvc.perform(post(String.format("/api/status/%d/reports", s1.getStatusID()))
                        .content("{\n" +
                                "    \"description\": \"Pas veseo, hrane ga stanari obližnje zgrade\",\n" +
                                "    \"imagePath\": \"Image path\",\n" +
                                "    \"latitude\": 43,\n" +
                                "    \"longitude\": 38\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Image path must be a valid URL; "));
    }

    @Test
    @Order(6)
    void getReport() throws Exception {
        mockMvc.perform(get(String.format("/api/reports/%s", r1.getReportID()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(41.263736));
    }

    @Test
    @Order(7)
    void getReportInvalidArgument() throws Exception {
        mockMvc.perform(get(String.format("/api/reports/kk"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Method Argument Type Mismatch Exception"));
    }

    @Test
    @Order(8)
    void getReportInvalidId() throws Exception {
        mockMvc.perform(get(String.format("/api/reports/1758722e-0e14-4f9f-80bd-bf41a862597"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    @Order(9)
    void addReportInvalidStatusId() throws Exception {
        mockMvc.perform(post(String.format("/api/status/4535/reports"))
                        .content("{\n" +
                                "    \"description\": \"Pas veseo, hrane ga stanari obližnje zgrade\",\n" +
                                "    \"imagePath\": \"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=1.00xw:0.669xh;0,0.190xh&resize=1200:*\",\n" +
                                "    \"latitude\": 41,\n" +
                                "    \"longitude\": 38\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.error").value("Not found"))
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    @Order(10)
    void deleteReportInvalidId() throws Exception {
        String id = "1758722e-0e14-4f9f-80bd-0bf41a862597";
        mockMvc.perform(delete(String.format("/api/reports/%s", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(jsonPath("$.message").value("Report with id = " + id + " does not exist!"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

    @Test
    @Order(11)
    void deleteReport() throws Exception {
        UUID id = r3.getReportID();
        mockMvc.perform(delete(String.format("/api/reports/%s", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    @Order(12)
    void deleteReportsByStatus() throws Exception {
        Integer id = s2.getStatusID();
        mockMvc.perform(delete(String.format("/api/status/%s/reports", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }



    /* public abstract List<Report> getAllReportsByStatusId(Integer statusId);
    public abstract Report getReportsById(UUID id);
    public abstract List<Report> getAllReports();
    public abstract Report createReport(Integer statusId, Report reportRequest);
    public abstract void deleteReport(UUID id);
    public abstract void deleteAllReportsOfStatus(Integer statusId);*/
//}

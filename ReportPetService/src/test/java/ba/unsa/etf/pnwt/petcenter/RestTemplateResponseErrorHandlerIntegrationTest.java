package ba.unsa.etf.pnwt.petcenter;

import ba.unsa.etf.pnwt.petcenter.Exceptions.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Model.Center;
import ba.unsa.etf.pnwt.petcenter.Model.Form;
import ba.unsa.etf.pnwt.petcenter.Repository.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Service.CenterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { NotFoundException.class, Form.class })
@RestClientTest
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestTemplateResponseErrorHandlerIntegrationTest {

    private MockRestServiceServer server;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CenterRepository centerRepo;

    private Center c1;

    @BeforeAll
    public void setup(){
        c1 = new Center("Tuzla",9999);
        centerRepo.save(c1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject centerJsonObject = new JSONObject();
        try {
            centerJsonObject.put("capacity", 12);
            centerJsonObject.put("address", "Safeta Zajke 111");
            centerJsonObject.put("city", "Sarajevo");
            centerJsonObject.put("phoneNumber", "033547896");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request =
                new HttpEntity<String>(centerJsonObject.toString(), headers);

        String centarResultAsJsonStr =
                restTemplate.postForObject("http://localhost:8081/api/center", request, String.class);

        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(centarResultAsJsonStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void init() {
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @Order(1)
    public void  givenRemoteApiCall_when404Error_thenThrowNotFound() {

        this.server
                .expect(ExpectedCount.once(), requestTo("http://pet-center-management-service/api/center/free/" + c1.getId()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
    }


}

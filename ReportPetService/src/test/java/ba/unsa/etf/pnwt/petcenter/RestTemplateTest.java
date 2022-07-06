package ba.unsa.etf.pnwt.petcenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(MockitoJUnitRunner.class)
public class RestTemplateTest {
    @Autowired
    RestTemplate restTemplate = new RestTemplate();
    MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

    @Test
    public void testCheckCapacityRestTemplate() throws URISyntaxException {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("http://pet-center-management-service/api/center/free/1")))
                .andExpect(method(HttpMethod.GET));
        //.andRespond(withStatus(HttpStatus.OK)
        //       .contentType(MediaType.APPLICATION_JSON)
        // );

        mockServer.verify();
    }
}

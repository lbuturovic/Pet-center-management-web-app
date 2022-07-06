package ba.unsa.etf.pnwt.petcenter;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ReservationServiceApplicationTests {

    @Test
    public void testAddReservation() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + "8080" + "/reservation";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{" +"\"startDate\": \"1998-12-12 12:12:12\",\"endDate\": \"2020-12-12 12:12:12\","+
                        "\"center\":{\"id\": 95},\"service\": {\"id\": 96},\"user\": {\"id\": 97}}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testFindReservations() throws URISyntaxException, IOException
    {

        final String baseUrl = "http://localhost:" + "8080" + "/reservation";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{" +"\"startDate\": \"1998-12-12 12:12:12\",\"endDate\": \"2020-12-12 12:12:12\","+
                        "\"center\":{\"id\": 95},\"service\": {\"id\": 96},\"user\": {\"id\": 97}}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl1 = "http://localhost:" + "8080" + "/reservations";
        URI uri = new URI(baseUrl1);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        Assertions.assertTrue(result.getBody().contains("reservationId"));
    }


    @Test
    public void testBadRequest1() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + "8080" + "/reservation";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{" +"\"startDate\": \"1998-12-12 12:12:12\",\"endDate\": \"1997-12-12 12:12:12\","+
                        "\"center\":{\"id\": 95},\"service\": {\"id\": 96},\"user\": {\"id\": 97}}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testBadRequest2() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + "8080" + "/reservation";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{" +"\"startDate\": \"1998-12-12 12:12:12\",\"endDate\": \"1999-12-12 12:12:12\","+
                        "\"center\":{\"id\": 95},\"service\": {\"id\": 96}}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }
}

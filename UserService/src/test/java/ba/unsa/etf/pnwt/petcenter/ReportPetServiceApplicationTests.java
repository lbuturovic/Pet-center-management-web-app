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

@SpringBootTest
class ReportPetServiceApplicationTests {

    /*@Test
    public void testAddStatus() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + "8080/api" + "/status";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{" +"\"name\": \"Ljubimac udomljen\"}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(201, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testFindStatus() throws URISyntaxException
    {
        String statusID = "1";
        final String baseUrl = "http://localhost:8080/api" + "/status/" + statusID;
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        //Verify request succeed
        Assertions.assertTrue(result.getBody().contains("statusID"));
    }


    @Test
    public void testAddStatus2() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + "8080/api" + "/status";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{" +"\"name\": \"U skloništu\"}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(201, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testPutStatus() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        String id = "2";
        final String baseUrl = "http://localhost:" + "8080/api" + "/status/" + id;
        HttpUriRequest request = RequestBuilder.create("PUT")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{" +"\"name\": \"Ljubimac u skloništu\"}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testFindAllStatus() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/api" + "/status";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        Assertions.assertTrue(result.getBody().contains("statusID"));
    }

    @Test
    public void testStatusByID() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/api" + "/status/2";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        Assertions.assertTrue(result.getBody().contains("statusID"));
    }

    @Test
    public void testAddReport() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + "8080/api" + "/status/1/reports";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{\"description\": \"Pas djeluje dehidrirano\",\n" +
                        "    \"imagePath\": \"image_path\",\n" +
                        "    \"location\": \"Geteova 18\",\n" +
                        "    \"dateTime\": \"2022-03-29T13:34:00.000\"}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(201, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testAddReportFail() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + "8080/api" + "/status/1/reports";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{\"description\": \"Pas djeluje dehidrirano\",\n" +
                        "    \"imagePath\": \"image_path\",\n" +
                        "    \"location\": \"\",\n" +
                        "    \"dateTime\": \"2022-03-29T13:34:00.000\"}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }
*/


}

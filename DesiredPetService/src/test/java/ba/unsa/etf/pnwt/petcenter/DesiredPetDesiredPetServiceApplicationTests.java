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
class DesiredPetDesiredPetServiceApplicationTests {
    @Test
    public void testAdd() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + "8080" + "/desired-pet";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{" +"\"species\": \"DOG\",\"age\": \"Junior\","+
                        "\"race\":\"pudla\"}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testFindDesiredPet() throws URISyntaxException,IOException
    {
        //first add one pet in db
        final String baseUrl = "http://localhost:" + "8080" + "/desired-pet";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{" +"\"species\": \"DOG\",\"age\": \"Junior\","+
                        "\"race\":\"pudla\"}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl1 = "http://localhost:" + "8080" + "/desired-pets";
        URI uri = new URI(baseUrl1);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        Assertions.assertTrue(result.getBody().contains("id"));
    }


    @Test
    public void testBadRequest1() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + "8080" + "/desired-pet";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{"+
                        "\"race\":\"pudla\"}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testBadRequest2() throws URISyntaxException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + "8080" + "/desired-pet";
        HttpUriRequest request = RequestBuilder.create("POST")
                .setUri(baseUrl)
                .setEntity(new StringEntity("{" +"\"species\": \"DOG\","+
                        "\"race\":\"pudla\"}", ContentType.APPLICATION_JSON))
                .build();

        CloseableHttpResponse response =  HttpClientBuilder.create().build().execute(request);
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }
}

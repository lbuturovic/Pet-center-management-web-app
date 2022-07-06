package ba.unsa.etf.pnwt.petcenter.Exceptions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.NotActiveException;
import java.util.Map;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(httpResponse.getBody());
            JsonNode error = root.path("error");
            JsonNode message = root.path("message");
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                if (error.asText().equals("Not found")) throw new NotFoundException(message.asText());
                else throw new NotFoundException("Nije nadjeno");
            }

            throw new ApiError(error.asText(), message.asText());


        }
    }

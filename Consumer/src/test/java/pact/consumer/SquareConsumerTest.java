package pact.consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import consumer.example.model.SquareDetailRequest;
import consumer.example.model.SquareOperationResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "Square Operation Service", pactVersion = PactSpecVersion.V3)
class SquareConsumerTest {

    SquareDetailRequest request = new SquareDetailRequest(12D);

    @Pact(consumer = "Shape Consumer Service", provider = "Square Operation Service")
    public RequestResponsePact squareAreaPact(PactDslWithProvider builder) {

        return builder
            .given("Calculate area")
            .uponReceiving("Request to calculate area of square")
            .path("/provider/square/AREA")
            .method("POST")
            .headers(Map.of("Content-Type", "application/json"))
            .body("{\"side\": 12.0}")
            .willRespondWith()
            .status(200)
            .body("{\"result\": 144.0}")
            .toPact();
    }

    @Test
    @PactTestFor(providerName = "Square Operation Service", pactMethod = "squareAreaPact")
    void shouldGetSquareArea(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        HttpHeaders headers = new HttpHeaders();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(APPLICATION_JSON, APPLICATION_OCTET_STREAM));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        HttpEntity<SquareDetailRequest> httpRequest = new HttpEntity<>(request, headers);

        ResponseEntity<SquareOperationResponse> responseEntity = restTemplate.exchange(
            mockServer.getUrl() + "/provider/square/AREA", POST, httpRequest, SquareOperationResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.getBody().result()).isEqualTo(144D);
    }

    @Pact(consumer = "Shape Consumer Service", provider = "Square Operation Service")
    public RequestResponsePact squarePerimeterPact(PactDslWithProvider builder) {

        return builder
            .given("Calculate perimeter")
            .uponReceiving("Request to calculate perimeter of square")
            .path("/provider/square/PERIMETER")
            .method("POST")
            .headers(Map.of("Content-Type", "application/json"))
            .body("{\"side\": 12.0}")
            .willRespondWith()
            .status(200)
            .body("{\"result\": 48.0}")
            .toPact();
    }

    @Test
    @PactTestFor(providerName = "Square Operation Service", pactMethod = "squarePerimeterPact")
    void shouldGetSquarePerimeter(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        HttpHeaders headers = new HttpHeaders();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(APPLICATION_JSON, APPLICATION_OCTET_STREAM));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        HttpEntity<SquareDetailRequest> httpRequest = new HttpEntity<>(request, headers);

        ResponseEntity<SquareOperationResponse> responseEntity = restTemplate.exchange(
            mockServer.getUrl() + "/provider/square/PERIMETER", POST, httpRequest, SquareOperationResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        assertThat(responseEntity.getBody().result()).isEqualTo(48D);
    }
}

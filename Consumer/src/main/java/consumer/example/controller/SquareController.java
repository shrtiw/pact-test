package consumer.example.controller;

import static org.springframework.http.HttpMethod.POST;

import consumer.example.model.AllSquareOperationResponse;
import consumer.example.model.SquareDetailRequest;
import consumer.example.model.SquareOperationResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
public class SquareController {

    private final RestTemplate restTemplate;
    private static final String PROVIDER_SERVICE_BASE_URL = "http://localhost:8080/provider";

    public SquareController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/square/{side}")
    public ResponseEntity<AllSquareOperationResponse> getAllSquareOperationResponse(@PathVariable double side) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        SquareDetailRequest request = new SquareDetailRequest(side);
        HttpEntity<SquareDetailRequest> httpRequest = new HttpEntity<>(request, headers);

        double perimeter = restTemplate.exchange(
            PROVIDER_SERVICE_BASE_URL+"/square/PERIMETER", POST, httpRequest,
            SquareOperationResponse.class).getBody().result();
        double area = restTemplate.exchange(
            PROVIDER_SERVICE_BASE_URL+"/square/AREA", POST, httpRequest, SquareOperationResponse.class).getBody().result();

        return ResponseEntity.ok(new AllSquareOperationResponse(perimeter, area));
    }
}

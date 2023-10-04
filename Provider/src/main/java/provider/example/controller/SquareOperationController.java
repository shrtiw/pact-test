package provider.example.controller;

import static provider.example.constant.SquareOperations.AREA;
import static provider.example.constant.SquareOperations.PERIMETER;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import provider.example.model.SquareDetail;
import provider.example.model.SquareOperationResponse;

@RestController
@RequestMapping("/provider")
public class SquareOperationController {

    @PostMapping("/square/{operation}")
    public ResponseEntity<SquareOperationResponse> getSquareOperationResponse(
        @PathVariable String operation, @RequestBody SquareDetail squareDetail) {

        if (PERIMETER.name().equals(operation)) {
            return ResponseEntity.ok(
                new SquareOperationResponse(PERIMETER.getResult(squareDetail.side())));
        } else if (AREA.name().equals(operation)) {
            return ResponseEntity.ok(new SquareOperationResponse(AREA.getResult(squareDetail.side())));
        }

        return ResponseEntity.ok(null);
    }
}

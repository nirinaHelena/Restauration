package school.hei.restoration.endpoint.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.restoration.Service.StockService;
import school.hei.restoration.repository.model.Movement;
import school.hei.restoration.repository.model.Restaurant;
import school.hei.restoration.repository.model.Stock;

import java.time.Instant;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    @PostMapping("/supply")
    public ResponseEntity<Stock> supply(@RequestBody Stock stock){
        Stock savedStock = stockService.supply(stock);
        return new ResponseEntity<>(savedStock, HttpStatus.CREATED);
    }
    @GetMapping("/get-stock-movement-details-at-date")
    public ResponseEntity<List<Movement>> getStockDetailsMovement(@RequestBody Restaurant restaurant,
                                                                  @RequestParam String beginString,
                                                                  @RequestParam String endString){
        Instant begin = Instant.parse(beginString);
        Instant end = Instant.parse(endString);
        List<Movement> movements = stockService.getStockDetailsMovementAtDate(restaurant, begin, end);
        return new ResponseEntity<>(movements, HttpStatus.OK);
    }
}

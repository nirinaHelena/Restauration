package school.hei.restoration.endpoint.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.restoration.model.Movement;
import school.hei.restoration.model.Restaurant;
import school.hei.restoration.model.Stock;
import school.hei.restoration.service.StockService;

import java.time.Instant;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    @PostMapping("/supply")
    public ResponseEntity<Stock> supply(@RequestBody Stock stock) {
        Stock savedStock = stockService.supply(stock);
        return new ResponseEntity<>(savedStock, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Movement>> getStockDetailsMovement(@RequestBody(required = true) Restaurant restaurant,
                                                                  @RequestParam(required = false) Instant begin,
                                                                  @RequestParam(required = false) Instant end) {
        List<Movement> movements = stockService.getStockDetails(restaurant, begin, end);
        return new ResponseEntity<>(movements, HttpStatus.OK);
    }
}

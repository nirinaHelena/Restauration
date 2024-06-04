package school.hei.restoration.endpoint.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.restoration.Service.StockService;
import school.hei.restoration.repository.MovementRepo;
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
    private final MovementRepo movementRepo;

    @PostMapping("/supply")
    public ResponseEntity<Stock> supply(@RequestBody Stock stock){
        Stock savedStock = stockService.supply(stock);
        return new ResponseEntity<>(savedStock, HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<List<Movement>> getStockDetailsMovement(@RequestBody(required = true) Restaurant restaurant,
                                                                  @RequestParam(required = false) Instant begin,
                                                                  @RequestParam(required = false) Instant end){
        if (begin == null  || end == null){
            List<Movement> movements = movementRepo.findAll(restaurant);
            return new ResponseEntity<>(movements, HttpStatus.OK);
        }else {
            List<Movement> movements = stockService.getStockDetailsMovementAtDate(restaurant, begin, end);
            return new ResponseEntity<>(movements, HttpStatus.OK);
        }
    }
}

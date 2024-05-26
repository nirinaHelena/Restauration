package school.hei.restoration.endpoint.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.restoration.Service.StockService;
import school.hei.restoration.repository.model.Stock;


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
}

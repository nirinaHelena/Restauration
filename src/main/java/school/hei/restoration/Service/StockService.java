package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.StockRepo;
import school.hei.restoration.repository.model.Stock;

import java.time.Instant;

@Service
@AllArgsConstructor
public class StockService {
    private StockRepo stockRepo;
    public void supply(Stock stock){
        double newQuantity = stockRepo.currentQuantity(stock.restaurant(), stock.ingredientTemplate())
                + stock.quantity();
        stockRepo.save(Instant.now(), newQuantity);
    }
}

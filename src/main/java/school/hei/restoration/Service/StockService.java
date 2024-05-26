package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.MovementRepo;
import school.hei.restoration.repository.StockRepo;
import school.hei.restoration.repository.model.Movement;
import school.hei.restoration.repository.model.MovementType;
import school.hei.restoration.repository.model.Stock;

import java.time.Instant;
import java.util.List;


@Service
@AllArgsConstructor
public class StockService {
    private final MovementRepo movementRepo;
    private StockRepo stockRepo;
    public Stock supply(Stock stock){
        try {
            Stock currentStock = stockRepo.currentQuantity(stock.restaurant(), stock.ingredientTemplate());
            double newQuantity = currentStock.quantity() + stock.quantity();
            Stock toSave = new Stock(stock.id(),stock.restaurant(),stock.ingredientTemplate(), stock.date(), newQuantity);
            stockRepo.save(toSave);

            Movement movement = new Movement(
                    1,
                    stock.date(),
                    stock.ingredientTemplate(),
                    MovementType.SUPPLY,
                    stock.quantity()
            );
            movementRepo.save(movement);
            return currentStock;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Movement> getStockDetailsAtDate(Instant begin, Instant end){
        return movementRepo.getAllMovementAtDate(begin, end);
    }
}

package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.MovementRepo;
import school.hei.restoration.repository.StockRepo;
import school.hei.restoration.repository.model.Movement;
import school.hei.restoration.repository.model.MovementType;
import school.hei.restoration.repository.model.Restaurant;
import school.hei.restoration.repository.model.Stock;

import java.time.Instant;
import java.util.List;

import static school.hei.restoration.repository.model.MovementType.SUPPLY;


@Service
@AllArgsConstructor
public class StockService {
    private final MovementRepo movementRepo;
    private StockRepo stockRepo;
    public Stock supply(Stock stock){
        try {
            double newQuantity;
            if (stockRepo.currentQuantity(stock.restaurant(), stock.ingredientTemplate()) != null){
                Stock currentStock = stockRepo.currentQuantity(stock.restaurant(), stock.ingredientTemplate());
                newQuantity = currentStock.quantity() + stock.quantity();
            }else{
                newQuantity = stock.quantity();
            }

            Stock toSave = new Stock(stock.id(),stock.restaurant(),stock.ingredientTemplate(), Instant.now(), newQuantity);
            stockRepo.save(toSave);

            Movement movement = new Movement(
                    1,
                    stock.date(),
                    stock.ingredientTemplate(),
                    SUPPLY,
                    stock.quantity(),
                    stock.restaurant()
            );
            movementRepo.save(movement);
            return toSave;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Movement> getStockDetailsMovementAtDate(Restaurant restaurant, Instant begin, Instant end){
        return movementRepo.getAllMovementAtDate(restaurant, begin, end);
    }
}

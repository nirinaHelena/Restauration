package school.hei.restoration.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.MovementRepo;
import school.hei.restoration.repository.StockRepo;
import school.hei.restoration.model.Movement;
import school.hei.restoration.model.MovementType;
import school.hei.restoration.model.Restaurant;
import school.hei.restoration.model.Stock;

import java.time.Instant;
import java.util.List;


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
                    MovementType.SUPPLY,
                    stock.quantity(),
                    stock.restaurant()
            );
            movementRepo.save(movement);
            return toSave;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Movement> getStockDetails(Restaurant restaurant, Instant begin, Instant end){
        if (begin == null  || end == null) {
            return movementRepo.findAll(restaurant);
        }
        return movementRepo.getAllMovementAtDate(restaurant, begin, end);
    }
}

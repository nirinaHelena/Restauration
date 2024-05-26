package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.StockRepo;
import school.hei.restoration.repository.model.Stock;


@Service
@AllArgsConstructor
public class StockService {
    private StockRepo stockRepo;
    public Stock supply(Stock stock){
        Stock currentStock = stockRepo.currentQuantity(stock.restaurant(), stock.ingredientTemplate());
        double newQuantity = currentStock.quantity() + stock.quantity();
        Stock toSave = new Stock(stock.id(),stock.restaurant(),stock.ingredientTemplate(), stock.date(), newQuantity);
        stockRepo.save(toSave);
        return currentStock;
    }
}

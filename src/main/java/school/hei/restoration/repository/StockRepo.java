package school.hei.restoration.repository;

import org.springframework.stereotype.Repository;
import school.hei.restoration.NotImplemented;
import school.hei.restoration.repository.model.IngredientTemplate;
import school.hei.restoration.repository.model.Restaurant;
import school.hei.restoration.repository.model.Stock;

import java.time.Instant;

@Repository
public class StockRepo {
    public void save(Instant date, double quantity){
        return new NotImplemented();
    }
    public double currentQuantity(Restaurant restaurant, IngredientTemplate ingredientTemplate){
        return new NotImplemented();
    }
}
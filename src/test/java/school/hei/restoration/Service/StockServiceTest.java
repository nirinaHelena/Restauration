package school.hei.restoration.Service;

import org.junit.jupiter.api.Test;
import school.hei.restoration.repository.model.*;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StockServiceTest {
    private StockService stockService;

    @Test
    void supply() {
        var piece = new Unity(1, "piece");
        var breadTemplate = new IngredientTemplate(1, "bread", 500, piece);

        var kg = new Unity(2, "kg");
        var sausageTemplate = new IngredientTemplate(2, "sausage", 20_000, kg);

        var litre = new Unity(3, "litre");
        var mayoTemplate = new IngredientTemplate(3, "mayo", 10_000, litre);
        var ketchupTemplate = new IngredientTemplate(4, "ketchup", 5_000, litre);

        var in26mai24 = Instant.parse("2024-05-26T00:00:00.00Z");
        var hotDogPrice = new MenuPrices(1, in26mai24, 3.750);
        var hotDog = new Menu(1, "hot dog", hotDogPrice, Set.of());

        var restaurant = new Restaurant(1, "Ivandry", Set.of(hotDog));
        var breadStock = new Stock(1, restaurant, breadTemplate, in26mai24, 0);
        var sausageStock = new Stock(2, restaurant, sausageTemplate, in26mai24, 0);
        var mayoStock = new Stock(3, restaurant, mayoTemplate, in26mai24, 0);
        var ketchupStock = new Stock(4, restaurant, ketchupTemplate, in26mai24, 0);

        var add20Bread = new Stock(5, restaurant, breadTemplate, in26mai24, 20);
        assertEquals(20, stockService.supply(add20Bread));
    }
}
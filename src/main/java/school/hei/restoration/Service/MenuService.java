package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.*;
import school.hei.restoration.repository.model.*;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {
    private final IngredientRepo ingredientRepo;
    private final MenuRepo menuRepo;
    private final StockRepo stockRepo;
    private final MovementRepo movementRepo;
    private final MenuHistorySaleRepo menuHistorySaleRepo;

    public void save(Menu menu){
        try {
            for (int i = 0; i < menu.getIngredients().size(); i++) {
                Ingredient ingredient = menu.getIngredients().get(i);
                ingredientRepo.save(ingredient);
            }
            menuRepo.save(menu);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Ingredient> getAllMenuIngredient(Menu menu){
        return ingredientRepo.getIngredientByMenu(menu);
    }
    public void addIngredientToAMenu(Ingredient ingredient){
        ingredientRepo.save(ingredient);
    }
    public void modifyAMenuIngredient(Ingredient ingredient){
        ingredientRepo.updateIngredient(ingredient);
    }
    public void deleteMenuIngredient(Ingredient ingredient){
        ingredientRepo.deleteMenuIngredient(ingredient);
    }
    private boolean checkIfIngredientRequiredIsOk(Menu menu, Restaurant restaurant, List<Ingredient> ingredients){
        for (Ingredient ingredient : ingredients) {
            double stock = stockRepo.currentQuantity(restaurant, ingredient.getIngredientTemplate()).quantity();
            if (ingredient.getQuantityRequired() > stock) {
                return false;
            }
        }
        return true;
    }
    public boolean saleMenu(Menu menu, Restaurant restaurant){
        Instant now = Instant.now();
        List<Ingredient> ingredients = ingredientRepo.getIngredientByMenu(menu);
        if (!checkIfIngredientRequiredIsOk(menu, restaurant, ingredients)){
            return false;
        }
        for (Ingredient ingredient : ingredients) {
            movementRepo.save(new Movement(1, now, ingredient.getIngredientTemplate(), MovementType.SALE,
                    ingredient.getQuantityRequired()));
            double currentQuantity = stockRepo.currentQuantity(restaurant, ingredient.getIngredientTemplate()).quantity();
            double quantity = currentQuantity - ingredient.getQuantityRequired();
            stockRepo.save(new Stock(1, restaurant, ingredient.getIngredientTemplate(), now, quantity));
        }
        menuHistorySaleRepo.save(new MenuHistorySale(1, now, menu));
        return true;
    }
}

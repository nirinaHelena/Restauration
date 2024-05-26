package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.IngredientRepo;
import school.hei.restoration.repository.MenuRepo;
import school.hei.restoration.repository.model.Ingredient;
import school.hei.restoration.repository.model.Menu;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {
    private final IngredientRepo ingredientRepo;
    private final MenuRepo menuRepo;

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
    public List<Ingredient> getAllIngredientOfAMenu(Menu menu){
        return ingredientRepo.getIngredientByMenu(menu);
    }
    public void addIngredientToAMenu(Ingredient ingredient){
        ingredientRepo.save(ingredient);
    }
    public void modifyAnIngredientToAMenu(Ingredient ingredient){
        ingredientRepo.updateIngredient(ingredient);
    }
}

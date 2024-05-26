package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.IngredientRepo;
import school.hei.restoration.repository.MenuRepo;
import school.hei.restoration.repository.model.Ingredient;
import school.hei.restoration.repository.model.Menu;

@Service
@AllArgsConstructor
public class MenuService {
    private final IngredientRepo ingredientRepo;
    private final MenuRepo menuRepo;

    public Menu save(Menu menu){
        Menu menuSaved ;
        try {
            for (int i = 0; i < menu.getIngredients().size(); i++) {
                Ingredient ingredient = menu.getIngredients().get(i);
                ingredientRepo.save(ingredient);
            }
            menuSaved = menuRepo.save(menu);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return menuSaved;
    }
}

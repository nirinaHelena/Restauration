package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.IngredientRepo;
import school.hei.restoration.repository.MenuRepo;
import school.hei.restoration.repository.model.Menu;

@Service
@AllArgsConstructor
public class MenuService {
    private final IngredientRepo ingredientRepo;
    private final MenuRepo menuRepo;

    public Menu save(Menu menu){
        Menu menuSaved ;
        try {
            menu.getIngredients().stream()
                    .map(ingredientRepo::save);
            menuSaved = menuRepo.save(menu);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return menuSaved;
    }
}

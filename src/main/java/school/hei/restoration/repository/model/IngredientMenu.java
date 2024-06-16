package school.hei.restoration.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientMenu {
    protected Menu menu;
    protected IngredientTemplate ingredientTemplate;

    public IngredientMenu(IngredientTemplate ingredientTemplate) {
        this.ingredientTemplate = ingredientTemplate;
    }
}

package school.hei.restoration.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientMenu {
    protected Menu menu;
    protected IngredientTemplate ingredientTemplate;
}

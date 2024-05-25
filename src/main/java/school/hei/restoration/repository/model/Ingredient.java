package school.hei.restoration.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class Ingredient {
    private final int id;
    private final Menu menu;
    private final IngredientTemplate ingredientTemplate;
    private final double quantityRequired;
    private final Unity unity;
}

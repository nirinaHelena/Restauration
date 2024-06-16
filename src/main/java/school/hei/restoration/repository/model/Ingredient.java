package school.hei.restoration.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@Data
public final class Ingredient extends IngredientMenu{
    private final int id;
    private final double quantityRequired;

    public Ingredient(Menu menu, IngredientTemplate ingredientTemplate, int id, double quantityRequired) {
        super(menu, ingredientTemplate);
        this.id = id;
        this.quantityRequired = quantityRequired;
    }
}

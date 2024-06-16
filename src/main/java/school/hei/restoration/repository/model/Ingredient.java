package school.hei.restoration.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
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
    public Ingredient(IngredientTemplate ingredientTemplate, int id, double quanityRequired){
        super(ingredientTemplate);
        this.id = id;
        this.quantityRequired = quanityRequired;
    }
}

package school.hei.restoration.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class Ingredient extends IngredientMenu{
    private final int id;
    private final double quantityRequired;
}

package school.hei.restoration.repository.model;

import java.time.Instant;

public record Stock (
        int id,
        Restaurant restaurant,
        IngredientTemplate ingredientTemplate,
        Instant date,
        double quantity){
}

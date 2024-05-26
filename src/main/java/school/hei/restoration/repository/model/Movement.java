package school.hei.restoration.repository.model;

import java.time.Instant;

public record Movement(
        int id,
        Instant date,
        IngredientTemplate ingredientTemplate,
        MovementType movementType,
        double quantity
) {
}

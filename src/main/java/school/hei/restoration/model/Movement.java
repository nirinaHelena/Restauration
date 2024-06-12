package school.hei.restoration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public final class Movement {
    private int id;
    private Instant date;
    private IngredientTemplate ingredientTemplate;
    private MovementType movementType;
    private double quantity;
    private Restaurant restaurant;

    public Movement(Instant date, IngredientTemplate ingredientTemplate, MovementType movementType, double quantity) {
        this.date = date;
        this.ingredientTemplate = ingredientTemplate;
        this.movementType = movementType;
        this.quantity = quantity;
    }
}

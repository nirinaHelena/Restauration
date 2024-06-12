package school.hei.restoration.model;

import lombok.*;

@AllArgsConstructor
@Data
public final class IngredientTemplate {
    private final int id;
    private final String name;
    private double price;
    private final Unity unity;
}

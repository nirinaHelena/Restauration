package school.hei.restoration.repository.model;

import java.time.Instant;

public record Stock (
        int id,
        Restaurant restaurant,
        Ingredient ingredient,
        Instant date,
        double quantity){
}

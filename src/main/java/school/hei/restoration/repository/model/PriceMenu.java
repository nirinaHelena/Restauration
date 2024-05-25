package school.hei.restoration.repository.model;

import java.time.Instant;

public record PriceMenu (
        int id,
        Instant date,
        double price,
        Menu menu){
}

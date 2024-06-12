package school.hei.restoration.model;

import java.time.Instant;

public record MenuHistorySale(
        int id,
        Instant date,
        Menu menu,
        Restaurant restaurant
) {
}

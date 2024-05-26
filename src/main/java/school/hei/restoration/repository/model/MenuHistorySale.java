package school.hei.restoration.repository.model;

import java.time.Instant;

public record MenuHistorySale(
        int id,
        Instant date,
        Menu menu
) {
}

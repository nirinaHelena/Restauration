package school.hei.restoration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public final class MenuPrices{
    private int id;
    private Instant date;
    private final double price;
    private int idMenu;
}
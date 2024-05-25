package school.hei.restoration.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@AllArgsConstructor
@Data
public final class Menu {
    private final int id;
    private final String name;
    @NonNull
    private MenuPrices menuPrices;
    private final Set<Ingredient> ingredients;
}

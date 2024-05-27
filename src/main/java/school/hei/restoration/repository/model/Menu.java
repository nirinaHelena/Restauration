package school.hei.restoration.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public final class Menu {
    private final int id;
    private final String name;
    private MenuPrices menuPrices;
    private List<Ingredient> ingredients;

    public Menu(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Menu(int id, String name, MenuPrices menuPrices) {
        this.id = id;
        this.name = name;
        this.menuPrices = menuPrices;
    }
}

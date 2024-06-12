package school.hei.restoration.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public final class Restaurant {
    private final int id;
    private final String location;
    private Set<Menu> menus;

    public Restaurant(int id, String location) {
        this.id = id;
        this.location = location;
    }
}

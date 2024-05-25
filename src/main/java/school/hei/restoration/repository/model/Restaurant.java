package school.hei.restoration.repository.model;

import java.util.Set;

public record Restaurant (
        int id,
        String location,
        Set<Menu> menus){
}

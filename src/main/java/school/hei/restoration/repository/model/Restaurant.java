package school.hei.restoration.repository.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public final class Restaurant {
    private final int id;
    private final String location;
    private Set<Menu> menus;

    @JsonCreator
    public Restaurant(@JsonProperty("id") int id, @JsonProperty("location") String location) {
        this.id = id;
        this.location = location;
    }
}

package school.hei.restoration.repository.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @BeforeEach
    void setUp() {
        Restaurant newRestaurant = new Restaurant(1, "Ivandry", Set.of());
    }
}
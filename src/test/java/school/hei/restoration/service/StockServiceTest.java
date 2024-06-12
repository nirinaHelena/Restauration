package school.hei.restoration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import school.hei.restoration.model.IngredientTemplate;
import school.hei.restoration.model.Movement;
import school.hei.restoration.model.MovementType;
import school.hei.restoration.model.Restaurant;
import school.hei.restoration.model.Stock;
import school.hei.restoration.model.Unity;
import school.hei.restoration.repository.MovementRepo;
import school.hei.restoration.repository.StockRepo;


import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StockServiceTest {
    @Mock
    private MovementRepo movementRepo;

    @Mock
    private StockRepo stockRepo;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSupplyWithExistingStock() {
        // Given
        Restaurant restaurant = new Restaurant(1, "Ivandry");

        Unity piece = new Unity(1, "piece");
        IngredientTemplate breadTemplate = new IngredientTemplate(1, "bread", 500, piece);

        Stock existingStock = new Stock(1, restaurant, breadTemplate, Instant.now(), 50.0);
        Stock newStock = new Stock(2, restaurant, breadTemplate, Instant.now(), 30.0);

        when(stockRepo.currentQuantity(restaurant, breadTemplate)).thenReturn(existingStock);

        // When
        Stock result = stockService.supply(newStock);

        // Then
        assertEquals(80.0, result.quantity());
        verify(stockRepo).save(any(Stock.class));
        verify(movementRepo).save(any(Movement.class));
    }

    @Test
    void testSupply_withNewStock() {
        // Given
        Restaurant restaurant = new Restaurant(1, "Ivandry");

        Unity piece = new Unity(1, "piece");
        IngredientTemplate breadTemplate = new IngredientTemplate(1, "bread", 500, piece);
        Stock newStock = new Stock(1, restaurant, breadTemplate, Instant.now(), 30.0);

        when(stockRepo.currentQuantity(restaurant, breadTemplate)).thenReturn(null);

        // When
        Stock result = stockService.supply(newStock);

        // Then
        assertEquals(30.0, result.quantity());
        verify(stockRepo).save(any(Stock.class));
        verify(movementRepo).save(any(Movement.class));
    }

    @Test
    public void testGetStockDetails() {
        // Given
        Restaurant restaurant = new Restaurant(1, "Ivandry");

        Instant begin = Instant.parse("2023-01-01T00:00:00Z");
        Instant end = Instant.parse("2023-12-31T23:59:59Z");

        Unity piece = new Unity(1, "piece");
        IngredientTemplate breadTemplate = new IngredientTemplate(1, "bread", 500, piece);
        Movement movement1 = new Movement(1, Instant.parse("2023-05-01T00:00:00Z"),
                breadTemplate, MovementType.SUPPLY, 50.0, restaurant);

        Unity kg = new Unity(2, "kg");
        IngredientTemplate sausageTemplate = new IngredientTemplate(2, "sausage", 500, kg);
        Movement movement2 = new Movement(2, Instant.parse("2023-06-01T00:00:00Z"),
                sausageTemplate, MovementType.SUPPLY, 20.0, restaurant);
        List<Movement> movements = Arrays.asList(movement1, movement2);

        when(movementRepo.getAllMovementAtDate(restaurant, begin, end)).thenReturn(movements);

        // When
        List<Movement> result = stockService.getStockDetails(restaurant, begin, end);

        // Then
        assertEquals(2, result.size());
        assertEquals(movement1, result.get(0));
        assertEquals(movement2, result.get(1));
        verify(movementRepo).getAllMovementAtDate(restaurant, begin, end);
    }
}
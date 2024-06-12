package school.hei.restoration.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import school.hei.restoration.model.Ingredient;
import school.hei.restoration.model.IngredientTemplate;
import school.hei.restoration.model.Menu;
import school.hei.restoration.model.MenuHistorySale;
import school.hei.restoration.model.MenuPrices;
import school.hei.restoration.model.Movement;
import school.hei.restoration.model.Restaurant;
import school.hei.restoration.model.Stock;
import school.hei.restoration.model.Unity;
import school.hei.restoration.repository.*;
import school.hei.restoration.model.dto.AllMenuSaleAtDate;
import school.hei.restoration.model.dto.MenuNumberSale;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MenuServiceTest {
    @Mock
    private IngredientRepo ingredientRepo;

    @Mock
    private MenuRepo menuRepo;

    @Mock
    private StockRepo stockRepo;

    @Mock
    private MovementRepo movementRepo;

    @Mock
    private MenuHistorySaleRepo menuHistorySaleRepo;

    @Mock
    private RestaurantRepo restaurantRepo;

    @Mock
    private MenuPricesRepo menuPricesRepo;

    @InjectMocks
    private MenuService menuService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        // Given
        Menu menu = new Menu(1, "hot dog");

        Unity piece = new Unity(1, "piece");
        IngredientTemplate breadTemplate = new IngredientTemplate(1, "bread", 500, piece);
        Ingredient ingredient = new Ingredient(1, menu, breadTemplate, 1);

        menu.setIngredients(Collections.singletonList(ingredient));
        Instant now = Instant.now();
        MenuPrices menuPrices = new MenuPrices(1, now, 3.750, 1);
        menu.setMenuPrices(menuPrices);

        // When
        Menu result = menuService.save(menu);

        // Then
        assertEquals(menu, result);
        verify(ingredientRepo, times(1)).save(ingredient);
        verify(menuPricesRepo, times(1)).save(menuPrices);
        verify(menuRepo, times(1)).save(menu);
    }
    @Test
    public void testGetAllMenuIngredient() {
        // Given
        Menu menu = new Menu(1, "hot dog");

        Unity piece = new Unity(1, "piece");
        IngredientTemplate breadTemplate = new IngredientTemplate(1, "bread", 500, piece);
        Ingredient ingredient = new Ingredient(1, menu, breadTemplate, 1);
        List<Ingredient> ingredients = Collections.singletonList(ingredient);
        when(ingredientRepo.getIngredientByMenu(menu)).thenReturn(ingredients);

        // When
        List<Ingredient> result = menuService.getAllMenuIngredient(menu.getId());

        // Then
        assertEquals(ingredients, result);
        verify(ingredientRepo, times(1)).getIngredientByMenu(menu);
    }
    @Test
    public void testAddIngredientToAMenu() {
        // Given
        Menu menu = new Menu(1, "hot dog");

        Unity piece = new Unity(1, "piece");
        IngredientTemplate breadTemplate = new IngredientTemplate(1, "bread", 500, piece);
        Ingredient ingredient = new Ingredient(1, menu, breadTemplate, 1);


        // When
        Ingredient result = menuService.addIngredientToAMenu(ingredient);

        // Then
        assertEquals(ingredient, result);
        verify(ingredientRepo, times(1)).save(ingredient);
    }
    @Test
    public void testModifyAMenuIngredient() {
        // Given
        Menu menu = new Menu(1, "hot dog");

        Unity piece = new Unity(1, "piece");
        IngredientTemplate breadTemplate = new IngredientTemplate(1, "bread", 500, piece);
        Ingredient ingredient = new Ingredient(1, menu, breadTemplate, 1);


        // When
        Ingredient result = menuService.modifyAMenuIngredient(ingredient);

        // Then
        assertEquals(ingredient, result);
        verify(ingredientRepo, times(1)).save(ingredient);
    }
    @Test
    public void testDeleteMenuIngredient() {
        // Given
        Menu menu = new Menu(1, "hot dog");

        Unity piece = new Unity(1, "piece");
        IngredientTemplate breadTemplate = new IngredientTemplate(1, "bread", 500, piece);
        Ingredient ingredient = new Ingredient(1, menu, breadTemplate, 1);


        // When
        List<Ingredient> result = menuService.deleteMenuIngredient(ingredient.getMenu().getId(), ingredient.getId());

        // Then
        List<Ingredient> noIngredient = new ArrayList<>();
        assertEquals(noIngredient, result);
        verify(ingredientRepo, times(1)).deleteMenuIngredient(ingredient);
    }
    @Test
    public void testSaleMenu() {
        // Given
        Menu menu = new Menu(1, "hot dog");

        Unity piece = new Unity(1, "piece");
        IngredientTemplate breadTemplate = new IngredientTemplate(1, "bread", 500, piece);
        Ingredient bread = new Ingredient(1, menu, breadTemplate, 1);


        Ingredient breadSet = new Ingredient(1, menu, breadTemplate, 2);
        List<Ingredient> ingredients = Collections.singletonList(breadSet);
        menu.setIngredients(ingredients);

        Restaurant restaurant = new Restaurant(1, "Ivandry");
        Stock currentStock = new Stock(1, restaurant, bread.getIngredientTemplate(), Instant.now(), 20.0);

        when(ingredientRepo.getIngredientByMenu(menu)).thenReturn(ingredients);
        when(stockRepo.currentQuantity(restaurant, bread.getIngredientTemplate())).thenReturn(currentStock);

        // When
        Menu result = menuService.saleMenu(menu, restaurant);

        // Then
        assertEquals(menu, result);
        verify(movementRepo, times(1)).save(any(Movement.class));
        verify(stockRepo, times(1)).save(any(Stock.class));
        verify(menuHistorySaleRepo, times(1)).save(any(MenuHistorySale.class));
    }
    @Test
    public void testGetAllMenuSaleAtDate() {
        // Given
        Instant begin = Instant.parse("2023-01-01T00:00:00Z");
        Instant end = Instant.parse("2023-12-31T23:59:59Z");
        Restaurant restaurant = new Restaurant(1, "Ivandry");
        Menu menu = new Menu(1, "hot dog");
        MenuPrices menuPrices = new MenuPrices(1, begin, 100.0, 1);
        menu.setMenuPrices(menuPrices);
        List<Menu> menus = Collections.singletonList(menu);
        List<Restaurant> restaurants = Collections.singletonList(restaurant);
        when(menuRepo.findAll()).thenReturn(menus);
        when(restaurantRepo.findAll()).thenReturn(restaurants);
        when(menuHistorySaleRepo.countMenuSalePerMenu(any(Restaurant.class), any(Menu.class))).thenReturn(10);

        // When
        List<AllMenuSaleAtDate> result = menuService.getAllMenuSaleAtDate(begin, end);

        // Then
        assertEquals(1, result.size());
        AllMenuSaleAtDate allMenuSaleAtDate = result.getFirst();
        assertEquals(restaurant, allMenuSaleAtDate.getRestaurant());
        List<MenuNumberSale> menuNumberSales = allMenuSaleAtDate.getMenuNumberSales();
        assertEquals(1, menuNumberSales.size());
        MenuNumberSale menuNumberSale = menuNumberSales.getFirst();
        assertEquals(menu, menuNumberSale.menu());
        assertEquals(10, menuNumberSale.numberOfMenuSale());
        assertEquals(1000.0, menuNumberSale.amountOfMenuSale(), 0.01);
        verify(menuRepo, times(1)).findAll();
        verify(restaurantRepo, times(1)).findAll();
        verify(menuHistorySaleRepo, times(1)).countMenuSalePerMenu(any(Restaurant.class), any(Menu.class));
    }

}
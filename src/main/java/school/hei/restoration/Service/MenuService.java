package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.*;
import school.hei.restoration.repository.dto.AllMenuSaleAtDate;
import school.hei.restoration.repository.dto.MenuNumberSale;
import school.hei.restoration.repository.model.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {
    private final IngredientRepo ingredientRepo;
    private final MenuRepo menuRepo;
    private final StockRepo stockRepo;
    private final MovementRepo movementRepo;
    private final MenuHistorySaleRepo menuHistorySaleRepo;
    private final RestaurantRepo restaurantRepo;
    private final MenuPricesRepo menuPricesRepo;

    public Menu save(Menu menu){
        try {
            for (int i = 0; i < menu.getIngredients().size(); i++) {
                Ingredient ingredient = menu.getIngredients().get(i);
                ingredientRepo.save(ingredient);
            }
            menuPricesRepo.save(menu.getMenuPrices());
            menuRepo.save(menu);
            return menu;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Ingredient> getAllMenuIngredient(int idMenu){
        Menu menu = menuRepo.getMenuById(idMenu);
        return ingredientRepo.getIngredientByMenu(menu);
    }
    public Ingredient addIngredientToAMenu(Ingredient ingredient){
        ingredientRepo.save(ingredient);
        return ingredient;
    }
    public Ingredient modifyAMenuIngredient(Ingredient ingredient){
        ingredientRepo.save(ingredient);
        return ingredient;
    }
    public void deleteMenuIngredient(int idMenu, int idIngredient){
        ingredientRepo.deleteMenuIngredient(ingredientRepo.getByIdAndMenu(idMenu, idIngredient));
    }
    private void checkIfIngredientRequiredIsOk( Restaurant restaurant, List<Ingredient> ingredients){
        for (Ingredient ingredient : ingredients) {
            double stock = stockRepo.currentQuantity(restaurant, ingredient.getIngredientTemplate()).quantity();
            if (ingredient.getQuantityRequired() > stock) {
                throw new RuntimeException("missing ingredients");
            }
        }
    }
    public Menu saleMenu(Menu menu, Restaurant restaurant){
        Instant now = Instant.now();
        List<Ingredient> ingredients = ingredientRepo.getIngredientByMenu(menu);
        checkIfIngredientRequiredIsOk(restaurant, ingredients);
        for (Ingredient ingredient : ingredients) {
            movementRepo.save(new Movement(1, now, ingredient.getIngredientTemplate(), MovementType.SALE,
                    ingredient.getQuantityRequired(), restaurant));
            double currentQuantity = stockRepo.currentQuantity(restaurant, ingredient.getIngredientTemplate()).quantity();
            double quantity = currentQuantity - ingredient.getQuantityRequired();
            stockRepo.save(new Stock(1, restaurant, ingredient.getIngredientTemplate(), now, quantity));
        }
        menuHistorySaleRepo.save(new MenuHistorySale(1, now, menu, restaurant));
        return menu;
    }
    public List<AllMenuSaleAtDate> getAllMenuSaleAtDate(Instant begin, Instant end){
        List<AllMenuSaleAtDate> allMenuSaleAtDates = new ArrayList<>();
        List<Menu> menus = menuRepo.findAll();
        List<Restaurant> restaurants = restaurantRepo.findAll();

        for (Restaurant restaurant : restaurants) {
            List<MenuNumberSale> menuNumberSales = new ArrayList<>();
            for(Menu menu : menus){
                int numberOfMenuSale = menuHistorySaleRepo.countMenuSalePerMenuAtDate(restaurant, menu, begin, end);
                double amountOfMenuSale = menu.getMenuPrices().getPrice() * numberOfMenuSale;
                menuNumberSales.add(new MenuNumberSale(menu, numberOfMenuSale, amountOfMenuSale));
            }
            allMenuSaleAtDates.add(new AllMenuSaleAtDate(restaurant, menuNumberSales));
        }
        return allMenuSaleAtDates;
    }
    public List<AllMenuSaleAtDate> getAllMenuSale(){
        List<AllMenuSaleAtDate> allMenuSaleAtDates = new ArrayList<>();
        List<Menu> menus = menuRepo.findAll();
        List<Restaurant> restaurants = restaurantRepo.findAll();

        for (Restaurant restaurant : restaurants) {
            List<MenuNumberSale> menuNumberSales = new ArrayList<>();
            for(Menu menu : menus){
                int numberOfMenuSale = menuHistorySaleRepo.countMenuSalePerMenu(restaurant, menu);
                double amountOfMenuSale = menu.getMenuPrices().getPrice() * numberOfMenuSale;
                menuNumberSales.add(new MenuNumberSale(menu, numberOfMenuSale, amountOfMenuSale));
            }
            allMenuSaleAtDates.add(new AllMenuSaleAtDate(restaurant, menuNumberSales));
        }
        return allMenuSaleAtDates;
    }
    public List<AllMenuSaleAtDate> allMenuSale(Instant begin, Instant end){
        if (begin == null || end == null){
            List<AllMenuSaleAtDate> allMenuSaleAtDates = getAllMenuSale();
            return allMenuSaleAtDates;
        }else {
            List<AllMenuSaleAtDate> allMenuSaleAtDates = getAllMenuSaleAtDate(begin, end);
            return allMenuSaleAtDates;
        }
    }
}

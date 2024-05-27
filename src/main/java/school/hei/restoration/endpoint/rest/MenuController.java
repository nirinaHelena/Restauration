package school.hei.restoration.endpoint.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.restoration.Service.MenuService;
import school.hei.restoration.repository.MenuRepo;
import school.hei.restoration.repository.model.Ingredient;
import school.hei.restoration.repository.model.Menu;
import school.hei.restoration.repository.model.Restaurant;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;
    private final MenuRepo menuRepo;

    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody Menu menu){
        menuService.save(menu);
        return new ResponseEntity<>(true,HttpStatus.CREATED);
    }
    @GetMapping("/get-menu-ingredient/{idMenu}")
    public ResponseEntity<List<Ingredient>> getMenuIngredient(@PathVariable int idMenu){
        List<Ingredient> ingredients = menuService.getAllMenuIngredient(menuRepo.getMenuById(idMenu));
        if (ingredients != null) {
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add-ingredient-to-menu")
    public ResponseEntity<Boolean> addIngredientToMenu(@RequestBody Ingredient ingredient){
        menuService.addIngredientToAMenu(ingredient);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete-ingredient-to-menu")
    public ResponseEntity<Boolean> deleteIngredientToMenu(@RequestBody Ingredient ingredient){
        menuService.deleteMenuIngredient(ingredient);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    @PutMapping("/update-ingredient-to-menu")
    public ResponseEntity<Boolean> updateIngredientToMenu(@RequestBody Ingredient ingredient){
        menuService.modifyAMenuIngredient(ingredient);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
    @PostMapping("/sale/{idMenu}")
    public ResponseEntity<Boolean> saleMenu(@RequestBody Restaurant restaurant,
                                            @PathVariable int idMenu){
        menuService.saleMenu(menuRepo.getMenuById(idMenu), restaurant);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
}

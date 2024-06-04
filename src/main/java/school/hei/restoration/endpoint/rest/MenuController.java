package school.hei.restoration.endpoint.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.restoration.Service.MenuService;
import school.hei.restoration.repository.MenuRepo;
import school.hei.restoration.repository.dto.AllMenuSaleAtDate;
import school.hei.restoration.repository.model.Ingredient;
import school.hei.restoration.repository.model.Menu;
import school.hei.restoration.repository.model.Restaurant;

import java.time.Instant;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;
    private final MenuRepo menuRepo;

    @PutMapping
    public ResponseEntity<Menu> save(@RequestBody Menu menu){
        Menu saved = menuService.save(menu);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }
    @GetMapping("/{idMenu}/ingredients")
    public ResponseEntity<List<Ingredient>> getIngredientByMenu(@PathVariable("idMenu") int idMenu){
        List<Ingredient> ingredients = menuService.getAllMenuIngredient(idMenu);
        if (!ingredients.isEmpty()) {
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/ingredients")
    public ResponseEntity<Ingredient> addIngredientToMenu(@RequestBody Ingredient ingredient){
        Ingredient added = menuService.addIngredientToAMenu(ingredient);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }
    @DeleteMapping("/{idMenu}/ingredients/{idIngredient}")
    public ResponseEntity<List<Ingredient>> deleteIngredientToMenu(@PathVariable("idMenu") int idMenu,
                                                       @PathVariable("idIngredient") int idIngredient){
        menuService.deleteMenuIngredient(idMenu, idIngredient);
        List<Ingredient> ingredients = menuService.getAllMenuIngredient(idMenu);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }
    @PostMapping("/sale/{idMenu}")
    public ResponseEntity<Menu> saleMenu(@RequestBody Restaurant restaurant,
                                            @PathVariable int idMenu){
        Menu menu = menuService.saleMenu(menuRepo.getMenuById(idMenu), restaurant);
        return new ResponseEntity<>(menu, HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<List<AllMenuSaleAtDate>> getAllMenuSaleAtDate(@RequestParam(required = false) Instant begin,
                                                                        @RequestParam(required = false) Instant end){
        List<AllMenuSaleAtDate> allMenuSaleAtDates = menuService.allMenuSale(begin, end);
        return new ResponseEntity<>(allMenuSaleAtDates, HttpStatus.OK);
    }
}

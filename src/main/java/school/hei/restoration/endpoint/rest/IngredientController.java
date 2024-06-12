package school.hei.restoration.endpoint.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.restoration.service.IngredientService;
import school.hei.restoration.model.dto.IngredientMostUsedByMenu;

import java.time.Instant;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;
    @GetMapping("/most-used")
    public ResponseEntity<List<IngredientMostUsedByMenu>>  ingredientMostUsedByMenu(@RequestParam int limit,
                                                                                    @RequestParam Instant begin,
                                                                                    @RequestParam Instant end){

        List<IngredientMostUsedByMenu> ingredientMostUsedByMenus = ingredientService
                .ingredientMostUsedByMenus(limit, begin, end);
        return new ResponseEntity<>(ingredientMostUsedByMenus, HttpStatus.OK);

    }
}

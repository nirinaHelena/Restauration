package school.hei.restoration.endpoint.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.restoration.Service.IngredientService;
import school.hei.restoration.repository.IngredientRepo;
import school.hei.restoration.repository.dto.IngredientMostUsedByMenu;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;
    @GetMapping("/most-used")
    public ResponseEntity<List<IngredientMostUsedByMenu>>  ingredientMostUsedByMenu(@RequestParam int limit,
                                                                                    @RequestParam String begin,
                                                                                    @RequestParam String end){

        Instant beginInstant = Instant.parse(begin);
        Instant endInstant = Instant.parse(end);

        List<IngredientMostUsedByMenu> ingredientMostUsedByMenus = ingredientService
                .ingredientMostUsedByMenus(limit, beginInstant, endInstant);
        return new ResponseEntity<>(ingredientMostUsedByMenus, HttpStatus.OK);

    }
}

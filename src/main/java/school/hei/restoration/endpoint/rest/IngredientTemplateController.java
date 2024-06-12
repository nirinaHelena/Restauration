package school.hei.restoration.endpoint.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.restoration.Service.IngredientTemplateService;
import school.hei.restoration.repository.model.IngredientTemplate;

@RestController
@AllArgsConstructor
@RequestMapping("/ingredient-template")
public class IngredientTemplateController {
    private final IngredientTemplateService ingredientTemplateService;

    @PutMapping
    public ResponseEntity<IngredientTemplate> save(@RequestBody IngredientTemplate ingredientTemplate) {
        IngredientTemplate toSave = ingredientTemplateService.save(ingredientTemplate);
        return new ResponseEntity<>(toSave, HttpStatus.CREATED);
    }
}

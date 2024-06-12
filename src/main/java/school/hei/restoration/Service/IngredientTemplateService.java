package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.IngredientTemplateRepo;
import school.hei.restoration.repository.model.IngredientTemplate;

@Service
@AllArgsConstructor
public class IngredientTemplateService {
    private final IngredientTemplateRepo ingredientTemplateRepo;

    public IngredientTemplate save(IngredientTemplate ingredientTemplate) {
        return ingredientTemplateRepo.save(ingredientTemplate);
    }
}

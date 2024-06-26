package school.hei.restoration.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restoration.repository.IngredientRepo;
import school.hei.restoration.repository.IngredientTemplateRepo;
import school.hei.restoration.repository.MovementRepo;
import school.hei.restoration.repository.dto.IngredientMostUsed;
import school.hei.restoration.repository.dto.IngredientMostUsedByMenu;
import school.hei.restoration.repository.model.IngredientTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IngredientService {
    private final IngredientRepo ingredientRepo;
    private final IngredientTemplateRepo ingredientTemplateRepo;
    private final MovementRepo movementRepo;

    public List<IngredientMostUsedByMenu> ingredientMostUsedByMenus(int limit, Instant begin, Instant end){
        List<IngredientMostUsed> ingredientMostUses = movementRepo.getIngredientMostUsed(limit, begin, end);
        List<IngredientMostUsedByMenu> ingredientMostUsedByMenus = new ArrayList<>();
        for (IngredientMostUsed ingredientMostUsed : ingredientMostUses) {
            IngredientTemplate ingredientTemplate = ingredientTemplateRepo.getById(ingredientMostUsed.id_ingredient_template());
            ingredientMostUsedByMenus.add(new IngredientMostUsedByMenu(
                    ingredientRepo.menuWhereIngredientIsMostUsed(ingredientTemplate),
                    ingredientTemplate,
                    ingredientMostUsed.quantity()));
        }
        return ingredientMostUsedByMenus;
    }
}

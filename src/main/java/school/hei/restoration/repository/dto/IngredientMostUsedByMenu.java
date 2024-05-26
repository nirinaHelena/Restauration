package school.hei.restoration.repository.dto;

import school.hei.restoration.repository.model.Menu;
import school.hei.restoration.repository.model.Unity;

public record IngredientMostUsedByMenu(
        int idIngredientTemplate,
        String nameIngredient,
        Menu menuWhichMostUsedIngredient,
        double quantitySpent,
        Unity unity
) {
}

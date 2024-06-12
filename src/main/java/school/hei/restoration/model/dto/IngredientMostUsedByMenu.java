package school.hei.restoration.model.dto;

import school.hei.restoration.model.Menu;
import school.hei.restoration.model.Unity;

public record IngredientMostUsedByMenu(
        int idIngredientTemplate,
        String nameIngredient,
        Menu menuWhichMostUsedIngredient,
        double quantitySpent,
        Unity unity
) {
}

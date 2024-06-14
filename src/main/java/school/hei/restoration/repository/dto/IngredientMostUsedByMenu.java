package school.hei.restoration.repository.dto;

import lombok.AllArgsConstructor;
import school.hei.restoration.repository.model.IngredientMenu;
import school.hei.restoration.repository.model.Menu;
import school.hei.restoration.repository.model.Unity;

public class IngredientMostUsedByMenu extends IngredientMenu {
    private double quantitySpent;
}

package school.hei.restoration.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import school.hei.restoration.repository.model.IngredientMenu;
import school.hei.restoration.repository.model.Menu;
import school.hei.restoration.repository.model.Unity;

@SuperBuilder
@AllArgsConstructor
@Data
public class IngredientMostUsedByMenu extends IngredientMenu {
    private double quantitySpent;
}

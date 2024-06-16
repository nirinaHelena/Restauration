package school.hei.restoration.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import school.hei.restoration.repository.model.IngredientMenu;
import school.hei.restoration.repository.model.IngredientTemplate;
import school.hei.restoration.repository.model.Menu;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class IngredientMostUsedByMenu extends IngredientMenu {
    private double quantitySpent;

    public IngredientMostUsedByMenu(Menu menu, IngredientTemplate ingredientTemplate, double quantitySpent) {
        super(menu, ingredientTemplate);
        this.quantitySpent = quantitySpent;
    }
}

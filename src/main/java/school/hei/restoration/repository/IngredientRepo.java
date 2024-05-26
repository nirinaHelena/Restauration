package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
import school.hei.restoration.repository.model.Ingredient;
import school.hei.restoration.repository.model.IngredientTemplate;
import school.hei.restoration.repository.model.Menu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class IngredientRepo {
    private final Database connection;
    private final IngredientTemplateRepo ingredientTemplateRepo;

    public void save(Ingredient ingredient){
        String sql = """
                insert into ingredient (quantity_required, id_ingredient_template, id_menu)
                values (?, ?, ?);
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setDouble(1, ingredient.getQuantityRequired());
            statement.setInt(2, ingredient.getIngredientTemplate().getId());
            statement.setInt(3, ingredient.getMenu().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Ingredient> getIngredientByMenu(Menu menu){
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = """
                select  * from ingredient
                join menu on ingredient.id_menu = menu.id
                where ingredient.id_menu = ?;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, menu.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                IngredientTemplate ingredientTemplate = ingredientTemplateRepo.getById(
                        resultSet.getInt("id_ingredient_template"));
                ingredients.add(new Ingredient(
                        resultSet.getInt("id"),
                        menu,
                        ingredientTemplate,
                        resultSet.getDouble("quantity_required")
                ));
            }
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

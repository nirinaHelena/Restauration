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
    private final MenuRepo menuRepo;

    public void save(Ingredient ingredient){
        Ingredient toSave = getByIdAndMenu(ingredient.getMenu().getId(), ingredient.getId());
        if (toSave == null){
            String sql = """
                insert into ingredient (quantity_required, id_ingredient_template, id_menu, id)
                values (?, ?, ?, ?);
                """;
            try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
                statement.setDouble(1, ingredient.getQuantityRequired());
                statement.setInt(2, ingredient.getIngredientTemplate().getId());
                statement.setInt(3, ingredient.getMenu().getId());
                statement.setInt(4, ingredient.getId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            String sql = """
                update ingredient set quantity_required = ? where ingredient.id = ?;
                """;
            try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
                statement.setDouble(1, ingredient.getQuantityRequired());
                statement.setInt(2, ingredient.getId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Ingredient getByIdAndMenu(int idMenu, int idIngredient){
        String sql = """
                select * from ingredient where id = ? and id_menu = ?;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, idIngredient);
            statement.setInt(2, idMenu);
            ResultSet resultSet = statement.executeQuery();
            return new Ingredient(
                    menuRepo.getMenuById(resultSet.getInt("id_menu")),
                    ingredientTemplateRepo.getById(resultSet.getInt("id_ingredient_template")),
                    resultSet.getInt("id"),
                    resultSet.getDouble("quantity_required"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Ingredient> getMenuIngredient(Menu menu){
        Menu menu1 = menuRepo.getMenuById(menu.getId());
        if (menu1 == null){
            throw new RuntimeException("the menu does not exist");
        }
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
                        menuRepo.getMenuById(resultSet.getInt("id_menu")),
                        ingredientTemplate,
                        resultSet.getInt("id"),
                        resultSet.getDouble("quantity_required"))
                );
            }
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMenuIngredient(Ingredient ingredient) {
        String sql = """
                delete from ingredient where id = ?;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, ingredient.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Menu menuWhereIngredientIsMostUsed(IngredientTemplate ingredientTemplate){
        String sql = """
             select * from ingredient
             where id_ingredient_template = ?
             order by quantity_required desc limit 1;
             """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, ingredientTemplate.getId());

            ResultSet resultSet = statement.executeQuery();
            return menuRepo.getMenuById(resultSet.getInt("id_menu"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

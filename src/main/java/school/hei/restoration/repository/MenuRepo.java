package school.hei.restoration.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
import school.hei.restoration.repository.model.Ingredient;
import school.hei.restoration.repository.model.IngredientTemplate;
import school.hei.restoration.repository.model.Menu;
import school.hei.restoration.repository.model.MenuPrices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class MenuRepo {
    private final IngredientTemplateRepo ingredientTemplateRepo;
    private Database connection;
    public void save(Menu menu){
        String sql = """
                insert into menu (name, id) values (?, ?);
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setString(1, menu.getName());
            statement.setInt(2, menu.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Menu> findAll(){
        List<Menu> menus = new ArrayList<>();
        String sql = """
                select  * from menu ;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int menuId = resultSet.getInt("id");
                Menu menu = getMenuById(menuId);
                menus.add(new Menu(
                        menuId,
                        resultSet.getString("name"),
                        getMenuPrices(menu),
                        getMenuIngredient(menu)
                ));
            }
            return menus;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Menu getMenuById(int id){
        String sql = """
                select  * from menu where id = ?
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            Menu menu =  new Menu(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );
            return new Menu(
                    menu.getId(),
                    menu.getName(),
                    getMenuPrices(menu),
                    getMenuIngredient(menu)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private MenuPrices getMenuPrices(Menu menu){
        if (getMenuById(menu.getId()) == null){
            throw new RuntimeException("the menu does not exists");
        }
        String sql = """
                select * from price_menu where id_menu = ?;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, menu.getId());

            ResultSet resultSet = statement.executeQuery();
            return new MenuPrices(
                    resultSet.getInt("id"),
                    resultSet.getTimestamp("date").toInstant(),
                    resultSet.getDouble("price"),
                    menu.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ingredient> getMenuIngredient(Menu menu){
        Menu menu1 = getMenuById(menu.getId());
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
                        getMenuById(resultSet.getInt("id_menu")),
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
}

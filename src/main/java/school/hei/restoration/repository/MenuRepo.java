package school.hei.restoration.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
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
    private final MenuPricesRepo menuPricesRepo;
    private final IngredientRepo ingredientRepo;
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
                        menuPricesRepo.getMenuPrices(menu),
                        ingredientRepo.getMenuIngredient(menu)
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
                    menuPricesRepo.getMenuPrices(menu),
                    ingredientRepo.getMenuIngredient(menu)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

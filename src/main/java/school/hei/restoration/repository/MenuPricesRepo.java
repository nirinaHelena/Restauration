package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
import school.hei.restoration.repository.model.Menu;
import school.hei.restoration.repository.model.MenuPrices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class MenuPricesRepo {
    private final Database connection;
    private final MenuRepo menuRepo;

    public Boolean save(MenuPrices menuPrices){
        String sql = """
                insert into price_menu( price, id_menu) values (?, ?);
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setDouble(1, menuPrices.getPrice());
            statement.setDouble(2, menuPrices.getIdMenu());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public MenuPrices getMenuPrices(Menu menu){
        if (menuRepo.getMenuById(menu.getId()) == null){
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
}

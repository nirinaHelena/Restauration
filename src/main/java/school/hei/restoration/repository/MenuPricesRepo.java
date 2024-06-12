package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.repository.conf.Database;
import school.hei.restoration.model.MenuPrices;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class MenuPricesRepo {
    private final Database connection;
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
}

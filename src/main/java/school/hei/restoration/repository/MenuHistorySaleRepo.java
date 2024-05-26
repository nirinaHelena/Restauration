package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
import school.hei.restoration.repository.model.Menu;
import school.hei.restoration.repository.model.MenuHistorySale;
import school.hei.restoration.repository.model.Restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

@Repository
@AllArgsConstructor
public class MenuHistorySaleRepo {
    private Database connection;

    public void save(MenuHistorySale menuHistorySale){
        String sql = """
                insert into menu_history_sale (date, id_menu) values (?, ?);
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setTimestamp(1, Timestamp.from(menuHistorySale.date()));
            statement.setInt(2, menuHistorySale.menu().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int countMenuSalePerMenu(Restaurant restaurant, Menu menu, Instant begin, Instant end){
        String sql = """
                select id_menu, count(*) as count from menu_history_sale
                where id_menu = ? and date >= ? and date <= ?
                group by id_menu;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, menu.getId());
            statement.setTimestamp(2, Timestamp.from(begin));
            statement.setTimestamp(3, Timestamp.from(end));
            statement.setInt(4, restaurant.getId());

            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt("count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

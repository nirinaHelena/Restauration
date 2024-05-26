package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
import school.hei.restoration.repository.model.MenuHistorySale;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
}

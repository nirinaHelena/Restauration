package school.hei.restoration.repository;


import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
import school.hei.restoration.repository.model.Menu;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class MenuRepo {
    private Database connection;
    public void save(Menu menu){
        String sql = """
                insert into menu (name) values (?);
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setString(1, menu.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

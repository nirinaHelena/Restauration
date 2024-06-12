package school.hei.restoration.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.repository.conf.Database;
import school.hei.restoration.model.Menu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class MenuRepo {
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
                menus.add(new Menu(
                        resultSet.getInt("id"),
                        resultSet.getString("name")
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
            return new Menu(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

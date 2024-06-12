package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.repository.conf.Database;
import school.hei.restoration.model.Restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class RestaurantRepo {
    private Database connection;
    public List<Restaurant> findAll(){
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = """
                select * from restaurant;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                restaurants.add(new Restaurant(
                        resultSet.getInt("id"),
                        resultSet.getString("localisation")
                ));
            }
            return restaurants;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

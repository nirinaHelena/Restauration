package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
import school.hei.restoration.repository.model.IngredientTemplate;
import school.hei.restoration.repository.model.Restaurant;
import school.hei.restoration.repository.model.Stock;

import java.sql.*;

@Repository
@AllArgsConstructor
public class StockRepo {
    private Database connection;
    public void save(Stock stock){
        String sql = """
                insert into stock (quantity, id_restaurant, id_ingredient_template, date)
                values (?, ?, ?, ?);
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setDouble(1, stock.quantity());
            statement.setInt(2, stock.restaurant().getId());
            statement.setInt(3, stock.ingredientTemplate().getId());
            statement.setTimestamp(4, Timestamp.from(stock.date()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Stock currentQuantity(Restaurant restaurant, IngredientTemplate ingredientTemplate){
        String sql = """
                select * from stock s
                join restaurant r on s.id_restaurant = r.id
                join ingreditent_template it on s.id_ingredient_template = it.id
                where s.id_ingredient_template = ? and s.id_restaurant = ?
                order by s.date desc limit 1;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, ingredientTemplate.getId());
            statement.setInt(2, restaurant.getId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return new Stock(
                        resultSet.getInt("id"),
                        restaurant,
                        ingredientTemplate,
                        resultSet.getTimestamp("date").toInstant(),
                        resultSet.getDouble("quantity")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
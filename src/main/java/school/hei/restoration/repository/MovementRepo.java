package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.repository.conf.Database;
import school.hei.restoration.model.dto.IngredientMostUsed;
import school.hei.restoration.model.Movement;
import school.hei.restoration.model.MovementType;
import school.hei.restoration.model.Restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class MovementRepo {
    private final IngredientTemplateRepo ingredientTemplateRepo;
    private Database connection;

    public void save(Movement movement){
        String sql = """
                insert into movement (date, id_ingredient_template, movement_type, quantity)
                values (?, ?, ?, ?);
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setTimestamp(1, Timestamp.from(movement.getDate()));
            statement.setInt(2, movement.getIngredientTemplate().getId());
            statement.setString(3, String.valueOf(movement.getMovementType()));
            statement.setDouble(4, movement.getQuantity());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Movement> findAll(Restaurant restaurant){
        List<Movement> movements = new ArrayList<>();
        String sql = """
                select * from movement where id_restaurant = ?;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, restaurant.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                movements.add(new Movement(
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("date").toInstant(),
                        ingredientTemplateRepo.getById(resultSet.getInt("id_ingredient_template")),
                        MovementType.valueOf(resultSet.getString("movement_type")),
                        resultSet.getDouble("quantity"),
                        restaurant
                ));
            }
            return movements;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movement> getAllMovementAtDate(Restaurant restaurant, Instant begin, Instant end) {
        List<Movement> movements = new ArrayList<>();
        String sql = """
                select * from movement where date >= ? and date <= ? and id_restaurant = ?;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setTimestamp(1, Timestamp.from(begin));
            statement.setTimestamp(2, Timestamp.from(end));
            statement.setInt(3, restaurant.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                movements.add(new Movement(
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("date").toInstant(),
                        ingredientTemplateRepo.getById(resultSet.getInt("id_ingredient_template")),
                        MovementType.valueOf(resultSet.getString("movement_type")),
                        resultSet.getDouble("quantity"),
                        restaurant
                ));
            }
            return movements;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<IngredientMostUsed> getIngredientMostUsed(int limit, Instant begin, Instant end){
        List<IngredientMostUsed> ingredientMostUseds = new ArrayList<>();
        String sql = """
                select mv.id_ingredient_template,  sum(mv.quantity) as quantity from movement mv
                where mv.date >= ? and mv.date <= ? and mv.movement_type = 'SALE'
                group by mv.id_ingredient_template
                order by quantity desc limit ?;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setTimestamp(1, Timestamp.from(begin));
            statement.setTimestamp(2, Timestamp.from(end));
            statement.setInt(3, limit);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ingredientMostUseds.add(new IngredientMostUsed(
                        resultSet.getInt("id_ingredient_template"),
                        resultSet.getDouble("quantity")
                ));
            }
            return ingredientMostUseds;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

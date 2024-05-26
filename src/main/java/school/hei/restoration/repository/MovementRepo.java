package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
import school.hei.restoration.repository.model.Movement;
import school.hei.restoration.repository.model.MovementType;

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
            statement.setTimestamp(1, Timestamp.from(movement.date()));
            statement.setInt(2, movement.ingredientTemplate().getId());
            statement.setString(3, String.valueOf(movement.movementType()));
            statement.setDouble(4, movement.quantity());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movement> getAllMovementAtDate(Instant begin, Instant end) {
        List<Movement> movements = new ArrayList<>();
        String sql = """
                select * from movement where date >= ? and date <= ?;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setTimestamp(1, Timestamp.from(begin));
            statement.setTimestamp(2, Timestamp.from(end));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                movements.add(new Movement(
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("date").toInstant(),
                        ingredientTemplateRepo.getById(resultSet.getInt("id_ingredient_template")),
                        MovementType.valueOf(resultSet.getString("movement_type")),
                        resultSet.getDouble("quantity")
                ));
            }
            return movements;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

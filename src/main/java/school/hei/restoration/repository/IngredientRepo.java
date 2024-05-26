package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
import school.hei.restoration.repository.model.Ingredient;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class IngredientRepo {
    private final Database connection;
    public void save(Ingredient ingredient){
        String sql = """
                insert into ingredient (quantity_required, id_ingredient_template, id_menu)
                values (?, ?, ?);
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setDouble(1, ingredient.getQuantityRequired());
            statement.setInt(2, ingredient.getIngredientTemplate().getId());
            statement.setInt(3, ingredient.getMenu().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

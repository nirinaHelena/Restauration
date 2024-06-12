package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.config.Database;
import school.hei.restoration.repository.model.IngredientTemplate;
import school.hei.restoration.repository.model.Unity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class IngredientTemplateRepo {
    private final UnityRepo unityRepo;
    private Database connection;

    public IngredientTemplate getById(int id){
        String sql = """
                select * from ingreditent_template where id = ?;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            Unity unity = null;
            if (resultSet.next()){
                unity = unityRepo.getUnityById(resultSet.getInt("id_unity"));
            }
            return new IngredientTemplate(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    unity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public IngredientTemplate save(IngredientTemplate ingredientTemplate){
        String sql = """
                insert into ingreditent_template (id, name, price, id_unity) values (?, ? , ?, ?);
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, ingredientTemplate.getId());
            statement.setString(2, ingredientTemplate.getName());
            statement.setDouble(3, ingredientTemplate.getPrice());
            statement.setInt(4, ingredientTemplate.getUnity().id());

            statement.executeUpdate();
            return getById(ingredientTemplate.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

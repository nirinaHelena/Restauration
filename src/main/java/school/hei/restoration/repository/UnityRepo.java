package school.hei.restoration.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.restoration.repository.conf.Database;
import school.hei.restoration.model.Unity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class UnityRepo {
    private Database connection;
    public Unity getUnityById(int id){
        String sql = """
                select * from unity where id = ?;
                """;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(sql)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return new Unity(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

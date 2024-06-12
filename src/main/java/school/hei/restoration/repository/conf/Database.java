package school.hei.restoration.repository.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class Database {
    private final String url;
    private final String user;
    private final String password;

    public Database() {
        this.url = System.getenv("url");
        this.user = System.getenv("username");
        this.password = System.getenv("password");
    }

    @Bean
    public Connection getConnection() {
        if (this.url != null && this.user != null && this.password != null) {
            try {
                Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
                if (connection != null && connection.isValid(2)) {
                    return connection;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("One or more environment variables are not set.");
        }
        return null;
    }
}

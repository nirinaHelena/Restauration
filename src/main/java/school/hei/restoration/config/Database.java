package school.hei.restoration.config;

import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String url;
    private String user;
    private String password;

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

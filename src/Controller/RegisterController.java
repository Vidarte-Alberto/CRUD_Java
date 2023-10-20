package Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Models.DatabaseConnection;

public class RegisterController {
    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO cuenta (usuario, contra) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Retorna verdadero si se insertaron filas, es decir, registro exitoso.
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna falso en caso de excepción.
        }
    }
}

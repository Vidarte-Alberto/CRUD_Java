package Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.DatabaseConnection;

public class LoginController {
    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM cuenta WHERE usuario = ? AND contra = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet result = preparedStatement.executeQuery();
            return result.next(); // Retorna verdadero si hay un resultado, es decir, autenticación exitosa.
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna falso en caso de excepción.
        }
    }
}

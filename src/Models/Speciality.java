package Models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Statement;

public class Speciality {
    private int specialityId;
    private String name;

    public Speciality(String name) {
        this.name = name;
    }

    public void saveToDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                if (specialityId > 0) {
                    // Ya existe un ID de especialidad, entonces actualiza en lugar de insertar
                    String sql = "UPDATE especialidad SET nombre = ? WHERE id_especialidad = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, name);
                    statement.setInt(2, specialityId);

                    statement.executeUpdate();
                    statement.close();
                } else {
                    // No existe un ID de especialidad, entonces inserta uno nuevo
                    String sql = "INSERT INTO especialidad (nombre) VALUES (?)";
                    PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setString(1, name);

                    statement.executeUpdate();

                    // Obtener el ID de especialidad generado automáticamente
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        specialityId = generatedKeys.getInt(1);
                    }

                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Speciality retrieveFromDatabase(int specialityId) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM especialidad WHERE id_especialidad = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, specialityId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("nombre");
                    Speciality speciality = new Speciality(name);
                    speciality.setSpecialityId(specialityId);
                    return speciality;
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void deleteFromDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "DELETE FROM especialidad WHERE id_especialidad = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, this.specialityId);
                statement.executeUpdate();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Speciality> getAllSpecialities() {
        ArrayList<Speciality> specialities = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM especialidad";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int specialityId = resultSet.getInt("id_especialidad");
                    String name = resultSet.getString("nombre");
                    Speciality speciality = new Speciality(name);
                    speciality.setSpecialityId(specialityId);
                    specialities.add(speciality);
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return specialities;
    }

    @Override
    public String toString() {
        return "Speciality ID: " + getSpecialityId() + ", Name: " + getName();
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

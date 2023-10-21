package Models;
import View.AlertDialog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Statement;

public class Medicine {
    private int medicineId;
    private String name;

    public Medicine(String name) {
        this.name = name;
    }

    public void saveToDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                if (medicineId > 0) {
                    // Ya existe un ID de medicamento, entonces actualiza en lugar de insertar
                    String sql = "UPDATE medicamento SET nombre = ? WHERE id_medicamento = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, name);
                    statement.setInt(2, medicineId);

                    statement.executeUpdate();
                    statement.close();
                } else {
                    // No existe un ID de medicamento, entonces inserta uno nuevo
                    String sql = "INSERT INTO medicamento (nombre) VALUES (?)";
                    PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setString(1, name);

                    statement.executeUpdate();

                    // Obtener el ID de medicamento generado automáticamente
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        medicineId = generatedKeys.getInt(1);
                    }

                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                if (e.getErrorCode() == 1452) {
                    AlertDialog.showError("Error de clave externa (1452) - Detalles:" + "\nID Medicamento: " + medicineId + "\nNombre de la especialidad: " + name + "\nEl medicamento que intentas agregar no se puede dado que alguno de los valores no existe");
                } else {
                    AlertDialog.showError(e.getMessage());
                }
            }
        }
    }

    public static Medicine retrieveFromDatabase(int medicineId) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM medicamento WHERE id_medicamento = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, medicineId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("nombre");
                    Medicine medicine = new Medicine(name);
                    medicine.setMedicineId(medicineId);
                    return medicine;
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
                String sql = "DELETE FROM medicamento WHERE id_medicamento = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, this.medicineId);
                statement.executeUpdate();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Medicine> getAllMedicines() {
        ArrayList<Medicine> medicines = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM medicamento";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int medicineId = resultSet.getInt("id_medicamento");
                    String name = resultSet.getString("nombre");
                    Medicine medicine = new Medicine(name);
                    medicine.setMedicineId(medicineId);
                    medicines.add(medicine);
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return medicines;
    }

    @Override
    public String toString() {
        return "Medicine ID: " + getMedicineId() + ", Name: " + getName();
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

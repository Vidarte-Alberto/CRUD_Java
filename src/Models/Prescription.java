package Models;

import View.AlertDialog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prescription {
    private int prescriptionId;
    private int patientId;
    private int medicineId;
    private String dose;
    private Date initialDate;
    private Date finishDate;

    public Prescription(int patientId, int medicineId, String dose, Date initialDate, Date finishDate) {
        this.patientId = patientId;
        this.medicineId = medicineId;
        this.dose = dose;
        this.initialDate = initialDate;
        this.finishDate = finishDate;
    }

    // Método para guardar en la base de datos
    public void saveToDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                if (prescriptionId > 0) {
                    // Ya existe un ID de receta, entonces actualiza en lugar de insertar
                    String sql = "UPDATE receta SET id_paciente = ?, id_medicamento = ?, dosis = ?, fechaini = ?, fechafin = ? WHERE id_receta = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, patientId);
                    statement.setInt(2, medicineId);
                    statement.setString(3, dose);
                    statement.setDate(4, new java.sql.Date(initialDate.getTime()));
                    statement.setDate(5, new java.sql.Date(finishDate.getTime()));
                    statement.setInt(6, prescriptionId); // Específica el ID de receta a actualizar

                    statement.executeUpdate();
                    statement.close();
                } else {
                    // No existe un ID de receta, entonces inserta uno nuevo
                    String sql = "INSERT INTO receta (id_paciente, id_medicamento, dosis, fechaini, fechafin) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, patientId);
                    statement.setInt(2, medicineId);
                    statement.setString(3, dose);
                    statement.setDate(4, new java.sql.Date(initialDate.getTime()));
                    statement.setDate(5, new java.sql.Date(finishDate.getTime()));

                    statement.executeUpdate();

                    // Obtener el ID de receta generado automáticamente
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        prescriptionId = generatedKeys.getInt(1);
                    }

                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                if (e.getErrorCode() == 1452) {
                    AlertDialog.showError("Error de clave externa (1452) - Detalles:" +
                            "\nID de paciente: " + patientId +
                            "\nID del medicamento: " + medicineId +
                            "\nFecha de inicio: " + initialDate +
                            "\nFecha de finalización: " + finishDate +
                            "\nLa receta que intentas agregar no se puede dado que alguno de los valores no existe");
                } else {
                    AlertDialog.showError(e.getMessage());
                }
            }
        }
    }

    // Método para recuperar una receta desde la base de datos por ID
    public static Prescription retrieveFromDatabase(int prescriptionId) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM receta WHERE id_receta = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, prescriptionId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int patientId = resultSet.getInt("id_paciente");
                    int medicineId = resultSet.getInt("id_medicamento");
                    String dose = resultSet.getString("dosis");
                    Date initialDate = resultSet.getDate("fechaini");
                    Date finishDate = resultSet.getDate("fechafin");
                    Prescription prescription = new Prescription(patientId, medicineId, dose, initialDate, finishDate);
                    prescription.setPrescriptionId(prescriptionId);
                    return prescription;
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

    // Método para eliminar una receta desde la base de datos
    public void deleteFromDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "DELETE FROM receta WHERE id_receta = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, this.prescriptionId);
                statement.executeUpdate();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para obtener todas las recetas en la base de datos
    public static ArrayList<Prescription> getAllPrescriptions() {
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM receta";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int prescriptionId = resultSet.getInt("id_receta");
                    int patientId = resultSet.getInt("id_paciente");
                    int medicineId = resultSet.getInt("id_medicamento");
                    String dose = resultSet.getString("dosis");
                    Date initialDate = resultSet.getDate("fechaini");
                    Date finishDate = resultSet.getDate("fechafin");
                    Prescription prescription = new Prescription(patientId, medicineId, dose, initialDate, finishDate);
                    prescription.setPrescriptionId(prescriptionId);
                    prescriptions.add(prescription);
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return prescriptions;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String initialDateString = (initialDate != null) ? dateFormat.format(initialDate) : "N/A";
        String finishDateString = (finishDate != null) ? dateFormat.format(finishDate) : "N/A";

        return "Prescription ID: " + prescriptionId +
                "\nPatient ID: " + patientId +
                "\nMedicine ID: " + medicineId +
                "\nDose: " + dose +
                "\nInitial Date: " + initialDateString +
                "\nFinish Date: " + finishDateString;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
}

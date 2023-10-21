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

public class Patient {
    private int patientId;
    private Date dateOfBirth;
    private long cel;
    private String address;
    private String genre;
    private String firstName;
    private String lastNameF;
    private String lastNameM;

    public Patient(Date dateOfBirth, long cel, String address, String genre, String firstName, String lastNameF, String lastNameM) {
        this.dateOfBirth = dateOfBirth;
        this.cel = cel;
        this.address = address;
        this.genre = genre;
        this.firstName = firstName;
        this.lastNameF = lastNameF;
        this.lastNameM = lastNameM;
    }

    public void saveToDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                if (patientId > 0) {
                    // Ya existe un ID de paciente, entonces actualiza en lugar de insertar
                    String sql = "UPDATE paciente SET fechana = ?, celular = ?, domicilio = ?, sexo = ?, nombre = ?, apep = ?, apem = ? WHERE id_paciente = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setDate(1, new java.sql.Date(dateOfBirth.getTime()));
                    statement.setLong(2, cel);
                    statement.setString(3, address);
                    statement.setString(4, genre);
                    statement.setString(5, firstName);
                    statement.setString(6, lastNameF);
                    statement.setString(7, lastNameM);
                    statement.setInt(8, patientId); // Específicamos el ID de paciente a actualizar

                    statement.executeUpdate();
                    statement.close();
                } else {
                    // No existe un ID de paciente, entonces inserta uno nuevo
                    String sql = "INSERT INTO paciente (fechana, celular, domicilio, sexo, nombre, apep, apem) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setDate(1, new java.sql.Date(dateOfBirth.getTime()));
                    statement.setLong(2, cel);
                    statement.setString(3, address);
                    statement.setString(4, genre);
                    statement.setString(5, firstName);
                    statement.setString(6, lastNameF);
                    statement.setString(7, lastNameM);

                    statement.executeUpdate();

                    // Obtener el ID de paciente generado automáticamente
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        patientId = generatedKeys.getInt(1);
                    }

                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                AlertDialog.showError(e.getMessage());
            }
        }
    }

    public static Patient retrieveFromDatabase(int patientId) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM paciente WHERE id_paciente = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, patientId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Date dateOfBirth = resultSet.getDate("fechana");
                    long cel = resultSet.getLong("celular");
                    String address = resultSet.getString("domicilio");
                    String genre = resultSet.getString("sexo");
                    String firstName = resultSet.getString("nombre");
                    String lastNameF = resultSet.getString("apep");
                    String lastNameM = resultSet.getString("apem");
                    Patient patient = new Patient(dateOfBirth, cel, address, genre, firstName, lastNameF, lastNameM);
                    patient.setPatientId(patientId);
                    return patient;
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
                String sql = "DELETE FROM paciente WHERE id_paciente = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, this.patientId);
                statement.executeUpdate();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM paciente";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int patientId = resultSet.getInt("id_paciente");
                    Date dateOfBirth = resultSet.getDate("fechana");
                    long cel = resultSet.getLong("celular");
                    String address = resultSet.getString("domicilio");
                    String genre = resultSet.getString("sexo");
                    String firstName = resultSet.getString("nombre");
                    String lastNameF = resultSet.getString("apep");
                    String lastNameM = resultSet.getString("apem");
                    Patient patient = new Patient(dateOfBirth, cel, address, genre, firstName, lastNameF, lastNameM);
                    patient.setPatientId(patientId);
                    patients.add(patient);
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return patients;
    }

    @Override
    public String toString() {
        return "Patient ID: " + getPatientId() +
                ", Name: " + getFirstName() + " " + getLastNameF() + " " + getLastNameM() +
                ", Date of Birth: " + formatDate(getDateOfBirth()) +
                ", Cel: " + getCel() +
                ", Address: " + getAddress() +
                ", Genre: " + getGenre();
    }

    private String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } else {
            return "N/A";
        }
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getCel() {
        return cel;
    }

    public void setCel(long cel) {
        this.cel = cel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNameF() {
        return lastNameF;
    }

    public void setLastNameF(String lastNameF) {
        this.lastNameF = lastNameF;
    }

    public String getLastNameM() {
        return lastNameM;
    }

    public void setLastNameM(String lastNameM) {
        this.lastNameM = lastNameM;
    }
}

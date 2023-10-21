package Models;
import View.AlertDialog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Doctor {
    private int doctorId;
    private int specialityId;
    private String firstName;
    private String lastNameM;
    private String lastNameF;
    private String genre;
    private String address;
    private Date dateOfBirth;
    private long cel;

    // Constructor
    public Doctor(int specialityId, String firstName, String lastNameM, String lastNameF, String genre, String address, Date dateOfBirth, long cel) {
        this.specialityId = specialityId;
        this.firstName = firstName;
        this.lastNameM = lastNameM;
        this.lastNameF = lastNameF;
        this.genre = genre;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.cel = cel;
    }

    // Método para guardar en la base de datos
    public void saveToDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                if (doctorId > 0) {
                    // Ya existe un ID de doctor, entonces actualiza en lugar de insertar
                    String sql = "UPDATE doctor SET id_especialidad = ?, nombre = ?, apep = ?, apem = ?, sexo = ?, domicilio = ?, fechana = ?, celular = ? WHERE id_doctor = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, specialityId);
                    statement.setString(2, firstName);
                    statement.setString(3, lastNameF);
                    statement.setString(4, lastNameM);
                    statement.setString(5, genre);
                    statement.setString(6, address);
                    statement.setDate(7, new java.sql.Date(dateOfBirth.getTime()));
                    statement.setLong(8, cel);
                    statement.setInt(9, doctorId); // Específica el ID de doctor a actualizar

                    statement.executeUpdate();
                    statement.close();
                } else {
                    // No existe un ID de doctor, entonces inserta uno nuevo
                    String sql = "INSERT INTO doctor (id_especialidad, nombre, apep, apem, sexo, domicilio, fechana, celular) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, specialityId);
                    statement.setString(2, firstName);
                    statement.setString(3, lastNameF);
                    statement.setString(4, lastNameM);
                    statement.setString(5, genre);
                    statement.setString(6, address);
                    statement.setDate(7, new java.sql.Date(dateOfBirth.getTime()));
                    statement.setLong(8, cel);

                    statement.executeUpdate();

                    // Obtener el ID de doctor generado automáticamente
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        doctorId = generatedKeys.getInt(1);
                    }

                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                if (e.getErrorCode() == 1452) {
                    AlertDialog.showError("Error de clave externa (1452) - Detalles:" + "\nEspecialidad: " + specialityId + "\nLa cirugía que intentas agregar no se puede dado que alguno de los valores no existe");
                } else {
                    AlertDialog.showError(e.getMessage());
                }
            }
        }
    }

    // Método para recuperar un doctor desde la base de datos por ID
    public static Doctor retrieveFromDatabase(int doctorId) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM doctor WHERE id_doctor = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, doctorId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int specialityId = resultSet.getInt("id_especialidad");
                    String firstName = resultSet.getString("nombre");
                    String lastNameM = resultSet.getString("apem");
                    String lastNameF = resultSet.getString("apep");
                    String genre = resultSet.getString("sexo");
                    String address = resultSet.getString("domicilio");
                    Date dateOfBirth = resultSet.getDate("fechana");
                    long cel = resultSet.getLong("celular");
                    Doctor doctor = new Doctor(specialityId, firstName, lastNameM, lastNameF, genre, address, dateOfBirth, cel);
                    doctor.setDoctorId(doctorId);
                    return doctor;
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

    // Método para eliminar un doctor desde la base de datos
    public void deleteFromDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "DELETE FROM doctor WHERE id_doctor = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, this.doctorId);
                statement.executeUpdate();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para obtener todos los doctores en la base de datos
    public static ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM doctor";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int doctorId = resultSet.getInt("id_doctor");
                    int specialityId = resultSet.getInt("id_especialidad");
                    String firstName = resultSet.getString("nombre");
                    String lastNameM = resultSet.getString("apem");
                    String lastNameF = resultSet.getString("apep");
                    String genre = resultSet.getString("sexo");
                    String address = resultSet.getString("domicilio");
                    Date dateOfBirth = resultSet.getDate("fechana");
                    long cel = resultSet.getLong("celular");
                    Doctor doctor = new Doctor(specialityId, firstName, lastNameM, lastNameF, genre, address, dateOfBirth, cel);
                    doctor.setDoctorId(doctorId);
                    doctors.add(doctor);
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return doctors;
    }

    // Otros métodos y getters y setters
    @Override
    public String toString() {
        return "Doctor ID: " + getDoctorId() +
                ", Name: " + getFirstName() + " " + getLastNameF() + " " + getLastNameM() +
                ", Date of Birth: " + formatDate(getDateOfBirth()) +
                ", Cel: " + getCel() +
                ", Address: " + getAddress() +
                ", Genre: " + getGenre();
    }

    // Método formatDate para formatear la fecha (debes implementarlo según tus necesidades)
    private String formatDate(Date date) {
        // Implementa la lógica para formatear la fecha aquí
        return date.toString(); // Esto es solo un ejemplo
    }
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNameM() {
        return lastNameM;
    }

    public void setLastNameM(String lastNameM) {
        this.lastNameM = lastNameM;
    }

    public String getLastNameF() {
        return lastNameF;
    }

    public void setLastNameF(String lastNameF) {
        this.lastNameF = lastNameF;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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



}

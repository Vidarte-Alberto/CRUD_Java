package Models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Surgery {
    private int surgeryId;  // No necesitas generar el ID aquí
    private int patientId;
    private int roomNumber;
    private int doctorId;
    private Date startDate;

    public Surgery(int patientId, int roomNumber, int doctorId, Date startDate) {
        this.patientId = patientId;
        this.roomNumber = roomNumber;
        this.doctorId = doctorId;
        this.startDate = startDate;
    }

    public void saveToDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                if (surgeryId > 0) {
                    // Ya existe un ID de cirugía, entonces actualiza en lugar de insertar
                    String sql = "UPDATE cirugia SET id_paciente = ?, id_sala = ?, id_doctor = ?, fechaini = ? WHERE id_cirugia = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, patientId);
                    statement.setInt(2, roomNumber);
                    statement.setInt(3, doctorId);
                    statement.setDate(4, new java.sql.Date(startDate.getTime()));
                    statement.setInt(5, surgeryId); // Específicamos el ID de cirugía a actualizar

                    statement.executeUpdate();
                    statement.close();
                } else {
                    // No existe un ID de cirugía, entonces inserta uno nuevo
                    String sql = "INSERT INTO cirugia (id_paciente, id_sala, id_doctor, fechaini) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, patientId);
                    statement.setInt(2, roomNumber);
                    statement.setInt(3, doctorId);
                    statement.setDate(4, new java.sql.Date(startDate.getTime()));

                    statement.executeUpdate();

                    // Obtener el ID de cirugía generado automáticamente
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        surgeryId = generatedKeys.getInt(1);
                    }

                    statement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static Surgery retrieveFromDatabase(int surgeryId) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM cirugia WHERE id_cirugia = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, surgeryId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int patientId = resultSet.getInt("id_paciente");
                    int roomNumber = resultSet.getInt("id_sala");
                    int doctorId = resultSet.getInt("id_doctor");
                    Date startDate = resultSet.getDate("fechaini");
                    Surgery surgery = new Surgery(patientId, roomNumber, doctorId, startDate);
                    surgery.setSurgeryId(surgeryId);
                    return surgery;
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
                String sql = "DELETE FROM cirugia WHERE id_cirugia = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, this.surgeryId);
                statement.executeUpdate();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Surgery> getAllSurgeries() {
        ArrayList<Surgery> surgeries = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM cirugia";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    int surgeryId = resultSet.getInt("id_cirugia");
                    int patientId = resultSet.getInt("id_paciente");
                    int roomNumber = resultSet.getInt("id_sala");
                    int doctorId = resultSet.getInt("id_doctor");
                    Date startDate = resultSet.getDate("fechaini");
                    Surgery surgery = new Surgery(patientId, roomNumber, doctorId, startDate);
                    surgery.setSurgeryId(surgeryId);
                    surgeries.add(surgery);
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return surgeries;
    }


    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = (startDate != null) ? dateFormat.format(startDate) : "";
        return "Surgery #" + surgeryId + ", Patient #" + patientId + ", Room #" + roomNumber + ", Doctor #" + doctorId + ", Start Date: " + startDateStr;
    }

    public int getSurgeryId() {
        return surgeryId;
    }

    public void setSurgeryId(int surgeryId) {
        this.surgeryId = surgeryId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}

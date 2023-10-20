package Controller;
import Models.Doctor;

import java.util.ArrayList;
import java.util.Date;

public class DoctorController {
    // Método para crear un nuevo doctor en la base de datos
    public static void createDoctor(int specialityId, String firstName, String lastNameM, String lastNameF, String genre, String address, Date dateOfBirth, long cel) {
        Doctor newDoctor = new Doctor(specialityId, firstName, lastNameM, lastNameF, genre, address, dateOfBirth, cel);
        newDoctor.saveToDatabase();
    }

    // Método para recuperar un doctor por ID
    public static Doctor getDoctorById(int doctorId) {
        return Doctor.retrieveFromDatabase(doctorId);
    }

    // Método para actualizar un doctor en la base de datos
    public static void updateDoctor(int doctorId, int specialityId, String firstName, String lastNameM, String lastNameF, String genre, String address, Date dateOfBirth, long cel) {
        Doctor existingDoctor = Doctor.retrieveFromDatabase(doctorId);
        if (existingDoctor != null) {
            existingDoctor.setSpecialityId(specialityId);
            existingDoctor.setFirstName(firstName);
            existingDoctor.setLastNameM(lastNameM);
            existingDoctor.setLastNameF(lastNameF);
            existingDoctor.setGenre(genre);
            existingDoctor.setAddress(address);
            existingDoctor.setDateOfBirth(dateOfBirth);
            existingDoctor.setCel(cel);
            existingDoctor.saveToDatabase();
        }
    }

    // Método para eliminar un doctor de la base de datos
    public static void deleteDoctor(int doctorId) {
        Doctor doctorToDelete = Doctor.retrieveFromDatabase(doctorId);
        if (doctorToDelete != null) {
            doctorToDelete.deleteFromDatabase();
        }
    }

    // Método para obtener todos los doctores en la base de datos
    public static ArrayList<Doctor> getAllDoctors() {
        return Doctor.getAllDoctors();
    }
}

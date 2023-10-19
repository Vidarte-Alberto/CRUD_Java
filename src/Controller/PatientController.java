package Controller;

import Models.Patient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PatientController {
    public PatientController() {
    }

    public void addPatient(Date dateOfBirth, long cel, String address, String genre, String firstName, String lastNameF, String lastNameM) {
        Patient patient = new Patient(dateOfBirth, cel, address, genre, firstName, lastNameF, lastNameM);
        patient.saveToDatabase();
    }

    public void updatePatient(int patientId, Date dateOfBirth, long cel, String address, String genre, String firstName, String lastNameF, String lastNameM) {
        Patient patient = Patient.retrieveFromDatabase(patientId);
        if (patient != null) {
            patient.setDateOfBirth(dateOfBirth);
            patient.setCel(cel);
            patient.setAddress(address);
            patient.setGenre(genre);
            patient.setFirstName(firstName);
            patient.setLastNameF(lastNameF);
            patient.setLastNameM(lastNameM);
            patient.saveToDatabase();
        }
    }

    public void deletePatient(int patientId) {
        Patient patient = Patient.retrieveFromDatabase(patientId);
        if (patient != null) {
            patient.deleteFromDatabase();
        }
    }

    public ArrayList<Patient> getPatients() {
        return Patient.getAllPatients();
    }

    public static String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } else {
            return "N/A";
        }
    }
}

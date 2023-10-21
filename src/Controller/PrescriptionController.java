package Controller;

import Models.Prescription;

import java.util.ArrayList;
import java.util.Date;

public class PrescriptionController {
    public void createPrescription(int patientId, int medicineId, String dose, Date initialDate, Date finishDate) {
        // Crea una nueva receta y la guarda en la base de datos
        Prescription prescription = new Prescription(patientId, medicineId, dose, initialDate, finishDate);
        prescription.saveToDatabase();
    }

    public Prescription getPrescription(int prescriptionId) {
        // Recupera una receta de la base de datos por su ID
        return Prescription.retrieveFromDatabase(prescriptionId);
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        // Obtiene todas las recetas de la base de datos
        return Prescription.getAllPrescriptions();
    }

    public void updatePrescription(int prescriptionId, int patientId, int medicineId, String dose, Date initialDate, Date finishDate) {
        // Actualiza una receta en la base de datos
        Prescription prescription = getPrescription(prescriptionId);
        if (prescription != null) {
            prescription.setPatientId(patientId);
            prescription.setMedicineId(medicineId);
            prescription.setDose(dose);
            prescription.setInitialDate(initialDate);
            prescription.setFinishDate(finishDate);
            prescription.saveToDatabase();
        }
    }

    public void deletePrescription(int prescriptionId) {
        // Elimina una receta de la base de datos
        Prescription prescription = getPrescription(prescriptionId);
        if (prescription != null) {
            prescription.deleteFromDatabase();
        }
    }
}

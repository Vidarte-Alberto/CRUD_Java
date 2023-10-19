package Controller;

import Models.Surgery;
import java.util.ArrayList;
import java.util.Date;

public class SurgeryController {
    // El controlador ya no mantiene una lista en memoria
    public SurgeryController() {
    }

    public void addSurgery(int patientId, int roomNumber, int doctorId, Date startDate) {
        Surgery surgery = new Surgery(patientId, roomNumber, doctorId, startDate);
        surgery.saveToDatabase(); // Guarda la cirugía en la base de datos
    }


    public void deleteSurgery(int surgeryId) {
        Surgery surgery = Surgery.retrieveFromDatabase(surgeryId);
        if (surgery != null) {
            // Borra la cirugía de la base de datos
            surgery.deleteFromDatabase();
        }
    }

    public ArrayList<Surgery> getSurgeries() {
        return Surgery.getAllSurgeries();
    }
}

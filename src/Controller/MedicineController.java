package Controller;
import Models.Medicine;
import Models.Speciality;

import java.util.ArrayList;

public class MedicineController {

    public void createMedicine(String name) {
        // Crea una nueva especialidad y la guarda en la base de datos
        Medicine medicine = new Medicine(name);
        medicine.saveToDatabase();
    }

    public Medicine getMedicine(int medicineId) {
        // Recupera una especialidad de la base de datos por su ID
        return Medicine.retrieveFromDatabase(medicineId);
    }

    public ArrayList<Medicine> getAllMedicines() {
        // Obtiene todas las especialidades de la base de datos
        return Medicine.getAllMedicines();
    }

    public void updateMedicine(int medicineId, String name) {
        // Actualiza una especialidad en la base de datos
        Medicine medicine = getMedicine(medicineId);
        if (medicine != null) {
            medicine.setName(name);
            medicine.saveToDatabase();
        }
    }

    public void deleteMedicine(int medicineId) {
        // Elimina una especialidad de la base de datos
        Medicine medicine = getMedicine(medicineId);
        if (medicine != null) {
            medicine.deleteFromDatabase();
        }
    }
}

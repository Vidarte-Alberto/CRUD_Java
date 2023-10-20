package Controller;
import Models.Speciality;

import java.util.ArrayList;

public class SpecialityController {
    public SpecialityController() {
        // Puedes realizar cualquier inicialización necesaria aquí
    }

    public void createSpeciality(String name) {
        // Crea una nueva especialidad y la guarda en la base de datos
        Speciality speciality = new Speciality(name);
        speciality.saveToDatabase();
    }

    public Speciality getSpeciality(int specialityId) {
        // Recupera una especialidad de la base de datos por su ID
        return Speciality.retrieveFromDatabase(specialityId);
    }

    public ArrayList<Speciality> getAllSpecialities() {
        // Obtiene todas las especialidades de la base de datos
        return Speciality.getAllSpecialities();
    }

    public void updateSpeciality(int specialityId, String name) {
        // Actualiza una especialidad en la base de datos
        Speciality speciality = getSpeciality(specialityId);
        if (speciality != null) {
            speciality.setName(name);
            speciality.saveToDatabase();
        }
    }

    public void deleteSpeciality(int specialityId) {
        // Elimina una especialidad de la base de datos
        Speciality speciality = getSpeciality(specialityId);
        if (speciality != null) {
            speciality.deleteFromDatabase();
        }
    }
}

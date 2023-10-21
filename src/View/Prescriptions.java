package View;

import Controller.PrescriptionController;
import Models.Prescription;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prescriptions extends JFrame {
    private DefaultListModel<Prescription> listModel;
    private JList<Prescription> listView;
    private JTextField initialDateField;
    private JTextField finishDateField;
    private JTextField doseField;
    private JTextField patientIdField;
    private JTextField medicineIdField;
    private PrescriptionController controller;

    public Prescriptions() {
        super("Prescription Management Interface");
        listModel = new DefaultListModel<>();
        listView = new JList<>(listModel);

        initialDateField = new JTextField(10);
        finishDateField = new JTextField(10);
        doseField = new JTextField(10);
        patientIdField = new JTextField(10);
        medicineIdField = new JTextField(10);

        controller = new PrescriptionController();

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        listView.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Prescription selectedPrescription = listView.getSelectedValue();
                if (selectedPrescription != null) {
                    // Rellenar los campos con los detalles de la receta seleccionada
                    initialDateField.setText(formatDate(selectedPrescription.getInitialDate()));
                    finishDateField.setText(formatDate(selectedPrescription.getFinishDate()));
                    doseField.setText(selectedPrescription.getDose());
                    patientIdField.setText(String.valueOf(selectedPrescription.getPatientId()));
                    medicineIdField.setText(String.valueOf(selectedPrescription.getMedicineId()));
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date initialDate = parseDate(initialDateField.getText());
                Date finishDate = parseDate(finishDateField.getText());
                String dose = doseField.getText();
                int patientId = Integer.parseInt(patientIdField.getText());
                int medicineId = Integer.parseInt(medicineIdField.getText());

                controller.createPrescription(patientId, medicineId, dose, initialDate, finishDate);
                updateList();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Prescription selectedPrescription = listView.getSelectedValue();
                if (selectedPrescription != null) {
                    int prescriptionId = selectedPrescription.getPrescriptionId();
                    Date initialDate = parseDate(initialDateField.getText());
                    Date finishDate = parseDate(finishDateField.getText());
                    String dose = doseField.getText();
                    int patientId = Integer.parseInt(patientIdField.getText());
                    int medicineId = Integer.parseInt(medicineIdField.getText());

                    controller.updatePrescription(prescriptionId, patientId, medicineId, dose, initialDate, finishDate);
                    updateList();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Prescription selectedPrescription = listView.getSelectedValue();
                if (selectedPrescription != null) {
                    int prescriptionId = selectedPrescription.getPrescriptionId();
                    controller.deletePrescription(prescriptionId);
                    updateList();
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Initial Date (yyyy-MM-dd):"));
        inputPanel.add(initialDateField);
        inputPanel.add(new JLabel("Finish Date (yyyy-MM-dd):"));
        inputPanel.add(finishDateField);
        inputPanel.add(new JLabel("Dose:"));
        inputPanel.add(doseField);
        inputPanel.add(new JLabel("Patient ID:"));
        inputPanel.add(patientIdField);
        inputPanel.add(new JLabel("Medicine ID:"));
        inputPanel.add(medicineIdField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(listView), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(deleteButton, BorderLayout.NORTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        updateList();
    }

    private void updateList() {
        listModel.clear();
        listModel.addAll(controller.getAllPrescriptions());
        clearInputFields();
    }

    private void clearInputFields() {
        initialDateField.setText("");
        finishDateField.setText("");
        doseField.setText("");
        patientIdField.setText("");
        medicineIdField.setText("");
        listView.clearSelection();
    }

    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}

package View;

import Controller.MedicineController;
import Models.Medicine;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Medicines extends JFrame {
    private DefaultListModel<Medicine> listModel;
    private JList<Medicine> listView;
    private JTextField nameField;
    private MedicineController controller;

    public Medicines() {
        super("Medicine Management Interface");
        listModel = new DefaultListModel<>();
        listView = new JList<>(listModel);

        nameField = new JTextField(20);
        controller = new MedicineController();

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        listView.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Medicine selectedMedicine = listView.getSelectedValue();
                if (selectedMedicine != null) {
                    // Rellenar el campo con el nombre del medicamento seleccionado
                    nameField.setText(selectedMedicine.getName());
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();

                controller.createMedicine(name);
                updateList();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Medicine selectedMedicine = listView.getSelectedValue();
                if (selectedMedicine != null) {
                    int medicineId = selectedMedicine.getMedicineId();
                    String name = nameField.getText();

                    controller.updateMedicine(medicineId, name);
                    updateList();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Medicine selectedMedicine = listView.getSelectedValue();
                if (selectedMedicine != null) {
                    int medicineId = selectedMedicine.getMedicineId();
                    controller.deleteMedicine(medicineId);
                    updateList();
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Medicine Name:"));
        inputPanel.add(nameField);
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
        ArrayList<Medicine> medicines = controller.getAllMedicines();
        listModel.addAll(medicines);
        clearInputFields();
    }

    private void clearInputFields() {
        nameField.setText("");
        listView.clearSelection();
    }
}

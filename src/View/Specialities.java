package View;

import Controller.SpecialityController;
import Models.Speciality;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Specialities extends JFrame {
    private DefaultListModel<Speciality> listModel;
    private JList<Speciality> listView;
    private JTextField nameField;
    private SpecialityController controller;

    public Specialities() {
        super("Speciality Management Interface");
        listModel = new DefaultListModel<>();
        listView = new JList<>(listModel);

        nameField = new JTextField(20);
        controller = new SpecialityController();

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        listView.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Speciality selectedSpeciality = listView.getSelectedValue();
                if (selectedSpeciality != null) {
                    // Rellenar el campo con el nombre de la especialidad seleccionada
                    nameField.setText(selectedSpeciality.getName());
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();

                controller.createSpeciality(name);
                updateList();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Speciality selectedSpeciality = listView.getSelectedValue();
                if (selectedSpeciality != null) {
                    int specialityId = selectedSpeciality.getSpecialityId();
                    String name = nameField.getText();

                    controller.updateSpeciality(specialityId, name);
                    updateList();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Speciality selectedSpeciality = listView.getSelectedValue();
                if (selectedSpeciality != null) {
                    int specialityId = selectedSpeciality.getSpecialityId();
                    controller.deleteSpeciality(specialityId);
                    updateList();
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Speciality Name:"));
        inputPanel.add(nameField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(listView), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(deleteButton, BorderLayout.NORTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        updateList();
    }

    private void updateList() {
        listModel.clear();
        listModel.addAll(controller.getAllSpecialities());
        clearInputFields();
    }

    private void clearInputFields() {
        nameField.setText("");
        listView.clearSelection();
    }
}

package View;

import Controller.SurgeryController;
import Models.Surgery;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Surgeon extends JFrame {
    private DefaultListModel<Surgery> listModel;
    private JList<Surgery> listView;
    private JTextField patientIdField;
    private JTextField roomNumberField;
    private JTextField doctorIdField;
    private JTextField startDateField;
    private SurgeryController controller;

    public Surgeon() {
        super("CRUD Interface");
        listModel = new DefaultListModel<>();
        listView = new JList<>(listModel);

        patientIdField = new JTextField(5);
        roomNumberField = new JTextField(5);
        doctorIdField = new JTextField(5);
        startDateField = new JTextField(10);

        controller = new SurgeryController();

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int patientId = Integer.parseInt(patientIdField.getText());
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                int doctorId = Integer.parseInt(doctorIdField.getText());
                Date startDate = parseDate(startDateField.getText());

                controller.addSurgery(patientId, roomNumber, doctorId, startDate);
                updateList();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Surgery selectedSurgery = listView.getSelectedValue();
                if (selectedSurgery != null) {
                    // Se ha seleccionado una cirugía, así que actualiza la existente
                    int patientId = Integer.parseInt(patientIdField.getText());
                    int roomNumber = Integer.parseInt(roomNumberField.getText());
                    int doctorId = Integer.parseInt(doctorIdField.getText());
                    Date startDate = parseDate(startDateField.getText());

                    selectedSurgery.setPatientId(patientId);
                    selectedSurgery.setRoomNumber(roomNumber);
                    selectedSurgery.setDoctorId(doctorId);
                    selectedSurgery.setStartDate(startDate);
                    selectedSurgery.saveToDatabase();  // Actualiza la cirugía en la base de datos
                } else {
                    // No se ha seleccionado una cirugía, así que agrega una nueva
                    int patientId = Integer.parseInt(patientIdField.getText());
                    int roomNumber = Integer.parseInt(roomNumberField.getText());
                    int doctorId = Integer.parseInt(doctorIdField.getText());
                    Date startDate = parseDate(startDateField.getText());

                    Surgery surgery = new Surgery(patientId, roomNumber, doctorId, startDate);
                    surgery.saveToDatabase();  // Agrega una nueva cirugía en la base de datos
                }
                updateList();
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Surgery selectedSurgery = listView.getSelectedValue();
                if (selectedSurgery != null) {
                    controller.deleteSurgery(selectedSurgery.getSurgeryId());
                    updateList();
                }
            }
        });

        listView.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Surgery selectedSurgery = listView.getSelectedValue();
                if (selectedSurgery != null) {
                    // Rellenar los campos con los detalles de la cirugía seleccionada
                    patientIdField.setText(Integer.toString(selectedSurgery.getPatientId()));
                    roomNumberField.setText(Integer.toString(selectedSurgery.getRoomNumber()));
                    doctorIdField.setText(Integer.toString(selectedSurgery.getDoctorId()));
                    startDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(selectedSurgery.getStartDate()));
                }
            }
        });


        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Patient ID:"));
        inputPanel.add(patientIdField);
        inputPanel.add(new JLabel("Room Number:"));
        inputPanel.add(roomNumberField);
        inputPanel.add(new JLabel("Doctor ID:"));
        inputPanel.add(doctorIdField);
        inputPanel.add(new JLabel("Start Date (yyyy-MM-dd):"));
        inputPanel.add(startDateField);
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
        listModel.addAll(controller.getSurgeries());
        clearInputFields();
    }

    private void clearInputFields() {
        patientIdField.setText("");
        roomNumberField.setText("");
        doctorIdField.setText("");
        startDateField.setText("");
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
}

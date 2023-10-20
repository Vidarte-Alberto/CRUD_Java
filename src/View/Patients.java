package View;

import Controller.PatientController;
import Models.Patient;
import Models.Surgery;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Patients extends JFrame {
    private DefaultListModel<Patient> listModel;
    private JList<Patient> listView;
    private JTextField dateOfBirthField;
    private JTextField celField;
    private JTextField addressField;
    private JTextField genreField;
    private JTextField firstNameField;
    private JTextField lastNameFField;
    private JTextField lastNameMField;
    private PatientController controller;

    public Patients() {
        super("Patient Management Interface");
        listModel = new DefaultListModel<>();
        listView = new JList<>(listModel);

        dateOfBirthField = new JTextField(10);
        celField = new JTextField(10);
        addressField = new JTextField(30);
        genreField = new JTextField(10);
        firstNameField = new JTextField(20);
        lastNameFField = new JTextField(20);
        lastNameMField = new JTextField(20);

        controller = new PatientController();

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        listView.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Patient selectedPatient = listView.getSelectedValue();
                if (selectedPatient != null) {
                    // Rellenar los campos con los detalles del paciente seleccionado
                    dateOfBirthField.setText(PatientController.formatDate(selectedPatient.getDateOfBirth()));
                    celField.setText(Long.toString(selectedPatient.getCel()));
                    addressField.setText(selectedPatient.getAddress());
                    genreField.setText(selectedPatient.getGenre());
                    firstNameField.setText(selectedPatient.getFirstName());
                    lastNameFField.setText(selectedPatient.getLastNameF());
                    lastNameMField.setText(selectedPatient.getLastNameM());
                }
            }
        });


        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date dateOfBirth = parseDate(dateOfBirthField.getText());
                int cel = Integer.parseInt(celField.getText());
                String address = addressField.getText();
                String genre = genreField.getText();
                String firstName = firstNameField.getText();
                String lastNameF = lastNameFField.getText();
                String lastNameM = lastNameMField.getText();

                controller.addPatient(dateOfBirth, cel, address, genre, firstName, lastNameF, lastNameM);
                updateList();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Patient selectedPatient = listView.getSelectedValue();
                if (selectedPatient != null) {
                    int patientId = selectedPatient.getPatientId();
                    Date dateOfBirth = parseDate(dateOfBirthField.getText());
                    long cel = Long.parseLong(celField.getText());
                    String address = addressField.getText();
                    String genre = genreField.getText();
                    String firstName = firstNameField.getText();
                    String lastNameF = lastNameFField.getText();
                    String lastNameM = lastNameMField.getText();

                    controller.updatePatient(patientId, dateOfBirth, cel, address, genre, firstName, lastNameF, lastNameM);
                    updateList();
                }
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Patient selectedPatient = listView.getSelectedValue();
                if (selectedPatient != null) {
                    int patientId = selectedPatient.getPatientId();
                    controller.deletePatient(patientId);
                    updateList();
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2));
        inputPanel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        inputPanel.add(dateOfBirthField);
        inputPanel.add(new JLabel("Cel:"));
        inputPanel.add(celField);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Genre:"));
        inputPanel.add(genreField);
        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Last Name (Father's):"));
        inputPanel.add(lastNameFField);
        inputPanel.add(new JLabel("Last Name (Mother's):"));
        inputPanel.add(lastNameMField);
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
        listModel.addAll(controller.getPatients());
        clearInputFields();
    }

    private void clearInputFields() {
        dateOfBirthField.setText("");
        celField.setText("");
        addressField.setText("");
        genreField.setText("");
        firstNameField.setText("");
        lastNameFField.setText("");
        lastNameMField.setText("");
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

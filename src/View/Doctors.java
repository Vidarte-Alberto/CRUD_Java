package View;
import Controller.DoctorController;
import Models.Doctor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Doctors extends JFrame {
    private DefaultListModel<Doctor> listModel;
    private JList<Doctor> listView;
    private JTextField specialityIdField;
    private JTextField firstNameField;
    private JTextField lastNameMField;
    private JTextField lastNameFField;
    private JTextField genreField;
    private JTextField addressField;
    private JTextField dateOfBirthField;
    private JTextField celField;
    private DoctorController controller;

    public Doctors() {
        super("Doctor Management Interface");
        listModel = new DefaultListModel<>();
        listView = new JList<>(listModel);

        specialityIdField = new JTextField(5);
        firstNameField = new JTextField(20);
        lastNameMField = new JTextField(20);
        lastNameFField = new JTextField(20);
        genreField = new JTextField(1);
        addressField = new JTextField(50);
        dateOfBirthField = new JTextField(10);
        celField = new JTextField(15);

        controller = new DoctorController();

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int specialityId = Integer.parseInt(specialityIdField.getText());
                String firstName = firstNameField.getText();
                String lastNameM = lastNameMField.getText();
                String lastNameF = lastNameFField.getText();
                String genre = genreField.getText();
                String address = addressField.getText();
                Date dateOfBirth = parseDate(dateOfBirthField.getText());
                long cel = Long.parseLong(celField.getText());

                controller.createDoctor(specialityId, firstName, lastNameM, lastNameF, genre, address, dateOfBirth, cel);
                updateList();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Doctor selectedDoctor = listView.getSelectedValue();
                if (selectedDoctor != null) {
                    int specialityId = Integer.parseInt(specialityIdField.getText());
                    String firstName = firstNameField.getText();
                    String lastNameM = lastNameMField.getText();
                    String lastNameF = lastNameFField.getText();
                    String genre = genreField.getText();
                    String address = addressField.getText();
                    Date dateOfBirth = parseDate(dateOfBirthField.getText());
                    long cel = Long.parseLong(celField.getText());

                    selectedDoctor.setSpecialityId(specialityId);
                    selectedDoctor.setFirstName(firstName);
                    selectedDoctor.setLastNameM(lastNameM);
                    selectedDoctor.setLastNameF(lastNameF);
                    selectedDoctor.setGenre(genre);
                    selectedDoctor.setAddress(address);
                    selectedDoctor.setDateOfBirth(dateOfBirth);
                    selectedDoctor.setCel(cel);

                    // Llama al método para actualizar en la base de datos
                    controller.updateDoctor(selectedDoctor.getDoctorId(), specialityId, firstName, lastNameM, lastNameF, genre, address, dateOfBirth, cel);
                } else {
                    int specialityId = Integer.parseInt(specialityIdField.getText());
                    String firstName = firstNameField.getText();
                    String lastNameM = lastNameMField.getText();
                    String lastNameF = lastNameFField.getText();
                    String genre = genreField.getText();
                    String address = addressField.getText();
                    Date dateOfBirth = parseDate(dateOfBirthField.getText());
                    long cel = Long.parseLong(celField.getText());

                    // Crea un nuevo doctor y lo agrega a la base de datos
                    controller.createDoctor(specialityId, firstName, lastNameM, lastNameF, genre, address, dateOfBirth, cel);
                }
                updateList();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Doctor selectedDoctor = listView.getSelectedValue();
                if (selectedDoctor != null) {
                    // Llama al método para eliminar de la base de datos
                    controller.deleteDoctor(selectedDoctor.getDoctorId());
                    updateList();
                }
            }
        });

        listView.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Doctor selectedDoctor = listView.getSelectedValue();
                if (selectedDoctor != null) {
                    // Rellena los campos con los detalles del doctor seleccionado
                    specialityIdField.setText(Integer.toString(selectedDoctor.getSpecialityId()));
                    firstNameField.setText(selectedDoctor.getFirstName());
                    lastNameMField.setText(selectedDoctor.getLastNameM());
                    lastNameFField.setText(selectedDoctor.getLastNameF());
                    genreField.setText(selectedDoctor.getGenre());
                    addressField.setText(selectedDoctor.getAddress());
                    dateOfBirthField.setText(new SimpleDateFormat("yyyy-MM-dd").format(selectedDoctor.getDateOfBirth()));
                    celField.setText(Long.toString(selectedDoctor.getCel()));
                }
            }
        });

        // Panel de entrada con una cuadrícula de 2 columnas
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        inputPanel.add(new JLabel("Speciality ID:"));
        inputPanel.add(specialityIdField);
        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Last Name (Mother):"));
        inputPanel.add(lastNameMField);
        inputPanel.add(new JLabel("Last Name (Father):"));
        inputPanel.add(lastNameFField);
        inputPanel.add(new JLabel("Genre:"));
        inputPanel.add(genreField);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        inputPanel.add(dateOfBirthField);
        inputPanel.add(new JLabel("Cel:"));
        inputPanel.add(celField);

        // Agregar los botones
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
        listModel.addAll(controller.getAllDoctors());
        clearInputFields();
    }

    private void clearInputFields() {
        specialityIdField.setText("");
        firstNameField.setText("");
        lastNameMField.setText("");
        lastNameFField.setText("");
        genreField.setText("");
        addressField.setText("");
        dateOfBirthField.setText("");
        celField.setText("");
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

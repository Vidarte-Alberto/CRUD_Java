package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModelSelector extends JFrame implements ActionListener {
    private JButton patientsButton;
    private JButton surgeriesButton;
    private JButton prescriptionsButton;
    private JButton doctorsButton;
    private JButton specialitiesButton;
    private JButton signOutButton;
    private JButton medicinesButton;

    public ModelSelector() {
        setTitle("HOSPITAL INTERFACE");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonPanel = new JPanel();

        patientsButton = new JButton("Patients");
        surgeriesButton = new JButton("Surgeon");
        prescriptionsButton = new JButton("Prescriptions");
        doctorsButton = new JButton("Doctors");
        specialitiesButton = new JButton("Specialities");
        medicinesButton = new JButton("Medicines");
        signOutButton = new JButton("Sign Out");

        patientsButton.addActionListener(this);
        surgeriesButton.addActionListener(this);
        prescriptionsButton.addActionListener(this);
        doctorsButton.addActionListener(this);
        specialitiesButton.addActionListener(this);
        medicinesButton.addActionListener(this);
        signOutButton.addActionListener(this);

        buttonPanel.setLayout(new GridLayout(2, 3));
        buttonPanel.add(patientsButton);
        buttonPanel.add(surgeriesButton);
        buttonPanel.add(prescriptionsButton);
        buttonPanel.add(doctorsButton);
        buttonPanel.add(specialitiesButton);
        buttonPanel.add(medicinesButton);
        buttonPanel.add(signOutButton);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == patientsButton) {
            new Patients();
        } else if (e.getSource() == surgeriesButton) {
            new Surgeon();
        } else if (e.getSource() == prescriptionsButton) {
            new Prescriptions();
        } else if (e.getSource() == doctorsButton) {
            new Doctors();
        } else if (e.getSource() == specialitiesButton) {
            new Specialities();
        } else if (e.getSource() == signOutButton) {
            new LoginOrRegister();
            this.dispose();
        } else if (e.getSource() == medicinesButton) {
            new Medicines();
        }
    }
}

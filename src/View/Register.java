package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.RegisterController;

public class Register extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public Register() {
        setTitle("Registro de Usuario");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        JLabel usernameLabel = new JLabel("Usuario:");
        JLabel passwordLabel = new JLabel("Contraseña:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        registerButton = new JButton("Registrar");
        registerButton.addActionListener(this);

        mainPanel.setLayout(new GridLayout(3, 2));
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        buttonPanel.add(registerButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Llama al controlador de registro para agregar el usuario a la base de datos
            RegisterController registerController = new RegisterController();
            boolean registrationSuccessful = registerController.registerUser(username, password);

            if (registrationSuccessful) {
                JOptionPane.showMessageDialog(this, "Registro exitoso.");
                new Login();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error en el registro. Inténtalo de nuevo.");
            }
        }
    }
}

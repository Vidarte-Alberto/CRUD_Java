package View;

import Controller.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login() {
        setTitle("Inicio de Sesión");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        JLabel usernameLabel = new JLabel("Usuario:");
        JLabel passwordLabel = new JLabel("Contraseña:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Iniciar Sesión");
        loginButton.addActionListener(this);

        mainPanel.setLayout(new GridLayout(3, 2));
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        buttonPanel.add(loginButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Crear una instancia del controlador
            LoginController loginController = new LoginController();

            // Llamar al método authenticate para verificar las credenciales
            if (loginController.authenticate(username, password)) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
                new Surgeon();
            } else {
                JOptionPane.showMessageDialog(this, "Inicio de sesión fallido. Inténtalo de nuevo.");
            }
        }
    }
}

package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginOrRegister extends JFrame implements ActionListener {
    private JButton loginButton;
    private JButton registerButton;

    public LoginOrRegister() {
        setTitle("Iniciar Sesión o Registrarse");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonPanel = new JPanel();

        loginButton = new JButton("Iniciar Sesión");
        registerButton = new JButton("Registrarse");

        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            new Login(); // Abre la vista de inicio de sesión
            this.dispose(); // Cierra la vista actual
        } else if (e.getSource() == registerButton) {
            new Register(); // Abre la vista de registro
            this.dispose(); // Cierra la vista actual
        }
    }
}

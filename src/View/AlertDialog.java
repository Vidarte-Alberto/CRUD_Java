package View;
import javax.swing.JOptionPane;

public class AlertDialog {

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void showError(int message) {
        String msg = String.valueOf(message);
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

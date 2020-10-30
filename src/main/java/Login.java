import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Login {

    public static void login() {

        /// login window
        JFrame jf = new JFrame("StorPass");
        jf.setSize(350, 180);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(null);
        jf.add(panelLogin);

        JLabel enterLabel = new JLabel("<html><body><center><h3>MasterPass:</h></center></body></html>");
        enterLabel.setBounds(50, 40, 100,20);
        panelLogin.add(enterLabel);

        JTextField fieldEnter = new JTextField(20);
        fieldEnter.setBounds(155, 40, 140,25);
        panelLogin.add(fieldEnter);

        JButton enterButton = new JButton("<html><body><center><h3>Enter</h></center></body></html>");
        enterButton.setBounds(120, 85, 100,33);
        panelLogin.add(enterButton);

        enterButton.addActionListener(e -> {
            if (Verify.verify(fieldEnter.getText())){
                jf.setVisible(false);
                try {
                    BaseWindow.baseWindow();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                JOptionPane pane = new JOptionPane();
                JOptionPane.showMessageDialog(pane, "MasterPass invalid");
                pane.setSize(100, 100);
                pane.setVisible(false);
            }
        });
    }
}

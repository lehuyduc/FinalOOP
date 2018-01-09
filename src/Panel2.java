import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Panel2 extends JPanel {

    private  int FRAME_WIDTH = GUI.PANEL2_WIDTH;
    private  int FRAME_HEIGHT = GUI.PANEL2_HEIGHT;
    public  int PANEL2X = GUI.PANEL2X;
    public  int PANEL2Y = GUI.PANEL2Y;
    private  int labelWidth = 100;
    private  int labelHeight = 20;
    private  int rowWidth = 255;
    private  int rowHeight = 20;
    private  int labelX = 10; // offset from the left of the panel
    private  int labelY = 50; // offset from the top of the panel to first label

    private GUIController guiController;
    private JLabel passwordLabel;
    private JPasswordField Password;
    private JButton Login;

    public static Panel2 createPanel2(GUIController guiController) {
        if (panel2==null) panel2 = new Panel2();
        return panel2.createNewPanel2(guiController);
    }
    
    private Panel2 createNewPanel2(GUIController guiController) {
        panel2.removeAll();
        panel2.setGuiController(guiController);

        panel2.setBounds(PANEL2X, PANEL2Y, FRAME_WIDTH, FRAME_HEIGHT);
        panel2.setOpaque(true);
        panel2.setLayout(null);
        panel2.setBorder(BorderFactory.createLineBorder(Color.black));

        panel2.passwordLabel();
        panel2.Password();
        panel2.Login();

        return panel2;
    }

    /*================================================================================================================*/
    private void passwordLabel() {
        passwordLabel = new JLabel();
        passwordLabel = new JLabel("Password:", JLabel.RIGHT);
        passwordLabel.setBounds(labelX,labelY,labelWidth,labelHeight);
        panel2.add(passwordLabel);
    }

    private void Password() {
        Password = new JPasswordField();
        Password.setBounds(labelX + labelWidth + 20,labelY,rowWidth,rowHeight);
        panel2.add(Password);
    }

    private void Login() {
        Login = new JButton("Login");
        Login.setBounds(labelX + labelWidth,labelY + rowHeight + 10, rowWidth, rowHeight);
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiController.setPassword(Password.getText());
                Password.setText("");
                JOptionPane.showMessageDialog(Login, "New password is being used");
            }
        });

        panel2.add(Login);
    }


    /*================================================================================================================*/
    public GUIController getGuiController() {
        return guiController;
    }

    public void setGuiController(GUIController guiController) {
        this.guiController = guiController;
    }

    /*================================================================================================================*/
    private static Panel2 panel2;
}

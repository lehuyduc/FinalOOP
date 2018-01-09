import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Panel1 extends JPanel {

    private static int PANEL_WIDTH = GUI.PANEL1_WIDTH;
    private static int PANEL_HEIGHT = GUI.PANEL1_HEIGHT;
    public static int PANEL1X = GUI.PANEL1X;
    public static int PANEL1Y = GUI.PANEL1Y;
    private static int labelX = 10; // offset from the left of the panel
    private static int labelY = 10; // offset from the top of the panel to first label
    private static int rowX = labelX + 100; // offset from the left of the panel
    private static int rowY = labelY; // offset from the top of the panel to first label
    private static int vg = 30;                // Vertical gap
    private static int hg = 20;                // Horizontal gap
    private static int labelWidth = 65;
    private static int labelHeight = 13;
    private static int rowWidth = 255;
    private static int rowHeight = 20;

    private GUIController guiController;

    private JTextField DocID = new JTextField();
    private JTextField DocName = new JTextField();
    private JTextField DocDate = new JTextField();
    private JTextField DocSignature = new JTextField();
    private JTextField DocSend = new JTextField();
    private JTextField Password = new JTextField();
    private JButton Credit = new JButton();
    private JButton Add = new JButton();
    private JButton SelectFile = new JButton();


    private File selectedFile;
    private String selectedFilePath;

    public void setGuiController(GUIController guiController) {
        this.guiController = guiController;
    }

    /*================================================================================================================*/
    private Panel1() {
        
    }

    public static Panel1 createPanel1(GUIController guiController) {
        if (panel1==null) panel1 = new Panel1();
        return panel1.createNewPanel1(guiController);
    }

    private Panel1 createNewPanel1(GUIController guiController) {
        panel1.guiController = guiController;
        panel1.setBounds(PANEL1X, PANEL1Y, PANEL_WIDTH, PANEL_HEIGHT);
        panel1.setOpaque(true);
        panel1.setLayout(null);

        JLabel IDLabel = new JLabel("ID:", JLabel.RIGHT);
        IDLabel.setSize(labelWidth, labelHeight);
        IDLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
        panel1.add(IDLabel);
        panel1.add(DocID());

        JLabel NameLabel = new JLabel("Name:", JLabel.RIGHT);
        NameLabel.setSize(labelWidth, labelHeight);
        NameLabel.setLocation(labelX, labelY + vg);
        panel1.add(NameLabel);
        panel1.add(DocName());

        JLabel DateLabel = new JLabel("Date:", JLabel.RIGHT);
        DateLabel.setSize(labelWidth, labelHeight);
        DateLabel.setLocation(labelX, labelY + 2 * vg);
        panel1.add(DateLabel);
        panel1.add(DocDate());

        JLabel SignatureLabel = new JLabel("Signature:", JLabel.RIGHT);
        SignatureLabel.setSize(labelWidth, labelHeight);
        SignatureLabel.setLocation(labelX, labelY + 3 * vg);
        panel1.add(SignatureLabel);
        panel1.add(DocSignature());

        JLabel SendLabel = new JLabel("Sent to:", JLabel.RIGHT);
        SendLabel.setSize(labelWidth, labelHeight);
        SendLabel.setLocation(labelX, labelY + 4 * vg);
        panel1.add(SendLabel);
        panel1.add(DocSend());

        JLabel SelectFileLabel = new JLabel("Add file:",JLabel.RIGHT);
        SelectFileLabel.setSize(labelWidth, labelHeight);
        SelectFileLabel.setLocation(labelX, labelY + 5 * vg);
        panel1.add(SelectFileLabel);
        panel1.add(SelectFileButton(panel1));

        JLabel passwordLabel = new JLabel("Password:",JLabel.RIGHT);
        passwordLabel = new JLabel("Password:", JLabel.RIGHT);
        passwordLabel.setBounds(labelX,labelY+6*vg,labelWidth,labelHeight);
        panel1.add(passwordLabel);
        panel1.Password();

        panel1.add(panel1.CreditButton());
        panel1.add(panel1.AddButton());

        panel1.setBorder(BorderFactory.createLineBorder(Color.black));
        return panel1;
    }

    private  JTextField DocID() {
        DocID.setBounds(rowX, rowY, rowWidth, rowHeight);
        return DocID;
    }

    private  JTextField DocName() {
        DocName.setBounds(rowX, rowY+vg, rowWidth, rowHeight);
        return DocName;
    }

    private  JTextField DocDate() {
        DocDate.setBounds(rowX, rowY+2*vg, rowWidth, rowHeight);
        return DocDate;
    }

    private  JTextField DocSignature() {
        DocSignature.setBounds(rowX, rowY+3*vg, rowWidth, rowHeight);
        return DocSignature;
    }

    private  JTextField DocSend() {
        DocSend.setBounds(rowX, rowY+4*vg, rowWidth, rowHeight);
        return DocSend;
    }

    private JButton SelectFileButton(Panel1 panel1) {
        SelectFile.setBounds(rowX, rowY+5*vg, rowWidth, rowHeight);
        SelectFile.setText("Open file location");
        JFileChooser fc = new JFileChooser();
        SelectFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(panel1);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fc.getSelectedFile();
                    selectedFilePath = selectedFile.getAbsolutePath();
                    SelectFile.setText(selectedFilePath);
                }
            }
        });
        return SelectFile;
    }

    private void Password() {
        Password = new JTextField();
        Password.setBounds(rowX,rowY+6*vg,rowWidth,rowHeight);
        panel1.add(Password);
    }

    private  JButton CreditButton() {
        Credit.setText("Credit!");
        Credit.setBounds(20, 230, 100, 30);
        Credit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Credit, "USTH Final OOP 2017 "
                        + "\nGroup 1 - Topic 5"
                        + "\nLUU Gia An [ USTHBI7-003 ]"
                        + "\nLE Huy Duc [ USTHBI7-036 ]"
                        + "\nBUI Vu Huy [ USTHBI7-082 ]"
                        + "\nHUYNH Vinh Nam [ USTHBI7-114 ]"
                        + "\nNGUYEN Ngoc Trung [ USTHBI7-154 ]");
            }
        });
        return Credit;
    }

    private  JButton AddButton() {
        Add.setText("Add");
        Add.setBounds(285, 230, 100, 30);
        Add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addRow();
            }
        });
        return Add;
    }

    private void addRow() {
        if (selectedFilePath==null) {JOptionPane.showMessageDialog(Add, "Please select a file"); return;}

        Document doc = new Document(DocID.getText(), DocName.getText(), DocDate.getText(), DocSignature.getText(), DocSend.getText(), selectedFilePath);
        if (doc.getID().equals("") || doc.getName().equals("") || doc.getDate().equals("") || doc.getSend().equals("") || doc.getSignature().equals("")) {
            JOptionPane.showMessageDialog(Add, "One or more field is blank");
            return;
        }
        guiController.addDocument(doc,Password.getText());

        // Clear txt
        DocID.setText("");
        DocName.setText("");
        DocDate.setText("");
        DocSignature.setText("");
        DocSend.setText("");
        SelectFile.setText("Open file location");
        selectedFile = null;
        selectedFilePath = null;
        Password.setText("");
    }


    //=======================================================================================================
    private static Panel1 panel1;
}

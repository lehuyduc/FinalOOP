

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.nio.*;

public class GUI extends JFrame {
    private String windowName;

    public static int FRAME_WIDTH = 1024;
    public static int FRAME_HEIGHT = 600;
    public static int PANELS_WIDTH = FRAME_WIDTH - 6;
    public static int PANELS_HEIGHT = FRAME_HEIGHT - 30;
    
    public static int PANEL1X = 2;
    public static int PANEL1Y = 0;
    public static int PANEL1_WIDTH = (PANELS_WIDTH-6) / 2;
    public static int PANEL1_HEIGHT = (PANELS_HEIGHT-4) /2;
    public static int PANEL2X = PANEL1X + PANEL1_WIDTH + 2;
    public static int PANEL2Y = PANEL1Y;
    public static int PANEL2_WIDTH = PANEL1_WIDTH;
    public static int PANEL2_HEIGHT = PANEL1_HEIGHT;
    public static int PANEL3X = PANEL1X;
    public static int PANEL3Y = PANEL1Y + PANEL1_HEIGHT + 2;
    public static int PANEL3_WIDTH = PANELS_WIDTH - 4;
    public static int PANEL3_HEIGHT = PANELS_HEIGHT - PANEL1_HEIGHT - 4;

    private JLayeredPane lpane = new JLayeredPane();
    private GUIController guiController;
    private Panel1 panel1;
    private Panel2 panel2;
    private Panel3 panel3;

    /*================================================================================================================*/
    public GUI(String windowName) {
        super();
        this.windowName = windowName;
    }

    /*================================================================================================================*/
    public void init() {
        JFrame frame = createJFrame(windowName);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());
        frame.add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, PANELS_WIDTH, PANELS_HEIGHT);

        guiController = new GUIController();
        addPanel1();
        addPanel2();
        addPanel3();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    /*================================================== IMPORTANT! ==================================================*/

    private JFrame createJFrame(String windowName) {
        JFrame frame = new JFrame(windowName);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    /*=================================================== PANEL #1 ===================================================*/
    private void addPanel1() {
        panel1 = Panel1.createPanel1(guiController);
        guiController.setPane1l(panel1);
        lpane.add(panel1, 0, 0);
    }

    /*=================================================== PANEL #2 ===================================================*/
    private void addPanel2() {
        panel2 = Panel2.createPanel2(guiController);
        guiController.setPanel2(panel2);
        lpane.add(panel2, new Integer(1), 0);
    }

    /*=================================================== PANEL #3 ===================================================*/
    private void addPanel3() {
        panel3 = Panel3.createPanel3(guiController);
        guiController.setPanel3(panel3);
        lpane.add(panel3, new Integer(2), 0);
    }

    /*=============================================== EVERYTHING TABLE ===============================================*/


    /*================================================================================================================*/
    public static JPanel createJPanel(String text) {
        JPanel panel = new JPanel(new GridLayout(1, 1));
        JLabel lb = new JLabel(text);
        lb.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lb);
        return panel;
    }

    /*================================================================================================================*/

    /*================================================================================================================*/

    /*================================================================================================================*/
//    public static void UpdateFile() throws FileNotFoundException
//    {
//        FileOutputStream fo = new FileOutputStream("Output.txt");
//        PrintWriter pw = new PrintWriter(fo);
//
//        for(int i = 0; i< Home.StoredList.getCURRENTLIST().size(); i++)
//        {
//            Home.StoredList.getCURRENTLIST().get(i).setID(i);
//            pw.printf("%s",Home.StoredList.getCURRENTLIST().get(i).getID()+" "+Home.StoredList.getCURRENTLIST().get(i).getNAME()+" "+Home.StoredList.getCURRENTLIST().get(i).getDOB()+" "+Home.StoredList.getCURRENTLIST().get(i).getACCOUNT().toString()+" "+Home.StoredList.getCURRENTLIST().get(i).getPASSWORD()+"\r\n");
//        }
//        pw.close();
//    }

    /*============================================= WRITE TABLE TO EXCEL =============================================*/
//    private static void writeToExcell(JTable table, Path path) throws FileNotFoundException, IOException {
//        new WorkbookFactory();
//        Workbook wb = new XSSFWorkbook(); //Excell workbook
//        Sheet sheet = wb.createSheet(); //WorkSheet
//        Row row = sheet.createRow(2); //Row created at line 3
//        TableModel model = table.getModel(); //Table model
//
//
//        Row headerRow = sheet.createRow(0); //Create row at line 0
//        for(int headings = 0; headings < model.getColumnCount(); headings++){ //For each column
//            headerRow.createCell(headings).setCellValue(model.getColumnName(headings));//Write column name
//        }
//
//        for(int rows = 0; rows < model.getRowCount(); rows++){ //For each table row
//            for(int cols = 0; cols < table.getColumnCount(); cols++){ //For each table column
//                row.createCell(cols).setCellValue(model.getValueAt(rows, cols).toString()); //Write value
//            }
//
//            //Set the row to the next one in the sequence
//            row = sheet.createRow((rows + 3));
//        }
//        wb.write(new FileOutputStream(path.toString()));//Save the file
//    }


    public GUIController getGuiController() {
        return guiController;
    }

    public void setGuiController(GUIController guiController) {
        this.guiController = guiController;
    }
}
import com.sun.javafx.scene.control.skin.DatePickerContent;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

public class Panel3 extends JPanel implements Serializable {

    private static int PANEL_WIDTH = GUI.PANEL3_WIDTH;
    private static int PANEL_HEIGHT = GUI.PANEL3_HEIGHT;
    public static int PANEL3X = GUI.PANEL3X;
    public static int PANEL3Y = GUI.PANEL3Y;
    private static int buttonX = 855; // offset from the left of the panel
    private static int buttonY = 70; // offset from the top of the panel to first label
    private static int buttonWidth = 150;
    private static int buttonHeight = 25;
    private static int vg = 30;                // Vertical gap
    private static int hg = 20;                // Horizontal gap
    private static int rowWidth = 255;
    private static int rowHeight = 20;

    private GUIController guiController;

    private JTable jTable1 = new JTable();
    private JScrollPane jScrollPanel = new JScrollPane();
    private JTextField filterTxt = new JTextField();
    DefaultTableModel tm;       // tm stands for Table model
    Object[] cols = null;

    private JButton delButton = new JButton();
    private JButton saveButton = new JButton();
    private JButton exportButton = new JButton();
    private JButton loadButton = new JButton();
    private JButton openButton = new JButton();

    private Vector<Document> documents = new Vector<>();
    private String password = "";

    public static Panel3 createPanel3(GUIController guiController) {
        Panel3 panel3 = new Panel3();
        panel3.guiController = guiController;

        panel3.setBounds(PANEL3X, PANEL3Y, PANEL_WIDTH, PANEL_HEIGHT);

        panel3.setLayout(null);
        panel3.setOpaque(true);

        JLabel FilterLabel = new JLabel("Filter:" , JLabel.RIGHT);
        FilterLabel.setSize(65, 13);
        FilterLabel.setLocation(10,15);
        panel3.add(FilterLabel);
        panel3.add(panel3.filterTxt());

        panel3.jScrollPanel = new JScrollPane(panel3.jTable1);
        panel3.jScrollPanel.setBounds(30, 58, 814, 168);

        panel3.createColumns();

        panel3.add(panel3.jScrollPanel);

        panel3.add(panel3.deleteButton());
        panel3.add(panel3.saveButton());
        panel3.add(panel3.exportButton());
        panel3.add(panel3.loadDataButton());
        panel3.add(panel3.openButton());


        panel3.setBorder(BorderFactory.createLineBorder(Color.black));

        return panel3;
    }

    /*================================================================================================================*/
    // Filter data
    private void filter(String query) {
        try {
            TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(tm);
            jTable1.setRowSorter(tr);

            tr.setRowFilter(RowFilter.regexFilter(query));
        }
        catch (Exception e) {
            // Prevent Error IllegalArgumentException
        }
    }

    private void filterTxtKeyReleased(KeyEvent evt) {
        String query = filterTxt.getText();
        filter(query);
    }

    private JTextField filterTxt() {
        filterTxt.setBounds(85, 13, 255, 20);

        filterTxt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                filterTxtKeyReleased(evt);
            }
        });

        return filterTxt;
    }

    /*================================================================================================================*/
    private void createColumns() {
        // Get Table model
        tm = (DefaultTableModel) jTable1.getModel();

        // Add Columns
        cols = new String[]{"ID","Name","Date","Signature","Sent to"};
        tm.setColumnIdentifiers(cols);
    }

    /*================================================================================================================*/
    private JButton deleteButton() {
        delButton.setText("Delete");
        delButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        delButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                deleteRow();
            }
        });
        return delButton;
    }

    private JButton saveButton() {
        saveButton.setText("Sava data to file...");
        saveButton.setBounds(buttonX, buttonY + vg, buttonWidth, buttonHeight);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveData();
            }
        });
        return saveButton;
    }

    private JButton exportButton() {
        exportButton.setText("Export to excel file...");
        exportButton.setBounds(buttonX, buttonY + 2*vg, buttonWidth, buttonHeight);

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(exportButton);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fc.getSelectedFile();
                    String selectedFilePath = selectedFile.getAbsolutePath();
                    String code = String.valueOf(System.currentTimeMillis());

                    Date date = new Date(); // your date
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH) + 1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    toExcel(jTable1,new File(selectedFilePath + File.separator + "Excel" + day + "_" + month + "_" + year + ".xls"));
                }
            }
        });
        return exportButton;
    }

    private JButton loadDataButton() {
        loadButton.setText("Load saved data");
        loadButton.setBounds(buttonX, buttonY + 3*vg, buttonWidth, buttonHeight);
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loadData();
            }
        });
        return loadButton;
    }

    private JButton openButton() {
        openButton.setText("Open a file");
        openButton.setBounds(buttonX, buttonY + 4*vg, buttonWidth, buttonHeight);
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openFile();
            }
        });
        return openButton;
    }

    /*================================================================================================================*/
    // Add Rows data
    public void populate(Document doc, String password) {
        String[] rowData = {doc.getID(), doc.getName(), doc.getDate(), doc.getSignature(), doc.getSend()};
        documents.add(doc);
        encryptDocument(doc,password);
        tm.addRow(rowData);
    }

    private void deleteRow() {
        try {
            int row = jTable1.getSelectedRow();
            int modelRow = jTable1.convertRowIndexToModel(row);
            Document doc = documents.get(row);
            documents.remove(row);
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(modelRow);

            try {
                Files.delete(Paths.get(doc.getFilePath()));
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "File already delete");
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            // Prevent Error ArrayIndexOutOfBoundsException
        }
    }

    private void openFile() {
        try {
            int row = jTable1.getSelectedRow();
            int modelRow = jTable1.convertRowIndexToModel(row);
            Document doc = documents.get(row);
            String filepath = doc.getFilePath();
            Path path = Paths.get(doc.getFilePath());
            File file = new File(path.toUri()), decryptedFile = null;
            if (Files.exists(path)) {
                decryptedFile = Encrypter.decrypt(file,"Temp",password);
                if (decryptedFile==null)
                    JOptionPane.showMessageDialog(this, "Wrong password or file is being used/deleted");
                else {
                    //JOptionPane.showMessageDialog(this, "File is saved to folder Temp");
                    if (file.exists())
                        try {
                            Desktop.getDesktop().open(decryptedFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "The file no longer exists or is corrupted");
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            // Prevent Error ArrayIndexOutOfBoundsException
        }
    }

    private void saveData() {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream("savedDocuments.dat");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            oos = new ObjectOutputStream(fout);
            if (documents!=null) oos.writeObject(documents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fout.close();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void encryptDocument(Document doc,String password) {
        File file;

        Utility.createFolder("Saves");

        if (doc.getFilePath()!=null) {
            file = new File(doc.getFilePath());
            if (file==null) {
                JOptionPane.showMessageDialog(this,"The file no longer exists or is corrupted");
                return;
            }

            File encryptedFile = Encrypter.encrypt(file,"Saves",password);
            if (encryptedFile==null) {
                JOptionPane.showMessageDialog(this, "File is corrupted or file can't be opened");
                return;
            }
            doc.setFilePath(encryptedFile.getAbsolutePath());
        }
    }

    private synchronized void loadData() {
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try {
            fin = new FileInputStream("savedDocuments.dat");
            ois = new ObjectInputStream(fin);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "No data to load");
            return;
        }

        Vector<Document> lastDocuments = null;
        try {
            lastDocuments = (Vector<Document>)(ois.readObject());
            fin.close();
            ois.close();
            if (lastDocuments==null) {throw new Exception("Data file not found");}
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Data file is corrupted or deleted");
            return;
        }

        documents.addAll(lastDocuments);
        documents.sort(new Comparator<Document>() {
            @Override
            public int compare(Document o1, Document o2) {
                return (o1.getFilePath()).compareTo(o2.getFilePath());
            }
        });

        for (int i=documents.size()-1;i>=1;i--) {
            try {
                if (documents.get(i).getFilePath().equals(documents.get(i - 1).getFilePath()))
                    documents.remove(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i=documents.size()-1;i>=0;i--)
            if (!Files.exists(Paths.get(documents.get(i).getFilePath()))) {documents.remove(i); continue;}

        for (int i=tm.getRowCount()-1;i>=0;i--) tm.removeRow(i);
        for (Document doc : documents) {
            if (new File(doc.getFilePath())==null) continue;
            String[] rowData = {doc.getID(), doc.getName(), doc.getDate(), doc.getSignature(), doc.getSend()};
            System.out.println(Utility.getFile(doc.getFilePath()));
            tm.addRow(rowData);
        }
    }

    public void toExcel(JTable table, File file){
        try{
            TableModel model = table.getModel();
            FileWriter excel = new FileWriter(file);

            for(int i = 0; i < model.getColumnCount(); i++){
                excel.write(model.getColumnName(i) + "\t");
            }

            excel.write("\n");

            for(int i=0; i< model.getRowCount(); i++) {
                for(int j=0; j < model.getColumnCount(); j++) {
                    excel.write(model.getValueAt(i,j).toString()+"\t");
                }
                excel.write("\n");
            }
            excel.close();
            JOptionPane.showMessageDialog(this, "Export successful");

        }catch(IOException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Export failed");
        }
    }



    //========================================================================================================

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

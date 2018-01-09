import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.print.Doc;
import javax.swing.*;

public class GUIController {
    Panel1 pane1l;
    Panel2 panel2;
    Panel3 panel3;

    public Panel1 getPane1l() {
        return pane1l;
    }

    public void setPane1l(Panel1 pane1l) {
        this.pane1l = pane1l;
    }

    public Panel2 getPanel2() {
        return panel2;
    }

    public void setPanel2(Panel2 panel2) {
        this.panel2 = panel2;
    }

    public Panel3 getPanel3() {
        return panel3;
    }

    public void setPanel3(Panel3 panel3) {
        this.panel3 = panel3;
    }

    //******************************************
    public void addDocument(Document document, String password) {
        panel3.populate(document, password);
    }

    public void setPassword(String password) {
        panel3.setPassword(password);
    }
}

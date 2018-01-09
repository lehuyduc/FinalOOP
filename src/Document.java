import java.io.Serializable;

public class Document implements Serializable {
    private String ID = "Document's I";
    private String name = "Document's name";
    private String date = "Date modified";
    private String signature = "Signature";
    private String send = "Sent to";
    private String filePath;

    public Document(String ID, String name, String date, String signature, String send, String filePath) {
        this.ID = ID;
        this.name = name;
        this.date = date;
        this.signature = signature;
        this.send = send;
        this.filePath = filePath;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    //====================================================================================
}

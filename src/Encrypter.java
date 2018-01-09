import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Encrypter {

    private static int base = 7817869;
    private static int keyer;
    private static int marker;
    private static final int name_padding = 100;
    private static final int extension_padding = 10;

    public static int getKeyer(String password) {
        int keyer = base;
        for (int i=0;i<password.length();i++)
            keyer = keyer*29 + (int)(password.charAt(i));
        return keyer;
    }

    public static int getMarker(String password) {
        int marker = base;
        for (int i=0;i<password.length();i++)
            marker = marker*31 + (int)(password.charAt(i));
        return marker;
    }

    public static File encrypt(File inputFile, String saveLocation, String password) {
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            String fileName = inputFile.getName();
            File outputFile = new File(saveLocation + File.separator + getBaseName(inputFile.getName()) + System.currentTimeMillis() + "." + getFileExtension(inputFile));
            fin = new FileInputStream(inputFile);
            fout = new FileOutputStream(outputFile);

            int keyer = getKeyer(password), marker = getMarker(password);
            byte[] byteMarker = new byte[] {(byte)(marker>>24),(byte)(marker>>16),(byte)(marker>>8),(byte)(marker)};
            fout.write(byteMarker);

            byte[] tmp = new byte[1];
            while (fin.read(tmp,0,1)!=-1) {
                tmp[0] ^= keyer;
                fout.write(tmp[0]);
            }

            fin.close();
            fout.close();
            return outputFile;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            fin.close();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File decrypt(File inputFile, String saveLocation, String password) {
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            Utility.createFolder(saveLocation);
            fin = new FileInputStream(inputFile);
            File outputFile = new File(saveLocation + File.separator + getBaseName(inputFile.getName()) + "_decoded." + getFileExtension(inputFile));
            fout = new FileOutputStream(outputFile);

            int keyer = getKeyer(password), marker = getMarker(password);

            byte[] fileMarker = new byte[4];
            fin.read(fileMarker);

            int givenMarker = ByteBuffer.wrap(fileMarker).getInt();
            System.out.println(givenMarker + " " + marker);
            if (givenMarker!=marker) {throw new Exception("Password is false");}

            byte[] tmp = new byte[1];
            while (fin.read(tmp,0,1)!=-1) {
                tmp[0] ^= keyer;
                fout.write(tmp[0]);
            }

            fin.close();
            fout.close();
            return outputFile;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            fin.close();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkPassword(File file, String password) {
        if (file==null) return false;

        int keyer = getKeyer(password), marker = getMarker(password);
        byte[] fileMarker = new byte[1];
        return false;
    }

    private static String getBaseName(String fileName) {
        String baseName = null;
        if (fileName.indexOf(".") > 0) {
            baseName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        else baseName = fileName;
        return baseName;
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

}

package config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Config<T> {


    public static Scanner scanner() {
        return new Scanner(System.in);
    }

    public static String getUnEmptyString() {
        String string;
        while (true) {
            string = scanner().nextLine();
            if (string.trim().equals("")) {
                System.err.println("Invalid input!");
            } else {
                break;
            }
        }
        return string;
    }

    public static int getValidInteger() {
        String strChoice = Config.scanner().nextLine();
        if (strChoice.matches("[0-9]+")) {
            return Integer.parseInt(strChoice);
        } else {
            return -1;
        }
    }

    public T read(String path) {
        try (
                FileInputStream fis = new FileInputStream(path);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            return (T) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public void write(String path, T data) {
        try (
                FileOutputStream fos = new FileOutputStream(path);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

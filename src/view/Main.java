package view;

import controller.UserController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public Main() {
        if (new UserController().getCurrentUser() == null) {
            new ViewMainMenu().menu();
        } else {
            new ViewHome().mainMenu(0);
        }
    }


    public static void main(String[] args) {
        new Main();
    }

}

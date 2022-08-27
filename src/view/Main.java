package view;

import controller.UserController;

public class Main {

    public Main() {
        if (new UserController().getCurrentUser() == null) {
            new ViewMainMenu().menu();
        } else {
            new ViewHome().menu();
        }
    }


    public static void main(String[] args) {
        new Main();
    }

}

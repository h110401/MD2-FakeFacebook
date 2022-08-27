package view;

import config.Config;
import controller.UserController;
import dto.request.SignInDTO;
import dto.request.SignUpDTO;
import dto.response.ResponseMessenger;
import model.account.User;
import plugin.Menu;

import java.util.List;

public class ViewMainMenu {

    private final UserController userController = new UserController();
    private final List<User> userList = userController.getUserList();

    public void menu() {

        Menu menu = new Menu();
        menu.addHeader("MENU");
        menu.addChoice("Login");
        menu.addChoice("Register");
        menu.addChoice("Exit");
        menu.print();

        int choice = Config.getValidInteger();

        switch (choice) {
            case 1:
                this.formLogin();
                break;
            case 2:
                this.formRegister();
                break;
            case 3:
                System.exit(0);
            case 4:
                this.showUserList();
                break;
            default:
                System.err.println("Invalid choice");
        }
        menu();
    }

    private void showUserList() {
        for (User user : userList) {
            System.out.println((user.getId()) + ". " + user.getUsername());
        }
    }

    private void formRegister() {
        System.out.println("*****FORM REGISTER*****");
        //id
        int id = userController.getLastId();
        //name
        String name;
        while (true) {
            System.out.println("Enter name:");
            name = Config.scanner().nextLine();
            if (name.matches("[A-Z][a-zA-Z0-9 ]{1,39}")) {
                break;
            } else {
                System.out.println("Invalid name, try again!");
            }
        }
        //username
        String username;
        while (true) {
            System.out.println("Enter username:");
            username = Config.scanner().nextLine();
            if (username.matches("[a-zA-Z0-9]{1,30}")) {
                break;
            } else {
                System.out.println("Invalid name, try again!");
            }
        }
        //email
        String email;
        while (true) {
            System.out.println("Enter email:");
            email = Config.scanner().nextLine();
            if (email.matches("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-z]+){1,2}")) {
                break;
            } else {
                System.out.println("Invalid email, try again!");
            }
        }
        //password
        String password;
        while (true) {
            System.out.println("Enter password:");
            password = Config.scanner().nextLine();
            // ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{1,10}$
            if (password.matches("[a-zA-Z0-9]{1,30}")) {
                break;
            } else {
                System.out.println("Invalid password, try again!");
            }
        }

        SignUpDTO signUpDTO = new SignUpDTO(id, name, username, email, password);

        ResponseMessenger responseMessenger = userController.register(signUpDTO);

        switch (responseMessenger.getMessage()) {
            case "username_existed":
                System.err.println("User already exists");
                break;
            case "email_existed":
                System.err.println("Email already exists");
                break;
            case "success":
                System.out.println("Create success");
        }
    }

    private void formLogin() {
        System.out.println("*****FORM LOGIN*****");

        System.out.println("Enter username");
        String username = Config.scanner().nextLine();
        System.out.println("Enter password");
        String password = Config.scanner().nextLine();

        if (!username.matches("[a-zA-Z0-9]{1,30}") || !password.matches("[a-zA-Z0-9]{1,30}")) {
            System.out.println("Invalid username or password");
            return;
        }

        SignInDTO signInDTO = new SignInDTO(username, password);
        ResponseMessenger responseMessenger = userController.login(signInDTO);
        switch (responseMessenger.getMessage()) {
            case "username_not_exist":
                System.err.println("User not exists!");
                break;
            case "password_not_correct":
                System.err.println("Wrong password!");
                break;
            case "user_blocked":
                System.err.println("This user is blocked!");
                break;
            case "login_success":
                new ViewHome().menu();
        }
    }
}

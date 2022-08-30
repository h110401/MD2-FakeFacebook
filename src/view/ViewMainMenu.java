package view;

import config.Config;
import controller.UserController;
import dto.request.SignInDTO;
import dto.request.SignUpDTO;
import dto.response.ResponseMessenger;
import model.account.User;
import plugin.Alert;

import static plugin.ConsoleColors.*;

import java.util.List;

public class ViewMainMenu {

    private final UserController userController = new UserController();
    private final List<User> userList = userController.getUserList();

    public void menu() {

//        menu.print();  ━ ┏ ┛ ┗ ┓ ┃ ┫ ╋ ┣ ︱ ┻ ┳ , ╭ ╮ ╰ ╯ ╱ █

        System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
        System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + "                 ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "      " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "       " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + "                 ┃");
        System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                ┏━━━━━━━━━━━━━━━━┓                ┃");
        System.out.println("┃                ┃  1.  Login     ┃                ┃");
        System.out.println("┃                ┗━━━━━━━━━━━━━━━━┛                ┃");
        System.out.println("┃                ┏━━━━━━━━━━━━━━━━┓                ┃");
        System.out.println("┃                ┃  2.  Register  ┃                ┃");
        System.out.println("┃                ┗━━━━━━━━━━━━━━━━┛                ┃");
        System.out.println("┃                                  " + RED + "┏━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
        System.out.println("┃                                  " + RED + "┃  3. Exit   ┃" + BLUE_BRIGHT + "  ┃");
        System.out.println("┃                                  " + RED + "┗━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print("┃  Enter your choice: ");
        int choice = Config.getValidInteger();
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

        switch (choice) {
            case 1:
                this.formLogin("", "", 0);
                break;
            case 2:
                this.formRegister("", "", "", "", 0);
                break;
            case 3:
                System.exit(0);
            case 4:
                this.showUserList();
                break;
            default:
                Alert.printRedAlert("Invalid choice!");
        }
        menu();
    }

    private void showUserList() {
        for (User user : userList) {
            System.out.println((user.getId()) + ". " + user.getUsername());
        }
    }

    private void formRegister(String name, String username, String email, String password, int choice) {
        while (true) {
            printRegisterView(name, username, email, password, choice);
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.print("┃  Enter your choice: ");
            choice = Config.getValidInteger();
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            switch (choice) {
                case 1:
                    printRegisterView(name, username, email, password, choice);
                    System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.print("┃  Enter name: ");
                    String oldName = name;
                    name = Config.scanner().nextLine();
                    System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    if (!name.matches("[A-Z][a-zA-Z0-9 ]{1,39}")) {
                        Alert.printRedAlert("Invalid name, try again!");
                        name = oldName;
                    }
                    break;
                case 2:
                    printRegisterView(name, username, email, password, choice);
                    System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.print("┃  Enter username: ");
                    String oldUsername = username;
                    username = Config.scanner().nextLine();
                    System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    if (!username.matches("[a-zA-Z0-9]{1,30}")) {
                        Alert.printRedAlert("Invalid username, try again!");
                        username = oldUsername;
                    }
                    break;
                case 3:
                    printRegisterView(name, username, email, password, choice);
                    System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.print("┃  Enter email: ");
                    email = Config.scanner().nextLine();
                    String oldEmail = email;
                    System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    if (!email.matches("[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-z]+){1,2}")) {
                        Alert.printRedAlert("Invalid email, try again!");
                        email = oldEmail;
                    }
                    break;
                case 4:
                    printRegisterView(name, username, email, password, choice);
                    System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.print("┃  Enter password: ");
                    String oldPassword = password;
                    password = Config.scanner().nextLine();
                    System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    // ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{1,10}$
                    if (!password.matches("[a-zA-Z0-9]{1,30}")) {
                        Alert.printRedAlert("Invalid password, try again!");
                        password = oldPassword;
                    }
                    break;
                case 5:
                    break;
                case 6:
                    return;
                default:
                    Alert.printRedAlert("Invalid choice!");
            }
            if (choice == 5) break;
            choice = 0;
        }


        int id = userController.getLastId();

        SignUpDTO signUpDTO = new SignUpDTO(id, name, username, email, password);

        ResponseMessenger responseMessenger = userController.register(signUpDTO);

        choice = 0;

        switch (responseMessenger.getMessage()) {
            case "username_existed":
                Alert.printRedAlert("User already exists");
                break;
            case "email_existed":
                Alert.printRedAlert("Email already exists");
                break;
            case "success":
                Alert.printCyanAlert("Create success!!");
                return;
        }
        formRegister(name, username, email, password, choice);
    }

    private void formLogin(String username, String password, int choice) {
        while (true) {
            printLoginView(username, password, choice);

            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.print("┃  Enter your choice: ");
            choice = Config.getValidInteger();
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            switch (choice) {
                case 1:
                    printLoginView(username, password, choice);
                    System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.print("┃  Enter username: ");
                    String oldUsername = username;
                    username = Config.scanner().nextLine();
                    System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    if (!username.matches("[a-zA-Z0-9]{1,30}")) {
                        Alert.printRedAlert("Invalid username, try again!");
                        username = oldUsername;
                    }
                    break;
                case 2:
                    printLoginView(username, password, choice);
                    System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                    System.out.print("┃  Enter password: ");
                    String oldPassword = password;
                    password = Config.scanner().nextLine();
                    System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
                    // ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{1,10}$
                    if (!password.matches("[a-zA-Z0-9]{1,30}")) {
                        Alert.printRedAlert("Invalid password, try again!");
                        password = oldPassword;
                    }
                    break;
                case 3:
                    break;
                case 4:
                    return;
                default:
                    Alert.printRedAlert("Invalid choice!");
            }
            if (choice == 3) {
                break;
            }
            choice = 0;
        }

        if (!username.matches("[a-zA-Z0-9]{1,30}") || !password.matches("[a-zA-Z0-9]{1,30}")) {
            Alert.printRedAlert("Invalid username or password!");
            return;
        }

        SignInDTO signInDTO = new SignInDTO(username, password);
        ResponseMessenger responseMessenger = userController.login(signInDTO);
        switch (responseMessenger.getMessage()) {
            case "username_not_exist":
                Alert.printRedAlert("User not exists!");
                break;
            case "password_not_correct":
                Alert.printRedAlert("Password is incorrect!");
                break;
            case "user_blocked":
                Alert.printRedAlert("This user is blocked!");
                break;
            case "login_success":
                Alert.printCyanAlert("Login success!");
                loadingScene();
                loadingScene();
                new ViewHome().mainMenu(0);
        }
        formLogin(username, password, choice);
    }

    private void loadingScene() {
        System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
        System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + "                 ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "      " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "       " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + "                 ┃");
        System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                " + WHITE_BRIGHT + "|   LOGGING IN   |" + BLUE_BRIGHT + "                ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                   " + WHITE_BRIGHT + "██" + BLUE_BRIGHT + "                             ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
        System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + "                 ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "      " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "       " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + "                 ┃");
        System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                " + WHITE_BRIGHT + "|   LOGGING IN   |" + BLUE_BRIGHT + "                ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                   " + WHITE_BRIGHT + "██   ██" + BLUE_BRIGHT + "                        ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
        System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + "                 ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "      " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "       " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
        System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + "                 ┃");
        System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                " + WHITE_BRIGHT + "|   LOGGING IN   |" + BLUE_BRIGHT + "                ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                   " + WHITE_BRIGHT + "██" + BLUE_BRIGHT + "   " + WHITE_BRIGHT + "██" + BLUE_BRIGHT + "   " + WHITE_BRIGHT + "██" + BLUE_BRIGHT + "                   ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void printLoginView(String username, String password, int choice) {
        switch (choice) {
            case 0:
                System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
                System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + "                 ┃");
                System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
                System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "      " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "       " + BLUE_BRIGHT + "               ┃");
                System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
                System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + "                 ┃");
                System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + WHITE_BRIGHT + "┃   LOG IN   ┃" + BLUE_BRIGHT + "                  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃      ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓      ┃");
                System.out.printf("┃      ┃  1. Username: %19s  ┃      ┃\n", username);
                System.out.println("┃      ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛      ┃");
                System.out.println("┃      ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓      ┃");
                System.out.printf("┃      ┃  2. Password: %19s  ┃      ┃\n", password);
                System.out.println("┃      ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛      ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━┓              " + RED + "┏━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┃  3. SUBMIT   ┃              " + RED + "┃  4.  BACK    ┃" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┗━━━━━━━━━━━━━━┛              " + RED + "┗━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;
            case 1:
                System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
                System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + "                 ┃");
                System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
                System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "      " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "       " + BLUE_BRIGHT + "               ┃");
                System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
                System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + "                 ┃");
                System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + WHITE_BRIGHT + "┃   LOG IN   ┃" + BLUE_BRIGHT + "                  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃      " + CYAN_BOLD_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "      ┃");
                System.out.printf("┃      " + CYAN_BOLD_BRIGHT + "┃  1. Username: %19s  ┃" + BLUE_BRIGHT + "      ┃\n", username);
                System.out.println("┃      " + CYAN_BOLD_BRIGHT + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "      ┃");
                System.out.println("┃      ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓      ┃");
                System.out.printf("┃      ┃  2. Password: %19s  ┃      ┃\n", password);
                System.out.println("┃      ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛      ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━┓              " + RED + "┏━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┃  3. SUBMIT   ┃              " + RED + "┃  4.  BACK    ┃" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┗━━━━━━━━━━━━━━┛              " + RED + "┗━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;
            case 2:
                System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
                System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + "                 ┃");
                System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
                System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "      " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "       " + BLUE_BRIGHT + "               ┃");
                System.out.println("┃                " + BLUE_BACKGROUND_BRIGHT + "      " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "          " + BLUE_BRIGHT + "               ┃");
                System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + "    " + WHITE_BACKGROUND_BRIGHT + "   " + BLUE_BRIGHT + BLUE_BACKGROUND_BRIGHT + "        " + BLUE_BRIGHT + "                 ┃");
                System.out.println("┃                    " + BLUE_BACKGROUND_BRIGHT + "           " + BLUE_BRIGHT + "                   ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                  " + BLUE_BACKGROUND_BRIGHT + WHITE_BRIGHT + "┃   LOG IN   ┃" + BLUE_BRIGHT + "                  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃      ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓      ┃");
                System.out.printf("┃      ┃  1. Username: %19s  ┃      ┃\n", username);
                System.out.println("┃      ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛      ┃");
                System.out.println("┃      " + CYAN_BOLD_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "      ┃");
                System.out.printf("┃      " + CYAN_BOLD_BRIGHT + "┃  2. Password: %19s  ┃" + BLUE_BRIGHT + "      ┃\n", password);
                System.out.println("┃      " + CYAN_BOLD_BRIGHT + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "      ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━┓              " + RED + "┏━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┃  3. SUBMIT   ┃              " + RED + "┃  4.  BACK    ┃" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┗━━━━━━━━━━━━━━┛              " + RED + "┗━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);

        }

    }

    public void printRegisterView(String name, String username, String email, String password, int choice) {
        switch (choice) {
            case 0:
                System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                         ╭-----╮  ┃");
                System.out.println("┃  " + BLUE_BACKGROUND_BRIGHT + WHITE_BRIGHT + "┃   REGISTER   ┃" + BLUE_BRIGHT + "                       |  " + WHITE_BRIGHT + "F" + BLUE_BRIGHT + "  |  ┃");
                System.out.println("┃                                         ╰-----╯  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  1. Name:  %-32s┃  ┃\n", name);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  2. Username:  %-28s┃  ┃\n", username);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  3. Email:  %-31s┃  ┃\n", email);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  4. Password:  %-28s┃  ┃\n", password);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━┓              " + RED + "┏━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┃  5. SUBMIT   ┃              " + RED + "┃  6.  BACK    ┃" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┗━━━━━━━━━━━━━━┛              " + RED + "┗━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;
            case 1:
                System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                         ╭-----╮  ┃");
                System.out.println("┃  " + BLUE_BACKGROUND_BRIGHT + WHITE_BRIGHT + "┃   REGISTER   ┃" + BLUE_BRIGHT + "                       |  " + WHITE_BRIGHT + "F" + BLUE_BRIGHT + "  |  ┃");
                System.out.println("┃                                         ╰-----╯  ┃");
                System.out.println("┃  " + CYAN_BOLD_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.printf("┃  " + CYAN_BOLD_BRIGHT + "┃  1. Name:  %-32s┃" + BLUE_BRIGHT + "  ┃\n", name);
                System.out.println("┃  " + CYAN_BOLD_BRIGHT + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  2. Username:  %-28s┃  ┃\n", username);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  3. Email:  %-31s┃  ┃\n", email);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  4. Password:  %-28s┃  ┃\n", password);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━┓              " + RED + "┏━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┃  5. SUBMIT   ┃              " + RED + "┃  6.  BACK    ┃" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┗━━━━━━━━━━━━━━┛              " + RED + "┗━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;
            case 2:
                System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                         ╭-----╮  ┃");
                System.out.println("┃  " + BLUE_BACKGROUND_BRIGHT + WHITE_BRIGHT + "┃   REGISTER   ┃" + BLUE_BRIGHT + "                       |  " + WHITE_BRIGHT + "F" + BLUE_BRIGHT + "  |  ┃");
                System.out.println("┃                                         ╰-----╯  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  1. Name:  %-32s┃  ┃\n", name);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  " + CYAN_BOLD_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.printf("┃  " + CYAN_BOLD_BRIGHT + "┃  2. Username:  %-28s┃" + BLUE_BRIGHT + "  ┃\n", username);
                System.out.println("┃  " + CYAN_BOLD_BRIGHT + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  3. Email:  %-31s┃  ┃\n", email);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  4. Password:  %-28s┃  ┃\n", password);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━┓              " + RED + "┏━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┃  5. SUBMIT   ┃              " + RED + "┃  6.  BACK    ┃" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┗━━━━━━━━━━━━━━┛              " + RED + "┗━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;
            case 3:
                System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                         ╭-----╮  ┃");
                System.out.println("┃  " + BLUE_BACKGROUND_BRIGHT + WHITE_BRIGHT + "┃   REGISTER   ┃" + BLUE_BRIGHT + "                       |  " + WHITE_BRIGHT + "F" + BLUE_BRIGHT + "  |  ┃");
                System.out.println("┃                                         ╰-----╯  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  1. Name:  %-32s┃  ┃\n", name);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  2. Username:  %-28s┃  ┃\n", username);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  " + CYAN_BOLD_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.printf("┃  " + CYAN_BOLD_BRIGHT + "┃  3. Email:  %-31s┃" + BLUE_BRIGHT + "  ┃\n", email);
                System.out.println("┃  " + CYAN_BOLD_BRIGHT + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  4. Password:  %-28s┃  ┃\n", password);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━┓              " + RED + "┏━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┃  5. SUBMIT   ┃              " + RED + "┃  6.  BACK    ┃" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┗━━━━━━━━━━━━━━┛              " + RED + "┗━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
                break;
            case 4:
                System.out.println(BLUE_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃                                         ╭-----╮  ┃");
                System.out.println("┃  " + BLUE_BACKGROUND_BRIGHT + WHITE_BRIGHT + "┃   REGISTER   ┃" + BLUE_BRIGHT + "                       |  " + WHITE_BRIGHT + "F" + BLUE_BRIGHT + "  |  ┃");
                System.out.println("┃                                         ╰-----╯  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  1. Name:  %-32s┃  ┃\n", name);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  2. Username:  %-28s┃  ┃\n", username);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
                System.out.printf("┃  ┃  3. Email:  %-31s┃  ┃\n", email);
                System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
                System.out.println("┃  " + CYAN_BOLD_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.printf("┃  " + CYAN_BOLD_BRIGHT + "┃  4. Password:  %-28s┃" + BLUE_BRIGHT + "  ┃\n", password);
                System.out.println("┃  " + CYAN_BOLD_BRIGHT + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃                                                  ┃");
                System.out.println("┃  ┏━━━━━━━━━━━━━━┓              " + RED + "┏━━━━━━━━━━━━━━┓" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┃  5. SUBMIT   ┃              " + RED + "┃  6.  BACK    ┃" + BLUE_BRIGHT + "  ┃");
                System.out.println("┃  ┗━━━━━━━━━━━━━━┛              " + RED + "┗━━━━━━━━━━━━━━┛" + BLUE_BRIGHT + "  ┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
        }


    }

}
/*
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┃                                                  ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print  ("┃  Enter your choice: ");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.print  ("┃  Enter username: ");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
* */
/*
        System.out.println("┏━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━┳━━━━━━━━━┓");
        System.out.println("┃  (   )  ┃  (   )  ┃  (    )  ┃  (   )  ┃  (   )  ┃");
        System.out.println("┣━━━━━━━━━┻━━━━━━━━━┛          ┗━━━━━━━━━┻━━━━━━━━━┫");
        System.out.println("┃  ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓  ┃");
        System.out.println("┃  ┃  ( ' _ ')   Hung                           ┃  ┃");
        System.out.println("┃  ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫  ┃");
        System.out.println("┃  ┃  ( ' _ ')   User                           ┃  ┃");
        System.out.println("┃  ┃  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━  ┃  ┃");
        System.out.println("┃  ┃  7 minutes ago                     Public  ┃  ┃");
        System.out.println("┃  ┃  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━  ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.println("┃  ┃    Hello                                   ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.println("┃  ┃                                            ┃  ┃");
        System.out.println("┃  ┃  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━  ┃  ┃");
        System.out.println("┃  ┃  Like: 3                       Comment: 5  ┃  ┃");
        System.out.println("┃  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛  ┃");
        System.out.println("┃  < 1. Previous|  ( 1. Choose )  |  1. Next   >   ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.print  ("┃  Enter your choice: ");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.print  ("┃  Enter username: ");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
* */
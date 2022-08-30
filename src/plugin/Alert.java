package plugin;

import static plugin.ConsoleColors.*;

public class Alert {

    public static void printCyanAlert(String alert) {
        System.out.println(CYAN_BOLD_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.printf("┃  %46s  ┃\n", alert);
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
    }
    public static void printRedAlert(String alert) {
        System.out.println(RED_BOLD_BRIGHT + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.printf("┃  %46s  ┃\n", alert);
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + RESET);
    }
}

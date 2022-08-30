package plugin;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    String header;

    List<String> choices = new ArrayList<>();

    int width;
    int maxWidth = 50;
    int leftPadding;
    boolean preset = false;

    public Menu() {
        this.preset = false;
    }

    public Menu(int width) {
        this.width = width;
        this.preset = true;
    }

    public int choiceCount() {
        return this.choices.size();
    }

    public void addHeader(String header) {
        this.header = header;
        setMaxWidth();
    }

    public void addChoice(String choice) {
        choices.add(choice);
        setMaxWidth();
    }

    public void addPaddingLeft(int padding) {
        this.leftPadding = padding;
    }

    public void print() {
        printUpLine();

        printHeader();

        printMiddleLine();

        printBody();


        printBottomLine();
    }

    private void printBody() {

        String paddingLeft = "";
        for (int i = 0; i < leftPadding; i++) {
            paddingLeft += " ";
        }
        int i = 1;
        for (String c : choices) {
            String temp = String.format("|" + paddingLeft + " %2d. %-" + (maxWidth - leftPadding - 5) + "s|", i++, c);
            System.out.println(temp);
        }

    }

    private void printHeader() {

        int space = (maxWidth - header.length()) / 2;
        String head = "";
        if (space == 0) {
            head = String.format("|" + header + "|");
        } else {
            head = String.format("|%" + space + "s" + "%-" + (maxWidth - space) + "s|", "", header);
        }
        System.out.println(head);
    }


    private void setMaxWidth() {
        int max = 50;
    }

    private void printUpLine() {
        System.out.print(".");
        for (int i = 0; i < maxWidth; i++) {
            System.out.print("-");
        }
        System.out.println(".");
    }

    private void printMiddleLine() {
        System.out.print("|");
        for (int i = 0; i < maxWidth; i++) {
            System.out.print("-");
        }
        System.out.println("|");
    }

    private void printBottomLine() {
        System.out.print("'");
        for (int i = 0; i < maxWidth; i++) {
            System.out.print("-");
        }
        System.out.println("'");
    }

    public int indexOf(String choice) {
        int i = 1;
        for (String c : choices) {
            if (c.equals(choice)) return i;
            i++;
        }
        return -1;
    }
}
package game;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // New window and add main menu
        JFrame frame = new JFrame("bocchi tower defense (btd)");
        frame.add(new Menu(frame));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
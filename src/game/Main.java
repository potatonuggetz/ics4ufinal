package game;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // New window and add main menu
        JFrame frame = new JFrame("csgo fragmovei");
        frame.add(new Menu(frame));
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
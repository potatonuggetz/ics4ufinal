package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel implements MouseListener, KeyListener, Runnable, MouseWheelListener, WindowListener {
    public static final int MENU_MAIN = 0;
    public static final int MENU_LEVEL_SELECT = 1;
    public static final int MENU_GAME = 2;
    public static final int MENU_CREDITS = 3;
    public static final int MENU_GAME_RESULTS = 4;
    public int currentMenu = 2; // temp

    GameEngine engine;
    long lastTime;

    public Menu(JFrame frame) {
        super();
        setPreferredSize(new Dimension(1280, 720));
        setBackground(new Color(0, 0, 0));
        this.setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);
        addMouseWheelListener(this);
        frame.addWindowListener(this);

        startGame(); // temp
        new Thread(this).start();
    }

    public void startGame() {
        engine = new GameEngine(new Level("test")); // temp
        lastTime = 0;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentMenu == MENU_MAIN) {
            drawMainMenu(g);
        } else if (currentMenu == MENU_LEVEL_SELECT) {
            drawLevelSelect(g);
        } else if (currentMenu == MENU_GAME) {
            drawGame(g);
        } else if (currentMenu == MENU_GAME_RESULTS) {
            drawGameResults(g);
        } else if (currentMenu == MENU_CREDITS){
            drawInstructions(g);
        } else {
            throw new RuntimeException("Invalid menu");
        }
    }

    public void drawMainMenu(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(255, 255, 255));
        g2d.drawRect(10, 10, 500, 500);
    }

    public void drawLevelSelect(Graphics g) {

    }

    public void drawGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        long currTime = System.currentTimeMillis();
        engine.update(currTime - lastTime);
        lastTime = currTime;
        engine.draw(this, g);
    }

    public void drawGameResults(Graphics g) {

    }

    public void drawInstructions(Graphics g) {

    }

    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {

    }

    public void windowClosed(WindowEvent e) {

    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {

    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowDeactivated(WindowEvent e) {

    }

    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {

            }
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (currentMenu == MENU_GAME) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                engine.startWave();
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}

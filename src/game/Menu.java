package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel implements MouseListener, MouseMotionListener, KeyListener, Runnable, MouseWheelListener, WindowListener {
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
        addMouseMotionListener(this);
        frame.addWindowListener(this);

        startGame(); // temp
        new Thread(this).start();
    }

    public void startGame() {
        engine = new GameEngine(new Level("levels/1.txt")); // temp
        lastTime = 0; // ૮•.•ა
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

    protected boolean drawButton(Graphics2D g, Image img, Image imghover, int posX, int posY, int width, int height) {
        Point mousePos = myGetMousePosition();
        boolean hoveredOver = mousePos.x > posX && mousePos.x < posX + width && mousePos.y > posY && mousePos.y < posY + height;
        if (hoveredOver && imghover != null) {
            g.drawImage(imghover, posX, posY, null);
        } else {
            g.drawImage(img, posX, posY, null);
        }
        return hoveredOver;
    }

    protected void drawCenteredString(Graphics g, String text, Rectangle rect, Font font, int offsetX, int offsetY) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x+offsetX, y+offsetY);
    }

    private Point myGetMousePosition() {
        Point p = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(p, this);
        return p;
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

    }

    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        if (currentMenu == MENU_GAME) {
            engine.mouseClicked(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (currentMenu == MENU_GAME) {
            engine.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (currentMenu == MENU_GAME) {
            engine.mouseReleased(e);
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

	public void mouseDragged(MouseEvent e) {
		if (currentMenu == MENU_GAME) {
            engine.mouseDragged(e);
        }
	}

	public void mouseMoved(MouseEvent e) {
		if (currentMenu == MENU_GAME) {
            engine.mouseMoved(e);
        }
	}
}

package game;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Menu extends JPanel implements MouseListener, KeyListener, Runnable, MouseWheelListener, WindowListener {
    public static final int MENU_MAIN = 0;
    public static final int MENU_LEVEL_SELECT = 1;
    public static final int MENU_GAME = 2;
    public static final int MENU_CREDITS = 3;
    public static final int MENU_GAME_RESULTS = 4;
    public static final int MENU_INSTRUCTIONS = 5;
    public int currentMenu = 0; // temp

    GameEngine engine;
    long lastTime;

    // images
    Map<String, Image> mmImages;

    public Menu(JFrame frame) {
        super();
        setPreferredSize(new Dimension(1280, 720));
        setBackground(new Color(0, 0, 0));
        this.setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);
        addMouseWheelListener(this);
        frame.addWindowListener(this);

        mmImages = new HashMap<>();

        // import images
        try {
            mmImages.put("bg", ImageIO.read(new File("img/bg/mainmenu.jpg")));
            mmImages.put("logo", ImageIO.read(new File("img/ui/main_menu_logo.png")));
            mmImages.put("play", ImageIO.read(new File("img/ui/main_menu_playbutton.png")));
            mmImages.put("playhover", ImageIO.read(new File("img/ui/main_menu_playbuttonhover.png")));
            mmImages.put("instr", ImageIO.read(new File("img/ui/main_menu_instructionsbutton.png")));
            mmImages.put("instrhover", ImageIO.read(new File("img/ui/main_menu_instructionsbuttonhover.png")));
            mmImages.put("about", ImageIO.read(new File("img/ui/main_menu_aboutbutton.png")));
            mmImages.put("abouthover", ImageIO.read(new File("img/ui/main_menu_aboutbuttonhover.png")));
        } catch (IOException e) {

        }

        //startGame(); // temp
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
        } else if (currentMenu == MENU_CREDITS) {
            drawAbout(g);
        } else if (currentMenu == MENU_INSTRUCTIONS) {
            drawInstructions(g);
        } else {
            throw new RuntimeException("Invalid menu");
        }
    }

    public void drawMainMenu(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(mmImages.get("bg"), 0, 0, null);
        g.drawImage(mmImages.get("logo"), 230, 25, null);
        drawButton(g2d, mmImages.get("play"), mmImages.get("playhover"), 361, 320, 559, 130, -14, -16);
        drawButton(g2d, mmImages.get("instr"), mmImages.get("instrhover"), 361, 435, 559, 130, -14, -16);
        drawButton(g2d, mmImages.get("about"), mmImages.get("abouthover"), 361, 550, 559, 130, -14, -16);
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

    public void drawAbout(Graphics g) {

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

    protected boolean drawButton(Graphics2D g, Image img, Image imghover, int posX, int posY, int width, int height, int paddingX, int paddingY) {
        Point mousePos = myGetMousePosition();
        boolean hoveredOver = mousePos.x > (posX - paddingX) && mousePos.x < (posX + width + paddingX) && mousePos.y > (posY - paddingY) && mousePos.y < (posY + height + paddingY);
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
        } else if (currentMenu == MENU_MAIN) {
            if (inRectangle(e, 375, 906, 336,  434)) currentMenu = MENU_LEVEL_SELECT;
            if (inRectangle(e, 375, 906, 451,  549)) currentMenu = MENU_INSTRUCTIONS;
            if (inRectangle(e, 375, 906, 566,  664)) currentMenu = MENU_CREDITS;
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

    private boolean inRectangle(MouseEvent e, int left, int right, int up, int down) {
        return e.getX() > left && e.getX() < right && e.getY() > up && e.getY() < down;
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}

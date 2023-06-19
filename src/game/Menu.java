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

    private int levelSelectPage = 1;

    // images
    Map<String, Image> mmImages;
    Map<String, Image> lsImages;

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
        lsImages = new HashMap<>();

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

            lsImages.put("bg", ImageIO.read(new File("img/bg/levelselect.jpg")));
            lsImages.put("logo", ImageIO.read(new File("img/ui/level_select_logo.png")));
            lsImages.put("leftmenu", ImageIO.read(new File("img/ui/level_select_leftbutton.png")));
            lsImages.put("rightmenu", ImageIO.read(new File("img/ui/level_select_rightbutton.png")));
            lsImages.put("leftmenuhover", ImageIO.read(new File("img/ui/level_select_leftbuttonhover.png")));
            lsImages.put("rightmenuhover", ImageIO.read(new File("img/ui/level_select_rightbuttonhover.png")));
            lsImages.put("backbutton", ImageIO.read(new File("img/ui/level_select_backbutton.png")));
            lsImages.put("backbuttonhover", ImageIO.read(new File("img/ui/level_select_backbuttonhover.png")));

            for (int i = 1; i <= 8; i++) {
                lsImages.put(Integer.toString(i), ImageIO.read(new File("img/ui/level_select_" + i + ".png")));
                lsImages.put(i + "h", ImageIO.read(new File("img/ui/level_select_" + i + "h.png")));
            }
            System.out.println(lsImages);
        } catch (IOException e) {

        }

        //startGame(); // temp
        new Thread(this).start();
    }

    public void startGame(int i) {
        currentMenu = MENU_GAME;
        engine = new GameEngine(new Level("levels/" + i + ".txt")); // temp
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
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(lsImages.get("bg"), 0, 0, null);
        g.drawImage(lsImages.get("logo"), 316, 30, null);

        drawButton(g2d, lsImages.get("backbutton"), lsImages.get("backbuttonhover"), 15, 15, 90, 90);
        if (levelSelectPage == 1) {
            drawButton(g2d, lsImages.get("rightmenu"), lsImages.get("rightmenuhover"), 849, 600, 160, 75);
            drawButton(g2d, lsImages.get("1"), lsImages.get("1h"), 145, 310, 453, 250);
            drawButton(g2d, lsImages.get("2"), lsImages.get("2h"), 700, 310, 453, 250);
        } else if (levelSelectPage == 2) {
            drawButton(g2d, lsImages.get("leftmenu"), lsImages.get("leftmenuhover"), 281, 600, 160, 75);
            drawButton(g2d, lsImages.get("rightmenu"), lsImages.get("rightmenuhover"), 849, 600, 160, 75);
            drawButton(g2d, lsImages.get("3"), lsImages.get("3h"), 145, 310, 453, 250);
            drawButton(g2d, lsImages.get("4"), lsImages.get("4h"), 700, 310, 453, 250);
        } else if (levelSelectPage == 3) {
            drawButton(g2d, lsImages.get("leftmenu"), lsImages.get("leftmenuhover"), 281, 600, 160, 75);
            drawButton(g2d, lsImages.get("rightmenu"), lsImages.get("rightmenuhover"), 849, 600, 160, 75);
            drawButton(g2d, lsImages.get("5"), lsImages.get("5h"), 145, 310, 453, 250);
            drawButton(g2d, lsImages.get("6"), lsImages.get("6h"), 700, 310, 453, 250);
        } else if (levelSelectPage == 4) {
            drawButton(g2d, lsImages.get("leftmenu"), lsImages.get("leftmenuhover"), 281, 600, 160, 75);
            drawButton(g2d, lsImages.get("7"), lsImages.get("7h"), 145, 310, 453, 250);
            drawButton(g2d, lsImages.get("8"), lsImages.get("8h"), 700, 310, 453, 250);
        }
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
            if (inRectangle(e, 375, 906, 336,  434)) {
                currentMenu = MENU_LEVEL_SELECT;
                levelSelectPage = 1;
            } else if (inRectangle(e, 375, 906, 451,  549)) {
                currentMenu = MENU_INSTRUCTIONS;
            } else if (inRectangle(e, 375, 906, 566,  664)) {
                currentMenu = MENU_CREDITS;
            }
        } else if (currentMenu == MENU_LEVEL_SELECT) {
            if (inRectangle(e, 15, 105, 15,  105)) {
                currentMenu = MENU_MAIN;
            } else if (levelSelectPage == 1) {
                if (inRectangle(e, 145, 598, 310, 560)) {
                    startGame(1);
                } else if (inRectangle(e, 700, 1153, 310, 560)) {
                    startGame(2);
                } else if (inRectangle(e, 849, 1009, 600, 675)) {
                    levelSelectPage = 2;
                }
            } else if (levelSelectPage == 2) {
                if (inRectangle(e, 145, 598, 310, 560)) {
                    startGame(3);
                } else if (inRectangle(e, 700, 1153, 310, 560)) {
                    startGame(4);
                } else if (inRectangle(e, 281, 441, 600, 675)) {
                    levelSelectPage = 1;
                } else if (inRectangle(e, 849, 1009, 600, 675)) {
                    levelSelectPage = 3;
                }
            } else if (levelSelectPage == 3) {
                if (inRectangle(e, 145, 598, 310, 560)) {
                    startGame(5);
                } else if (inRectangle(e, 700, 1153, 310, 560)) {
                    startGame(6);
                } else if (inRectangle(e, 281, 441, 600, 675)) {
                    levelSelectPage = 2;
                } else if (inRectangle(e, 849, 1009, 600, 675)) {
                    levelSelectPage = 4;
                }
            } else if (levelSelectPage == 4) {
                if (inRectangle(e, 145, 598, 310, 560)) {
                    startGame(7);
                } else if (inRectangle(e, 700, 1153, 310, 560)) {
                    startGame(8);
                } else if (inRectangle(e, 281, 441, 600, 675)) {
                    levelSelectPage = 3;
                }
            }
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

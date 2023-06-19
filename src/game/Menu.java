package game;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Menu extends JPanel implements MouseListener, MouseMotionListener, KeyListener, Runnable, MouseWheelListener, WindowListener {
    public static final int MENU_MAIN = 0;
    public static final int MENU_LEVEL_SELECT = 1;
    public static final int MENU_GAME = 2;
    public static final int MENU_CREDITS = 3;
    public static final int MENU_GAME_RESULTS = 4;
    public static final int MENU_INSTRUCTIONS = 5;
    public int currentMenu = 0; // temp

    GameEngine engine;
    long lastTime;
    Font bruhfont;

    private int levelSelectPage = 1;
    private int instructionsPage = 1;

    // images
    Map<String, Image> mmImages;
    Map<String, Image> lsImages;
    Map<String, Image> inImages;
    Map<String, Image> abImages;
    Map<String, Image> grImages;

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

        mmImages = new HashMap<>();
        lsImages = new HashMap<>();
        inImages = new HashMap<>();
        abImages = new HashMap<>();
        grImages = new HashMap<>();

        bruhfont = new Font("Calibri", Font.PLAIN, 35);

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

            inImages.put("bg", ImageIO.read(new File("img/bg/instructions.jpg")));
            inImages.put("logo", ImageIO.read(new File("img/ui/instructions_logo.png")));
            inImages.put("backbutton", ImageIO.read(new File("img/ui/instructions_backbutton.png")));
            inImages.put("backbuttonhover", ImageIO.read(new File("img/ui/instructions_backbuttonhover.png")));
            inImages.put("1", ImageIO.read(new File("img/ui/instructions_1.png")));
            inImages.put("2", ImageIO.read(new File("img/ui/instructions_2.png")));
            inImages.put("3", ImageIO.read(new File("img/ui/instructions_3.png")));
            inImages.put("ability", ImageIO.read(new File("img/ui/instructions_ability.png")));
            inImages.put("boccher", ImageIO.read(new File("img/ui/instructions_boccher.png")));
            inImages.put("drag", ImageIO.read(new File("img/ui/instructions_drag.png")));
            inImages.put("gold", ImageIO.read(new File("img/ui/instructions_gold.png")));
            inImages.put("health", ImageIO.read(new File("img/ui/instructions_health.png")));
            inImages.put("wave", ImageIO.read(new File("img/ui/instructions_wave.png")));
            inImages.put("xp", ImageIO.read(new File("img/ui/instructions_xp.png")));

            abImages.put("bg", ImageIO.read(new File("img/bg/about.jpg")));
            abImages.put("logo", ImageIO.read(new File("img/ui/about_logo.png")));
            abImages.put("backbutton", ImageIO.read(new File("img/ui/about_backbutton.png")));
            abImages.put("backbuttonhover", ImageIO.read(new File("img/ui/about_backbuttonhover.png")));

            grImages.put("failbg", ImageIO.read(new File("img/bg/fail.png")));
            grImages.put("passbg", ImageIO.read(new File("img/bg/pass.png")));
            grImages.put("continue", ImageIO.read(new File("img/ui/game_results_continue.png")));
            grImages.put("continuehover", ImageIO.read(new File("img/ui/game_results_continuehover.png")));
            grImages.put("failicon", ImageIO.read(new File("img/ui/game_results_fail.png")));
            grImages.put("winicon", ImageIO.read(new File("img/ui/game_results_win.png")));
       } catch (IOException e) {

        }

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
        if (engine.isDone()) {
            currentMenu = MENU_GAME_RESULTS;
        }
    }

    public void drawGameResults(Graphics g) {
        if (engine.health > 0) {
            g.drawImage(grImages.get("passbg"), 0, 0 ,null);
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(330, 50, 620, 620);
            g.drawImage(grImages.get("winicon"), 480 ,20, null);
            g.setColor(new Color(255,255,255));
            drawCenteredString(g, "Health Left: " + engine.health, new Rectangle(330, 300, 620, 50), engine.gamerfont, 0, 0);
        } else {
            g.drawImage(grImages.get("failbg"), 0, 0 ,null);
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(330, 50, 620, 620);
            g.drawImage(grImages.get("failicon"), 480 ,0, null);
            g.setColor(new Color(255,255,255));
            drawCenteredString(g, "Highest Wave: " + (engine.wave - 1), new Rectangle(330, 300, 620, 50), engine.gamerfont, 0, 0);
        }
        drawButton((Graphics2D) g, grImages.get("continue"), grImages.get("continuehover"), 490, 360, 300, 300);
    }

    public void drawInstructions(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(inImages.get("bg"), 0, 0, null);
        g.drawImage(inImages.get("logo"), 316, 30, null);
        drawButton(g2d, inImages.get("backbutton"), inImages.get("backbuttonhover"), 15, 15, 90, 90);
        g.setColor(new Color(0, 0, 0, 127));
        g.fillRect(200, 320, 880, 320);

        g.setColor(new Color(255,255,255));
        if (instructionsPage == 1) {
            drawCenteredString(g, "Bocchi Tower Defense is a tower defense game, where the", new Rectangle(200, 330, 880, 45), bruhfont, 0, 0);
            drawCenteredString(g, "objective is to stop enemies from reaching the endzone.", new Rectangle(200, 375, 880, 45), bruhfont, 0, 0);
            drawButton(g2d, lsImages.get("rightmenu"), lsImages.get("rightmenuhover"), 1100, 442, 160, 75);
            g.drawImage(inImages.get("boccher"), 552,466, null);
        } else if (instructionsPage == 2) {
            drawCenteredString(g, "Enemies come in waves. To start a wave, press the wave", new Rectangle(200, 330, 880, 45), bruhfont, 0, 0);
            drawCenteredString(g, "button. You win when you have reached the last wave", new Rectangle(200, 375, 880, 45), bruhfont, 0, 0);
            drawCenteredString(g, "without your health dropping to 0.", new Rectangle(200, 420, 880, 45), bruhfont, 0, 0);
            drawButton(g2d, lsImages.get("leftmenu"), lsImages.get("leftmenuhover"), 20, 442, 160, 75);
            drawButton(g2d, lsImages.get("rightmenu"), lsImages.get("rightmenuhover"), 1100, 442, 160, 75);
            g.drawImage(inImages.get("wave"), 370, 482, null);
            g.drawImage(inImages.get("health"), 740, 482, null);
        } else if (instructionsPage == 3) {
            drawCenteredString(g, "To stop enemies, you have to kill them using towers.", new Rectangle(200, 330, 880, 45), bruhfont, 0, 0);
            drawCenteredString(g, "Towers can be placed by dragging the icon from the", new Rectangle(200, 375, 880, 45), bruhfont, 0, 0);
            drawCenteredString(g, "bottom right into the playfield.", new Rectangle(200, 420, 880, 45), bruhfont, 0, 0);
            drawButton(g2d, lsImages.get("leftmenu"), lsImages.get("leftmenuhover"), 20, 442, 160, 75);
            drawButton(g2d, lsImages.get("rightmenu"), lsImages.get("rightmenuhover"), 1100, 442, 160, 75);
            g.drawImage(inImages.get("drag"), 445, 480, null);
        } else if (instructionsPage == 4) {
            drawCenteredString(g, "Towers cost gold, so make sure you", new Rectangle(200, 330, 880, 45), bruhfont, 0, 0);
            drawCenteredString(g, "have enough gold to place one.", new Rectangle(200, 375, 880, 45), bruhfont, 0, 0);
            drawButton(g2d, lsImages.get("leftmenu"), lsImages.get("leftmenuhover"), 20, 442, 160, 75);
            drawButton(g2d, lsImages.get("rightmenu"), lsImages.get("rightmenuhover"), 1100, 442, 160, 75);
            g.drawImage(inImages.get("gold"), 555, 460, null);
        } else if (instructionsPage == 5) {
            drawCenteredString(g, "Towers gain XP when they kill enemies,", new Rectangle(200, 330, 880, 45), bruhfont, 0, 0);
            drawCenteredString(g, "which allows them to level up and do more damage.", new Rectangle(200, 375, 880, 45), bruhfont, 0, 0);
            drawButton(g2d, lsImages.get("leftmenu"), lsImages.get("leftmenuhover"), 20, 442, 160, 75);
            drawButton(g2d, lsImages.get("rightmenu"), lsImages.get("rightmenuhover"), 1100, 442, 160, 75);
            g.drawImage(inImages.get("xp"), 492, 430, null);
        } else if (instructionsPage == 6) {
            drawCenteredString(g, "Towers also have abilities that can", new Rectangle(200, 330, 880, 45), bruhfont, 0, 0);
            drawCenteredString(g, "be activated by clicking on the tower.", new Rectangle(200, 375, 880, 45), bruhfont, 0, 0);
            drawButton(g2d, lsImages.get("leftmenu"), lsImages.get("leftmenuhover"), 20, 442, 160, 75);
            drawButton(g2d, lsImages.get("rightmenu"), lsImages.get("rightmenuhover"), 1100, 442, 160, 75);
            g.drawImage(inImages.get("ability"), 448, 434, null);
        } else if (instructionsPage == 7) {
            drawCenteredString(g, "Each tower has different abilities and attributes", new Rectangle(200, 330, 880, 45), bruhfont, 0, 0);
            drawCenteredString(g, "that counter different enemies. Be strategic!", new Rectangle(200, 375, 880, 45), bruhfont, 0, 0);
            drawButton(g2d, lsImages.get("leftmenu"), lsImages.get("leftmenuhover"), 20, 442, 160, 75);
            g.drawImage(inImages.get("3"), 550, 430, null);
            g.drawImage(inImages.get("1"), 314, 420, null);
            g.drawImage(inImages.get("2"), 800, 420, null);
        }
    }

    public void drawAbout(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(abImages.get("bg"), 0, 0, null);
        g.drawImage(abImages.get("logo"), 316, 30, null);

        drawButton(g2d, abImages.get("backbutton"), abImages.get("backbuttonhover"), 15, 15, 90, 90);

        g.setColor(new Color(0, 0, 0, 127));
        g.fillRect(50, 300, 1180, 370);

        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Calibri",1,32));
        g.drawString("Tower defense game made by Jayden and Brendan",75,400);
        g.drawString("Characters taken from League of Legends and Bocchi the Rock",75,500);
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
        if (currentMenu == MENU_GAME) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                engine.pause();
            }
        }
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
            if (engine.isGamePaused()) {
                if (inRectangle(e, 240, 540, 210 ,510)) engine.pause();
                else if (inRectangle(e, 740, 1040, 210 ,510)) {
                    currentMenu = MENU_LEVEL_SELECT;
                }
            } else {
                engine.mousePressed(e);
            }
        } else if (currentMenu == MENU_MAIN) {
            if (inRectangle(e, 375, 906, 336,  434)) {
                levelSelectPage = 1;
                currentMenu = MENU_LEVEL_SELECT;
            } else if (inRectangle(e, 375, 906, 451,  549)) {
                instructionsPage = 1;
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
        } else if (currentMenu == MENU_INSTRUCTIONS) {
            if (inRectangle(e, 15, 105, 15,  105)) {
                currentMenu = MENU_MAIN;
            } else if (instructionsPage == 1 && inRectangle(e, 1100, 1100+160, 442, 442+75)) {
                instructionsPage = 2;
            } else if (instructionsPage == 7 && inRectangle(e, 20, 180, 442, 442+75)) {
                instructionsPage = 6;
            } else if (instructionsPage <= 6 && instructionsPage >= 2 && inRectangle(e, 20, 180, 442, 442+75)) {
                instructionsPage--;
            } else if (instructionsPage <= 6 && instructionsPage >= 2 && inRectangle(e, 1100, 1100+160, 442, 442+75)) {
                instructionsPage++;
            }
        } else if (currentMenu == MENU_CREDITS) {
            if (inRectangle(e, 15, 105, 15,  105)) {
                currentMenu = MENU_MAIN;
            }
        } else if (currentMenu == MENU_GAME_RESULTS) {
            if (inRectangle(e, 490, 790, 360, 660)) {
                currentMenu = MENU_LEVEL_SELECT;
            }
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

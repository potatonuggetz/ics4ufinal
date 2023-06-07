import java.awt.*;
import javax.swing.*;

public class GameRenderer {

    GameEngine engine;

    public GameRenderer(GameEngine engine) {
        this.engine = engine;
    }

    public void draw(JPanel panel, Graphics g) {
        g.drawImage(engine.level.background, 0, 0, null);

        for (Enemy e : engine.shownEnemies) {
            g.drawImage(e.image, e.posX-10, e.posY-10, null);
        }
        for (Tower t : engine.placedTowers) {
            g.drawImage(t.image, t.posX, t.posY, null);
        }
    }
}

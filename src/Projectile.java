import java.awt.*;

public class Projectile {

    Tower tower;
    Line path;
    // auto
    private final boolean auto;
    Enemy target;

    // visual
    private int posX;
    private int posY;
    private final int projSizeX;
    private final int projSizeY;
    private final Image projectile;

    // movement
    private double absPosX;
    private double absPosY;

    public Projectile(Tower tower, Enemy target) {
        auto = target != null;
        this.tower = tower;
        this.target = target;

        posX = tower.posX;
        posY = tower.posY;
        absPosX = posX;
        absPosY = posY;

        projSizeX = tower.projSizeX;
        projSizeY = tower.projSizeY;
        projectile = tower.projectile;

        path = new Line(new Pair<>(tower.posX,tower.posY), new Pair<>(target.posX,target.posY));
    }

    public void move() {
        absPosX += (tower.projectileSpeed / GameEngine.getFPS()) * path.getDirection().first;
        absPosY += (tower.projectileSpeed / GameEngine.getFPS()) * path.getDirection().second;
        if (auto) path = new Line(new Pair<>(tower.posX,tower.posY), new Pair<>(target.posX,target.posY));

        posX = (int)Math.round(absPosX);
        posY = (int)Math.round(absPosY);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public double getAbsPosX() {
        return absPosX;
    }

    public double getAbsPosY() {
        return absPosY;
    }

    public int getProjSizeX() {
        return projSizeX;
    }

    public int getProjSizeY() {
        return projSizeY;
    }

    public Image getProjectile() {
        return projectile;
    }

    public boolean isAuto() {
        return auto;
    }
}

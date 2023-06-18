package game;
import java.awt.*;

public class Projectile {

    Tower tower;
    Line path;
    Ability ability;
    // auto
    private final boolean auto;
    Enemy target;

    // skillshot
    protected boolean magicDamage;
    protected double scalingAD;
    protected double scalingAP;
    protected int pierce;

    // visual
    protected int posX;
    protected int posY;
    protected final int projSizeX;
    protected final int projSizeY;
    protected final Image projectile;

    // movement
    private double absPosX;
    private double absPosY;

    //skillshots
    public Projectile(Tower tower, Ability ability, Pair<Integer,Integer> target) {
        auto = false;
        this.tower = tower;
        this.ability = ability;

        posX = tower.posX;
        posY = tower.posY;
        absPosX = posX;
        absPosY = posY;

        //create a new vector from tower to where the mouse was to indicate direction
        Line direction=new Line(new Pair<>(tower.posX,tower.posY),new Pair<>(target.first,target.second));
        //creates a new position which is located at the ability's range from the tower, using this direction
        Pair<Integer,Integer> destination=new Pair<>((int)Math.round(direction.getDirection().first*ability.range)+tower.posX,(int)Math.round(direction.getDirection().second*ability.range)+tower.posY);

        projSizeX = ability.projSizeX;
        projSizeY = ability.projSizeY;
        projectile = ability.projectile;

        path = new Line(new Pair<>(tower.posX,tower.posY), destination);
    }

    //auto attacks, they originate from tower
    public Projectile(Tower tower, Enemy target) {
        auto = true;
        this.tower = tower;
        this.target = target;

        posX = tower.posX;
        posY = tower.posY;
        absPosX = posX;
        absPosY = posY;

        projSizeX = tower.projSizeX;
        projSizeY = tower.projSizeY;
        projectile = tower.projectileAuto;

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

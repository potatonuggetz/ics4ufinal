public class Caitlyn extends Tower{
    public Caitlyn(int x,int y,GameEngine g){
        super(x,y,g);
        name="Caitlyn";
        description="The Sheriff of Piltover";
        attackDamage=new double[]{0,10,20,30,40,50};
        abilityPower=new double[]{0,0,0,0,0,0};
        attackSpeed=new double[]{0,0.6,0.7,0.9,1.2,1.5};
    }
}

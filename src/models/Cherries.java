package models;

public class Cherries extends Fruit {

    public Cherries(){
        super();
        image = this.image.getSubimage(32, 32 , 32, 32);
        symbol = "Che";
    }
}

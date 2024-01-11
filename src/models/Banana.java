package models;

public class Banana extends Fruit {


    public Banana(){
        super();
        image = this.image.getSubimage(64, 0 , 32, 32);
        symbol = "B";
    }
}

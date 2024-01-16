package models;

public class Strawberry extends Fruit {

    public Strawberry(){
        super();
        image = this.image.getSubimage(128, 224 , 32, 32);
        symbol = "S";
    }
}

package models;

public class Avocado extends Fruit {
    public Avocado(){
        super();
        image = this.image.getSubimage(32, 0 , 32, 32);
        symbol = "A";
    }
}

package models;

public class Orange extends Fruit {

    public Orange(){
        super();
        image = this.image.getSubimage(0, 0 , 32, 32);
        symbol = "O";
    }
}
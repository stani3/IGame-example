package models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public abstract class Fruit {
    public static String IMAGE_PATH= "src/FRUIT_LINE.png";
    protected BufferedImage image;
    public String symbol;

    public Fruit(){
        try {
            image = ImageIO.read(new File(IMAGE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fruit fruit)) return false;
        return Objects.equals(symbol, fruit.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
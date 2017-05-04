package me.ianhe.ioc;

/**
 * Created by iHelin on 17/5/3 20:11.
 */
public class Car {
    private String brand;
    private String color;
    private int speed;

    public void introduce() {
        System.out.println("brand:" + brand + ",color:" + color + ",speed" + speed);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

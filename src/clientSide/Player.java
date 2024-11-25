package clientSide;

import javax.swing.*;
import java.awt.*;

public class Player {
    private int points;
    private String name;
//    private ImageIcon avatar;

    public Player(int points, String name) {
        this.points = points;
        this.name = name;
    }

//    public Player(int points, String name, ImageIcon avatar) {
//        this.points = points;
//        this.name = name;
//        this.avatar = avatar;
//    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public ImageIcon getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(ImageIcon avatar) {
//        this.avatar = avatar;
//    }
}

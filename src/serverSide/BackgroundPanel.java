package serverSide;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel{
    private Image backgroundPicture;

    public BackgroundPanel(String path) {
        try {
            backgroundPicture = new ImageIcon(path).getImage();
        } catch (Exception e) {
            System.out.println("Could not find the picture you wanted" + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(backgroundPicture != null){
            g.drawImage(backgroundPicture, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

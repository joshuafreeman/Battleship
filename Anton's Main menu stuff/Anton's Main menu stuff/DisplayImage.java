import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import java.net.*;
import java.util.*;
import javafx.embed.swing.JFXPanel;
import javafx.util.Duration.*;
import java.time.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class DisplayImage{

    public static void main(String avg[]) throws IOException
    {
        DisplayImage abc=new DisplayImage();
        final JFXPanel fxPanel = new JFXPanel();
        File music = new File("mainmenu.wav");
        long time = 100000000;
        Duration.add(time);
        Media p = new Media(music.toURI().toString());      
        MediaPlayer mP = new MediaPlayer(p);
        mP.setStopTime();
        System.out.println(mP.getStopTime());
        mP.play();
    }

    public DisplayImage() throws IOException
    {
        BufferedImage img=ImageIO.read(new File("Title.png"));
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(1280,720);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
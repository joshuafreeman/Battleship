import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.embed.swing.JFXPanel;
/**
 * Write a description of class playMusic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class playMusic
{

    /**
     * Here is the main() method
     * 
     */
    public static void main(String[] args)
    {
        final JFXPanel fxPanel = new JFXPanel();
        File f = new File("mainmenu.mp3");
        Media m = new Media(f.toURI().toString());  
        MediaPlayer mP = new MediaPlayer(m);        
        mP.setCycleCount(10);
        mP.play();
    }
}

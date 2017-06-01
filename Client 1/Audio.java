import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.embed.swing.JFXPanel;
/**
 * Anton Gerasimov
 * 
 * Takes care of the music in the game
 * Mainmenu.mp3,battlemusic1 and 2 and 3 have been created by ArenaNet these songs do not belong 
 * us in anyway
 */
public class Audio
{
    /** Music Media Players*/
    private MediaPlayer battle1;
    private MediaPlayer battle2;
    private MediaPlayer battle3;
    private MediaPlayer won;
    private MediaPlayer loss;
    private MediaPlayer end;
    private MediaPlayer sunk;
    private MediaPlayer mP;
    private boolean playing;
    /**
     * Constructor for objects of class music, it reads in the music from the folder musica
     * and creates a new mediaplayer for each
     */
    public Audio()
    {
        final JFXPanel fxPanel = new JFXPanel();
        
        File f = new File("musica/mainmenu.mp3");
        Media m = new Media(f.toURI().toString());  
        mP = new MediaPlayer(m);  
        
        f = new File("musica/battlemusic2.mp3");
        m = new Media(f.toURI().toString());  
        battle1 = new MediaPlayer(m);
        
        f = new File("musica/battlemusic3.mp3");
        m = new Media(f.toURI().toString());  
        battle2 = new MediaPlayer(m);  
        
        f = new File("musica/battlemusic1.mp3");
        m = new Media(f.toURI().toString());  
        battle3 = new MediaPlayer(m);  
                
        f = new File("musica/thanksforplaying.mp3");
        m = new Media(f.toURI().toString());
        end = new MediaPlayer(m);
        
        f = new File("musica/won.mp3");
        m = new Media(f.toURI().toString());
        won = new MediaPlayer(m);
        
        f = new File("musica/loss.mp3");
        m = new Media(f.toURI().toString());
        loss = new MediaPlayer(m);
        
        f = new File("musica/sunk.mp3");
        m = new Media(f.toURI().toString());
        sunk = new MediaPlayer(m);
        }
    
    /**
     * This will play the main menu music 10 times before stopping
     */
    public void playMain()
    {
        mP.setCycleCount(10);
        mP.play();        
    }
    
    /**
     * This will stop the main menu music
     */
    public void stopMain()
    {
        mP.stop();        
    }    
    
    /**
     * This play the first song
     * then it will create a Runable that is checking when the song ends and then plays the
     * next battle song using the method
     */
    public void playBattle()
    {
        battle1.setVolume(.1);
        battle1.play();
        battle1.setOnEndOfMedia(new Runnable()
        {
            public void run()
            {
                //playing = false;
                playBattle2();
            }
        }); 
    }    
    
    /**
     * Same as playBattle but instead plays the second song then wait again to the end
     * then plays the next battle song using the method
     */
    private void playBattle2()
    {
        battle2.setVolume(.1);
        battle2.play();
        battle2.setOnEndOfMedia(new Runnable()
        {
            public void run()
            {
                //playing = false;
                playBattle3();
            }
        });
    }    
    
    /**
     * This will play the last battle song 10 times
     */
    private void playBattle3()
    {
        battle3.setVolume(.1);
        battle3.setCycleCount(10);
        battle3.play();
    }    
    
    /**
     * Stops all songs
     */
    public void stopBattle()
    {
        battle3.stop();  
    }    
    
    /**
     * Plays the audio clip that thanks at the end of the game
     */
    public void playThanks()
    {
        won.setOnEndOfMedia(new Runnable()
        {
            public void run()
            {
                won.play(); 
            }
        });   
    }      
    
    /**
     * Plays the win audio
     */
    public void playWon()
    {
        sunk.setOnEndOfMedia(new Runnable()
        {
            public void run()
            {
                won.play(); 
            }
        });    
    }   
    
    /**
     * Plays the losing audio
     */
    public void playLoss()
    {
        sunk.setOnEndOfMedia(new Runnable()
        {
            public void run()
            {
                loss.play(); 
            }
        });       
    }    
    
    public void playSunk()
    {
        sunk.play();   
        sunk.setOnEndOfMedia(new Runnable()
        {
            public void run()
            {
                File f = new File("musica/sunk.mp3");
                Media m = new Media(f.toURI().toString());
                sunk = new MediaPlayer(m);
            }
        });
    }    
    
    /**
     * you sink a battleship this clip will play
     */
    public void stopSunk()
    {
        sunk.stop();  
        sunk.setCycleCount(5);
    }    

}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public  class ImageComponent extends JComponent
{
    private static final long serialVerisonUID = 1L;
    private Image image;
    public ImageComponent()
    {
       try
       { 
           File image2 = new File("Title.png");
           image = ImageIO .read(image2);
        
       }       
       catch(IOException x)
       {
           x.printStackTrace();
       }    
    } 
    
    public void paintComp(Graphics g)
    {
        if(image == null)
            return;
        int imageWidth = image.getWidth(this);
        int imageHeight = image.getHeight(this);
        
        g.drawImage(image,50,50,this);
        for(int x = 0; x*imageWidth <= getWidth(); x++)
        {
            for(int y = 0; y*imageHeight <= getHeight(); y++)
            {
                if(x + y > 0)
                g.copyArea(0,0,imageWidth, imageHeight, x * imageWidth, y*imageHeight);
                
            }    
        }    
    }  
}    
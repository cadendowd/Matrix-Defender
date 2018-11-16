import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class AfterImage extends Trail
{
   private int imageNum = 0;
   
   public AfterImage(String img, int xPos, int yPos)
	{
		super(img, xPos, yPos);

      ArrayList<Image> images = new ArrayList<Image>();
      images.add(new ImageIcon("GameImages\\player3.PNG").getImage());
      images.add(new ImageIcon("GameImages\\player4.PNG").getImage());
      images.add(new ImageIcon("GameImages\\player5.PNG").getImage());
      images.add(new ImageIcon("GameImages\\player6.PNG").getImage());
      images.add(new ImageIcon("GameImages\\player7.PNG").getImage());    
      
      super.setImages(images);
   }
}
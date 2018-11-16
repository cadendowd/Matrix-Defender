import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Title extends Trail
{
   private boolean starting = true;
   
   public Title(String img, int xPos, int yPos, int W, int H)
	{
		super(img, xPos, yPos);
      super.setW(W);
      super.setH(H);

      ArrayList<Image> images = new ArrayList<Image>();
      images.add(new ImageIcon("GameImages\\black.PNG").getImage());
      images.add(new ImageIcon("GameImages\\black1.PNG").getImage());
      images.add(new ImageIcon("GameImages\\black2.PNG").getImage());
      images.add(new ImageIcon("GameImages\\black3.PNG").getImage());
      images.add(new ImageIcon("GameImages\\black4.PNG").getImage());
      images.add(new ImageIcon("GameImages\\black5.PNG").getImage());
      images.add(new ImageIcon("GameImages\\black6.PNG").getImage());
      images.add(new ImageIcon("GameImages\\black7.PNG").getImage());
      images.add(new ImageIcon("GameImages\\black8.PNG").getImage());
      images.add(new ImageIcon("GameImages\\black9.PNG").getImage());
      
      images.add(new ImageIcon("GameImages\\Intro.PNG").getImage());
      images.add(new ImageIcon("GameImages\\GameOver.PNG").getImage());
      
      images.add(new ImageIcon("GameImages\\TutorialPage.PNG").getImage());
      images.add(new ImageIcon("GameImages\\TutorialPage1.PNG").getImage());
      images.add(new ImageIcon("GameImages\\TutorialPage2.PNG").getImage());
      images.add(new ImageIcon("GameImages\\TutorialPage3.PNG").getImage());
      images.add(new ImageIcon("GameImages\\TutorialPage4.PNG").getImage());
      
      super.setImages(images);
   }
   
   public void decay()
   {
      if(starting)
      {
         super.decay();
      }
      else
      {
         if(super.getT()>0);
         {
            super.setImage(super.getArray().get(super.getT()/5));
         }
         super.setT(super.getT()-1);
      }
   }
   
   public void setStarting(boolean a)
   {
      starting = a;
   }
}
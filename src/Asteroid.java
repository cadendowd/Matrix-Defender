
import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Asteroid extends Enemy
{
   private ArrayList<Image> images;
   private int imageNum;
   
   public Asteroid(String img, int xPos, int yPos)
	{
		super(img, xPos, yPos);
      super.setVelMain((int)(Math.random()*5)+2);
      super.setAngle((int)(Math.random()*6.28));
      int rand = (int)(Math.random()*200)+200;
      super.setResizable(true);
      super.setW(rand);
      super.setH(rand);
      super.setR(rand/2-rand/10);
      super.setMidX(rand/2);
      super.setMidY(rand/2);
      super.setShape(false);
      super.setHealth(5);
      
      super.shrink(.8);
      
      images = new ArrayList<Image>();
      images.add(new ImageIcon("GameImages\\asteroid2.PNG").getImage());
      images.add(new ImageIcon("GameImages\\asteroid3.PNG").getImage());
      images.add(new ImageIcon("GameImages\\asteroid4.PNG").getImage());
      images.add(new ImageIcon("GameImages\\asteroid5.PNG").getImage());
      images.add(new ImageIcon("GameImages\\asteroid6.PNG").getImage());
   }
   public void damage(int a)
   {
      super.damage(a);
      if(imageNum<images.size()-1)
      {
         super.setImage(images.get(imageNum));
      }
      imageNum++;
   }
}
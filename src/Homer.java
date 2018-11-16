import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Homer extends Enemy
{
   private boolean locked;
   private ArrayList<Image> images;
   private ArrayList<Image> images2;
   private int imageNum = 0;
   
   public Homer(String img, int xPos, int yPos)
	{
		super(img, xPos, yPos);
      super.setVelMain(6);
      super.setHealth(4);
      super.setR(40);
      super.setShape(false);
      locked = false;
      super.setAngle(Math.random()*6.3);
      
      images = new ArrayList<Image>();
      images.add(new ImageIcon("GameImages\\triangle.PNG").getImage());
      images.add(new ImageIcon("GameImages\\triangle1.PNG").getImage());
      images.add(new ImageIcon("GameImages\\triangle2.PNG").getImage());
      images.add(new ImageIcon("GameImages\\triangle3.PNG").getImage());
      
      images2 = new ArrayList<Image>();
      images2.add(new ImageIcon("GameImages\\triangle r.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\triangle1 r.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\triangle2 r.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\triangle3 r.PNG").getImage());
	}
   public void action(Unit i)
   {
      if(super.distanceTo(i)<400)
      {
         locked = true;
      }
      if(locked)
      {
         super.setAngle(super.angleTo(i));
         super.setImage(images2.get(imageNum));
      }
      else  
      {
         super.setImage(images.get(imageNum));
      }
   }
   public void unlock()
   {
      locked = false;
      super.setImage(images.get(imageNum));
   }
   public void damage(int a)
   {
      super.damage(a);
      if(imageNum<images.size()-1)
      imageNum++;
   }
}
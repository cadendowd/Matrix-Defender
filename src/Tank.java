import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Tank extends Enemy
{
   private int timer = 0;
   private int redirectDelay = 150;
   private int turnTime = 30;
   private int timer2 = 0;
   private int weaponCD = 70;
   private double newAngle;
   private boolean clockwise = true;
   private boolean locked;
   private ArrayList<Image> images;
   private ArrayList<Image> images2;
   private int imageNum = 0;
   private boolean firstShot = false;

   public Tank(String img, int xPos, int yPos)
	{
		super(img, xPos, yPos);
      super.shrink(.5);
      super.setVelMain(5);
      super.setHealth(4);
      super.setShape(false);
      locked = false;
      super.setAngle(Math.random()*6.3);
      
      images = new ArrayList<Image>();
      images.add(new ImageIcon("GameImages\\hexagon.PNG").getImage());
      images.add(new ImageIcon("GameImages\\hexagon1.PNG").getImage());
      images.add(new ImageIcon("GameImages\\hexagon2.PNG").getImage());
      images.add(new ImageIcon("GameImages\\hexagon3.PNG").getImage());
      images.add(new ImageIcon("GameImages\\hexagonr.PNG").getImage());
      
      images2 = new ArrayList<Image>();
      images2.add(new ImageIcon("GameImages\\hexagon r.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\hexagon1 r.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\hexagon2 r.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\hexagon3 r.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\hexagonr r.PNG").getImage());
	}
   
   public void setTime(int a, int b)
   {
      timer = a;
      redirectDelay = b;
   }
   
   public void move()
   {
      if(timer >= redirectDelay)
      {
         super.setVelMain(0);
         if(timer<redirectDelay+turnTime)
         {
            if(clockwise)
            {
               super.setAngle(super.getAngle()+.1);
            }
            else
            super.setAngle(super.getAngle()-.1);
         }
         else
         {
            timer = 0;
            redirectDelay = (int)(Math.random()*150)+30;
            turnTime = (int)(Math.random()*20)+10;
            clockwise = !clockwise;
            super.setVelMain(5);
            super.setAngle(super.getAngle());
         }
      }
      timer++;
      super.move();
   }
   
   public void action(Unit i)
   {
      if(super.distanceTo(i)<600)
      {
         locked = true;
         if(!firstShot)
         {
            timer2 = 0;
            firstShot = true;
         }
      }
      else
      {
         locked = false;
      }
      if(locked)
      {
         super.setImage(images2.get(imageNum));
      }
      else  
      {
         super.setImage(images.get(imageNum));
      }
      timer2++;  
      if(timer2==weaponCD)
      {
         firstShot = false;
         timer2=0;
      }    
   }
   public void damage(int a)
   {
      super.damage(a);
      if(imageNum<images.size()-1)
      imageNum++;
   }
   
   public boolean fire()
   {
      if(timer2==0 && locked)
      return true;
      else
      return false;
   }
}

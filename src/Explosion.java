import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Explosion extends Unit
{
   private int time = 0;
   private ArrayList<Image> images;
   private ArrayList<Image> images2;
   private int pX;
   private int pY;
   private boolean b = true;
   private int decaySpeed = 3;
   
   public Explosion(String img, int xPos, int yPos, int r)
   {
      super(img, xPos, yPos);
      super.setH(2*r);
      super.setW(2*r);
      pX = xPos+r;
      pY = yPos+r;
      
      images = new ArrayList<Image>();
      images.add(new ImageIcon("GameImages\\explosionb.PNG").getImage());
      images.add(new ImageIcon("GameImages\\explosionb1.PNG").getImage());
      images.add(new ImageIcon("GameImages\\explosionb2.PNG").getImage());
      images.add(new ImageIcon("GameImages\\explosionb3.PNG").getImage());
      images.add(new ImageIcon("GameImages\\explosionb4.PNG").getImage());
      images.add(new ImageIcon("GameImages\\explosionb5.PNG").getImage());
      
      images2 = new ArrayList<Image>();
      images2.add(new ImageIcon("GameImages\\explosion.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\explosion1.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\explosion2.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\explosion3.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\explosion4.PNG").getImage());
      images2.add(new ImageIcon("GameImages\\explosion5.PNG").getImage());
   }
   
   public void setB(boolean a)
   {
      b = a;
   }
   public void decay()
   {
      time++;
      if(time<decaySpeed*images.size()-1)
      {        
         super.shrink(1.1);
      }
      else
      {         
         super.shrink(.8);
      }
      super.setPos(pX-super.getW()/2, pY-super.getH()/2);
      if(b)
      super.setImage(images.get(time/decaySpeed));
      else
      super.setImage(images2.get(time/decaySpeed));
   }
   
   public void setDecaySpeed(int a)
   {
      decaySpeed = a;
   }
   
   public void shiftCenter(int a, int b)
   {
      pX+=a;
      pY+=b;
   }
   
   public int getTime()
   {
      return time;
   }
   public int getMaxTime()
   {
      return images.size()*decaySpeed-1;
   }
}
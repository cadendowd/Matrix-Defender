import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Bar extends Unit
{
   private int max;
   private int amount;
   boolean vertical;
   private Unit frame;
   private int maxH;
   private int maxW;
   private int maxX;
   private int maxY;
   
   public Bar(String img, int xPos, int yPos, int w, int h, boolean v, int am)
	{
		super(img, xPos, yPos);
      maxH = h;
      maxW = w;
      maxX = xPos;
      maxY = yPos;
      super.setW(maxW);
      super.setH(maxH);
      vertical = v;
      frame = new Unit("GameImages\\bar frame1.PNG", xPos, yPos);
      frame.setH(h);
      frame.setW(w);
      max = am;
      amount = am;
   }
   public void setMax(int a)
   {
      max = a;
   }
   public void setAmount(int a)
   {
      amount = a;
   }
   public int getMax()
   {
      return max;
   }
   public int getAmount()
   {
      return amount;
   }
   public void setMaxH(int a)
   {
      maxH = a;
   }
   public void setMaxW(int a)
   {
      maxH = a;
   }
   public int getMaxH()
   {
      return maxH;
   }
   public int getMaxW()
   {
      return maxW;
   }
   public void setVertical(boolean a)
   {
      vertical = a;
   }
   public void inc(int a)
   {
      if((a>0 && amount<=max) || (a<0))
      {
         amount+=a;
         if(vertical)
         {
            super.setH( (int)( (double)(maxH)*((double)(amount)/(double)(max)) ) );
            super.setPos(super.getX(), maxY+maxH-super.getH());
            if(amount<0)
            {
               super.setPos(super.getX(), super.getY()+super.getH());
               super.setH(0);
               amount = 0;
            }
            if(amount>max)
            {
               super.setPos(super.getX(), super.getY()+super.getH()-maxH);
               super.setH(maxH);
               amount = max;
            }
         }  
         else
         {
            super.setW(super.getW()+a);
            super.setPos(super.getX()-a, super.getY());
            if(amount<0)
            {
               super.setW(0);
               amount = 0;
            }
            if(amount>max)
            {
               super.setPos(super.getX()-maxW, super.getY()+super.getH());
               super.setH(maxW);
               amount = max;
            }
         }
      }
   }
   public Image getFrame()
   {
      return frame.getImage();
   }
}
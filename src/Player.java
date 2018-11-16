
import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Player extends Unit
{
   private int timer;
   private int cap = 25;
   private int accel = 1;
   public Player(String img, int xPos, int yPos)
	{
		super(img, xPos, yPos);
      super.setShape(false);
      super.setR( (int)((super.getW()-(super.getW()/6))/2)-15 );
      setCap(12, 1);
	}
   
   public void setCap(int a, int b)
   {
      cap = a;
      accel = b;
   }
   
   public void move()
   {
      if(super.getVelX()>=0)
      {
         super.incVelX(-accel);
      }
      if(super.getVelY()>=0)
      {
         super.incVelY(-accel);
      }
      if(super.getVelX()<0)
      {
         super.incVelX(accel);
      }
      if(super.getVelY()<0)
      {
         super.incVelY(accel);
      }
      super.setPos(super.getX()+super.getVelX(),super.getY()+super.getVelY());
      if(timer<=7)
      {
         timer++;
      }
      else
      timer = 0;
   }
   public void control(boolean[] keys)
   {  
      if(keys[KeyEvent.VK_A])
      {
         if(super.getVelX()>-cap)
         super.incVelX(-accel*2);
         if(super.getVelX()<-cap)
         super.setVel(-cap, super.getVelY());
      } 
      if(keys[KeyEvent.VK_D])
      {
         if(super.getVelX()<cap)
         super.incVelX(accel*2);
         if(super.getVelX()>cap)
         super.setVel(cap, super.getVelY());
      }
      if(keys[KeyEvent.VK_W])
      {
         if(super.getVelY()>-cap)
         super.incVelY(-accel*2);
         if(super.getVelY()<-cap)
         super.setVel(super.getVelX(), -cap);
      }
      if(keys[KeyEvent.VK_S])
      {
         if(super.getVelY()<cap)
         super.incVelY(accel*2);
         if(super.getVelY()>cap)
         super.setVel(super.getVelX(), cap);
      }
   }
   public int getTime()
   {
      return timer;
   }
   public void setTimer(int a)
   {
      timer = a;
   }
}
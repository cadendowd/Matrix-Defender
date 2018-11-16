
import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Enemy extends Unit
{
   private int hp;
   private int maxHp;
   
   public Enemy(String img, int xPos, int yPos)
	{
		super(img, xPos, yPos);
      super.shrink(.75);
	}
   public int getHealth()
   {
      return hp;
   }
   public int getMaxHp()
   {
      return maxHp;
   }
   public void damage(int a)
   {
      hp-=a;
   }
   public void setHealth(int a)
   {
      hp = a;
      maxHp = a;
   }
   public void setAngle(double a)
   {
      super.setAngle(a);
      super.setVel((int)(super.getVel()*Math.cos(super.getAngle()-Math.PI/2)),
         (int)(super.getVel()*Math.sin(super.getAngle()-Math.PI/2)) );
   }
   public void action(Unit a)
   {
   } 
   public void unlock()
   {
   }
}
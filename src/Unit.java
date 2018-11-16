import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Rectangle;

class Unit extends JComponent
{
   private Image image;
   private int x;
   private int y;
   private double angle = 0;
   private int midX;
   private int midY;
   private int velX;
   private int velY;
   private int vel;
   private int r;
   private int h;
   private int w;
   private boolean isRectangle = true;
   private static int ids=0;
   private int id;
   private boolean resizable = false;
   
   public Unit(Image img, int xPos, int yPos)
   {
      image = img;
      x = xPos;
      y = yPos;
      w = image.getWidth(null);
      h = image.getHeight(null);
      midX = image.getWidth(null)/2;
      midY = image.getHeight(null)/2;
      ids++;
      id=ids;
      if(midX<=midY)
      {
         r = image.getHeight(null)/2;
      }
      else
      {
         r = image.getWidth(null)/2;
      }
   }
   public Unit(String path, int xPos, int yPos)
   {
      this(new ImageIcon(path).getImage(), xPos, yPos);	
   }
   public Unit(String path, int xPos, int yPos, int width, int height)
   {
      this(new ImageIcon(path).getImage(), xPos, yPos);	
      setW(width);
      setH(height);
   }
   public int getID()
   {
      return id;
   }
   //Shape methods
   public boolean getResizable()
   {
      return resizable;
   }
   public void setResizable(boolean a)
   {
      resizable = a;
   }
   public void setShape(boolean a)
   {
      isRectangle = a;
   }
   public boolean isRect()
   {
      return isRectangle;
   }
   public int getR()
   {
      return r;
   }
   public void setR(int a)
   {
      r = a;
   }
   public int getX()
   {
      return x;
   }  
   public int getY()
   {
      return y;
   }  
   public int getCenterX()
   {
      return midX+getX();
   }
   public int getCenterY()
   {
      return midY+getY();
   }
   public void setMidX(int a)
   {
      midX = a;
   }
   public void setMidY(int a)
   {
      midY = a;
   }
   public int getW()
   {
      return w;
   }  
   public int getH()
   {
      return h;
   }
   public void setW(int a)
   {
      w = a;
   }  
   public void setH(int a)
   {
      h = a;
   } 
   //Velocity methods
   public int getVel()
   {
      return vel;
   }
   public void setVelMain(int a)
   {
      vel = a;
   }
   public int getVelX()
   {
      return velX;  
   }
   public int getVelY()
   {
      return velY;
   }
   public void incVelX(int a)
   {
      velX+=a;
   }
   public void incVelY(int a)
   {
      velY+=a;
   }
   public void setVel(int a, int b)
   {
      velX = a;
      velY = b;
   }

   public void setPos(int xPos, int yPos)
   {
      x = xPos;
      y = yPos;
   }
   public Image getImage()
   {
      return image;
   }
   public void setImage(Image a)
   {
      image = a;
   }
   public double getAngle()
   {
      return angle;
   }
   public void setAngle(double a)
   {
      angle = a;
   }
   public void move()
   {
      setPos(x+velX,y+velY);
   }
   public boolean collide(Unit t)
   {
      if(this.isRect() == t.isRect() && this.isRect())
      {
         Rectangle a = new Rectangle(x,y,getW(),getH());
         Rectangle b = new Rectangle(t.getX(),t.getY(),t.getH(),t.getW());
         return a.intersects(b);
      }
      else if(this.isRect() == t.isRect() && !this.isRect())
      {
         int xComp = Math.abs(this.getCenterX()-t.getCenterX());
         int yComp = Math.abs(this.getCenterY()-t.getCenterY());
         int d = (int)(Math.sqrt(Math.pow(xComp,2)+Math.pow(yComp,2)));
         return (d <= this.getR()+t.getR());
      }
      else 
         return false;
   }
   
   public int distanceTo(Unit t)
   {
      int xComp = Math.abs(this.getCenterX()-t.getCenterX());
      int yComp = Math.abs(this.getCenterY()-t.getCenterY());
      int d = (int)(Math.sqrt(Math.pow(xComp,2)+Math.pow(yComp,2)));
      return d;
   }
   
   public double angleTo(Unit a)
   {
      return ( -Math.atan2(a.getCenterX()-this.getCenterX(), a.getCenterY()-this.getCenterY())+ Math.PI );
   }
   
   public void shrink(double f)
   {
      setH((int)(getH()*f));
      setW((int)(getW()*f));
      setR((int)(getR()*f));
      setMidX((int)(getW()/2));
      setMidY((int)(getH()/2));
      setResizable(true);
   }
}
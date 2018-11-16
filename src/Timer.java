import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Timer
{
   private ArrayList<Unit> numbers = new ArrayList<Unit>();
   private ArrayList<Unit> numbers1 = new ArrayList<Unit>();
   private ArrayList<Unit> numbers2 = new ArrayList<Unit>();
   private ArrayList<Unit> numbers3 = new ArrayList<Unit>();
   private ArrayList<Integer> clock;
   private ArrayList<Image> digits;
   private int type = 0;
   
   private int time;  
   private int x;
   private int y;
   private int w;
   private int h;
   
   public Timer(int xPos, int yPos, int W, int H)
   {
      x = xPos;
      y = yPos;
     
      clock = new ArrayList<Integer>();
      digits = new ArrayList<Image>();
      
      w = W;
      h = H;
      numbers1.add(new Unit("GameImages\\0.PNG", x, y, w, h));
      numbers1.add(new Unit("GameImages\\1.PNG", x, y, w, h));
      numbers1.add(new Unit("GameImages\\2.PNG", x, y, w, h));
      numbers1.add(new Unit("GameImages\\3.PNG", x, y, w, h));
      numbers1.add(new Unit("GameImages\\4.PNG", x, y, w, h));
      numbers1.add(new Unit("GameImages\\5.PNG", x, y, w, h));
      numbers1.add(new Unit("GameImages\\6.PNG", x, y, w, h));
      numbers1.add(new Unit("GameImages\\7.PNG", x, y, w, h));
      numbers1.add(new Unit("GameImages\\8.PNG", x, y, w, h));
      numbers1.add(new Unit("GameImages\\9.PNG", x, y, w, h));
      
      numbers2.add(new Unit("GameImages\\0g.PNG", x, y, w, h));
      numbers2.add(new Unit("GameImages\\1g.PNG", x, y, w, h));
      numbers2.add(new Unit("GameImages\\2g.PNG", x, y, w, h));
      numbers2.add(new Unit("GameImages\\3g.PNG", x, y, w, h));
      numbers2.add(new Unit("GameImages\\4g.PNG", x, y, w, h));
      numbers2.add(new Unit("GameImages\\5g.PNG", x, y, w, h));
      numbers2.add(new Unit("GameImages\\6g.PNG", x, y, w, h));
      numbers2.add(new Unit("GameImages\\7g.PNG", x, y, w, h));
      numbers2.add(new Unit("GameImages\\8g.PNG", x, y, w, h));
      numbers2.add(new Unit("GameImages\\9g.PNG", x, y, w, h));
      
      numbers3.add(new Unit("GameImages\\0w.PNG", x, y, w, h));
      numbers3.add(new Unit("GameImages\\1w.PNG", x, y, w, h));
      numbers3.add(new Unit("GameImages\\2w.PNG", x, y, w, h));
      numbers3.add(new Unit("GameImages\\3w.PNG", x, y, w, h));
      numbers3.add(new Unit("GameImages\\4w.PNG", x, y, w, h));
      numbers3.add(new Unit("GameImages\\5w.PNG", x, y, w, h));
      numbers3.add(new Unit("GameImages\\6w.PNG", x, y, w, h));
      numbers3.add(new Unit("GameImages\\7w.PNG", x, y, w, h));
      numbers3.add(new Unit("GameImages\\8w.PNG", x, y, w, h));
      numbers3.add(new Unit("GameImages\\9w.PNG", x, y, w, h));
      
      numbers = numbers1;
      for(int i = 0; i<6; i++)
      {
         clock.add(0);
         digits.add(numbers.get(0).getImage());
      }
   }  
   public int getX()
   {
      return x;
   }  
   public void setType(int a)
   {
      type = a;
      if(type==0)
      numbers = numbers1;
      else if(type==1)
      numbers = numbers2;
      else if(type==2)
      numbers = numbers3;
      
      setDigits();
   }
   public int getY()
   {
      return y;
   }  
   public int getW()
   {
      return w;
   }
   public int getH()
   {
      return h;
   }
   public int getTime()
   {
      return time;
   }
   public void setPos(int xPos, int yPos)
   {
      x = xPos;
      y = yPos;
   }    
   public void setW(int a)
   {
      w = a;
   }
   public void setH(int a)
   {
      h = a;
   }
   public void setDigits()
   {
      for(int i = 0; i<6; i++)
      {
         digits.set(i, numbers.get(clock.get(i)).getImage());
      }
   } 
   public void addTime()
   {
      time++;
      clock.set(5, clock.get(5)+1);
      
      for(int i = 5; i>0; i--)
      {
         if(clock.get(i) == 10)
         {
            clock.set(i-1, clock.get(i-1)+1);
            clock.set(i, 0);
         }
      }
   }   
   public void setTime(int a)
   {
      if(a<time)
      {
         reset();
         for(int i = 0; i<a; i++)
         {
            addTime();
         } 
      }
      else
      {
         for(int i = time; i<a; i++)
         {
            addTime();
         }
      }
   }  
   public void reset()
   {
      time = 0;
      for(int i = 0; i<6; i++)
      {
         clock.set(i, 0);
         digits.set(i, numbers.get(0).getImage());
      }
   }  
   public Image getImage(int digit)
   {
      return digits.get(digit);
   }
}
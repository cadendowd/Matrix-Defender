import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Trail extends Unit
{
   private ArrayList<Image> images;
   private int imageNum = 0;
   
   public Trail(String img, int xPos, int yPos)
	{
		super(img, xPos, yPos);

      images = new ArrayList<Image>();
      images.add(new ImageIcon("GameImages\\trail.PNG").getImage());
      images.add(new ImageIcon("GameImages\\trail1.PNG").getImage());
      images.add(new ImageIcon("GameImages\\trail2.PNG").getImage());
      images.add(new ImageIcon("GameImages\\trail3.PNG").getImage());
      images.add(new ImageIcon("GameImages\\trail4.PNG").getImage());
      images.add(new ImageIcon("GameImages\\trail5.PNG").getImage());
   }
   
   public void decay()
   {
      if((imageNum/5)<images.size()-1);
      {
         super.setImage(images.get(imageNum/5));
      }
      imageNum++;
   }
   public void setImages(ArrayList<Image> a)
   {
      images = a;
   }
   public int getT()
   {
      return imageNum;
   }
   public void setT(int a)
   {
      imageNum = a;
   }
   
   public void setImage(int a)
   {
      imageNum = a;
      super.setImage(images.get(a));
   }
   public int getImageNum()
   {
      int a = 0;
      for(int i = 0; i<images.size(); i++)
      {
         if(images.get(i).equals(super.getImage()))
         {
            a = i;
         }
      }
      return a;
   }
   public ArrayList<Image> getArray()
   {
      return images;
   }
}

import java.awt.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Projectile extends Unit
{
   public Projectile(String img, int xPos, int yPos)
	{
		super(img, xPos, yPos);
      super.setR(5);
      super.setShape(false);
      super.shrink(.7);
   }
}
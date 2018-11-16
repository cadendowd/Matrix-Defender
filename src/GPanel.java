import java.awt.*;
import javax.swing.*;
import java.util.*;
import javafx.scene.shape.*;

class GPanel extends JPanel 
{ 
   private ArrayList<Unit> units;
   private Image background;
   private Player player;
   private Unit bg;
   private Unit bg2;
   private boolean slow = false;
   private Bar energy;
   private Bar health;
   private Unit circle;
   private ArrayList<Trail> trail;
   private ArrayList<AfterImage> afterImages;
   private Timer scorekeep;
   private ArrayList<Explosion> explosions;
   private Unit paused;
   private Timer hsCount;
   private Title intro;
   private Unit scores;
      
	public GPanel(String path) 
  	{
  		this(new ImageIcon(path).getImage());
      units = new ArrayList<Unit>();
      trail = new ArrayList<Trail>();
      player = new Player("GameImages\\player.PNG", 900, 500);
      bg = new Unit("GameImages\\bg.PNG", 0, 0);
      bg2 = new Unit("GameImages\\bg3 original.PNG", 0, 0);
      energy = new Bar("GameImages\\blue bar.PNG", 1550, 50, 200, 900, true, 900);
      health = new Bar("GameImages\\blue bar.PNG", 1550, 50, 200, 900, true, 900);
      circle = new Unit("GameImages\\circle.PNG", 0, 0);
      afterImages = new ArrayList<AfterImage>();
      scorekeep = new Timer(10, 10, 10, 10);
      explosions = new ArrayList<Explosion>();
      paused = new Unit("GameImages\\pause.PNG", -20000, -20000);
      hsCount = new Timer(0,0,0,0);
      intro = new Title("GameImages\\black.PNG", 0, 0, 0, 0);
      scores = new Unit("GameImages\\scores.PNG", 0,0,0,0);
  	}   
   
  	public GPanel(Image b) //sets panel size
  	{
    	background = b;
    	Dimension size = new Dimension(b.getWidth(null), b.getHeight(null));	
     	setPreferredSize(size);
    	setMinimumSize(size);
    	setMaximumSize(size);
    	setSize(size);
  	}

  	public void paintComponent(Graphics g) //makes all the objects visible
  	{	
    	g.drawImage(background, 0, 0, null); 
                           
      Graphics2D g2d = (Graphics2D)g.create();
      g2d.drawImage(bg.getImage(), (int)(bg.getX()), (int)(bg.getY()), 
         (int)(bg2.getW()/2)+1050, (int)(bg2.getH()/2)+1000, null);
      
      if(!slow)
      {
      g2d.drawImage(bg2.getImage(), (int)(bg2.getX()), (int)(bg2.getY()), null);
      }
      
      for(int i=0; i<explosions.size(); i++)
      {
         g2d.drawImage(explosions.get(i).getImage(), (int)(explosions.get(i).getX()), (int)(explosions.get(i).getY()), 
            explosions.get(i).getW(), explosions.get(i).getH(), null);
      }
      
      try{
         for(int i=0; i<units.size(); i++)
         {
            Graphics2D graphics2D = (Graphics2D)g.create();
            graphics2D.rotate(units.get(i).getAngle(), units.get(i).getCenterX(), units.get(i).getCenterY());
            
            if(units.get(i).getResizable())
            { 
            graphics2D.drawImage(units.get(i).getImage(), (int)(units.get(i).getX()), (int)(units.get(i).getY()), 
               units.get(i).getW(), units.get(i).getH(), null);
            }
            else{
            graphics2D.drawImage(units.get(i).getImage(), (int)(units.get(i).getX()), (int)(units.get(i).getY()), null);
            }
         } 
      } catch(IndexOutOfBoundsException e) {}
      
      if(slow)
      {
         g2d.drawImage(bg2.getImage(), (int)(bg2.getX()), (int)(bg2.getY()), null);
      }
      try {
         for(int i=0; i<trail.size(); i++)
         {
            Graphics2D graphics2D = (Graphics2D)g.create();
            graphics2D.rotate(trail.get(i).getAngle(), trail.get(i).getCenterX(), trail.get(i).getCenterY());
            graphics2D.drawImage(trail.get(i).getImage(), (int)(trail.get(i).getX()), (int)(trail.get(i).getY()),
               trail.get(i).getW(), trail.get(i).getH(), null);
         }
      } catch(IndexOutOfBoundsException e) {}
      
      for(int i=0; i<afterImages.size(); i++)
      {
         Graphics2D afterImg = (Graphics2D)g.create();
         afterImg.rotate(afterImages.get(i).getAngle(), afterImages.get(i).getCenterX(), afterImages.get(i).getCenterY());
         afterImg.drawImage(afterImages.get(i).getImage(), (int)(afterImages.get(i).getX()), (int)(afterImages.get(i).getY()),
            afterImages.get(i).getW(), afterImages.get(i).getH(), null);
      }
      
      if(intro.getX()<-500 && intro.getY()<-500)
      {
         Graphics2D playerImg = (Graphics2D)g.create();
         playerImg.rotate(player.getAngle(), player.getCenterX(), player.getCenterY());
         playerImg.drawImage(player.getImage(), (int)(player.getX()), (int)(player.getY()), 
            player.getW(), player.getH(), null);
      }
      
      g2d.drawImage(energy.getImage(), (int)(energy.getX()), (int)(energy.getY()), 
         energy.getW(), energy.getH(), null);

      g2d.drawImage(health.getImage(), (int)(health.getX()), (int)(health.getY()), 
         health.getW(), health.getH(), null);
         
      g2d.drawImage(circle.getImage(), (int)(circle.getX()), (int)(circle.getY()), 
         circle.getW(), circle.getH(), null);   
      
      if(intro.getX()<-500 || intro.getX()==-11)
      { 
         for(int i = 0; i<6; i++)
         {
            g2d.drawImage(scorekeep.getImage(i), scorekeep.getX()+i*(scorekeep.getW()+10), scorekeep.getY(), 
               scorekeep.getW(), scorekeep.getH(), null);
         }
         for(int i = 0; i<6; i++)
         {
            g2d.drawImage(hsCount.getImage(i), hsCount.getX()+i*(hsCount.getW()+10), hsCount.getY(), 
               hsCount.getW(), hsCount.getH(), null);
         }
         g2d.drawImage(scores.getImage(), (int)(scores.getX()), (int)(scores.getY()), 
            scores.getW(), scores.getH(), null);
      }
      g2d.drawImage(paused.getImage(), (int)(paused.getX()), (int)(paused.getY()), 
         paused.getW(), paused.getH(), null);
         
      g2d.drawImage(intro.getImage(), (int)(intro.getX()), (int)(intro.getY()), 
         intro.getW(), intro.getH(), null);
      
      if(intro.getX()>-11 && intro.getY()>-11)
      {
         Graphics2D playerImg = (Graphics2D)g.create();
         playerImg.rotate(player.getAngle(), player.getCenterX(), player.getCenterY());
         playerImg.drawImage(player.getImage(), (int)(player.getX()), (int)(player.getY()), 
            player.getW(), player.getH(), null);
      }
      if(intro.getX()==-12)
      { 
         for(int i = 0; i<6; i++)
         {
            g2d.drawImage(scorekeep.getImage(i), scorekeep.getX()+i*(scorekeep.getW()+10), scorekeep.getY(), 
               scorekeep.getW(), scorekeep.getH(), null);
         }
         for(int i = 0; i<6; i++)
         {
            g2d.drawImage(hsCount.getImage(i), hsCount.getX()+i*(hsCount.getW()+10), hsCount.getY(), 
               hsCount.getW(), hsCount.getH(), null);
         }
         g2d.drawImage(scores.getImage(), (int)(scores.getX()), (int)(scores.getY()), 
            scores.getW(), scores.getH(), null);
      }
   }
  	
   //Updates all the objects
  	public void update(Unit newBg, ArrayList<Unit> newUnits, Player newPlayer, Unit newBg2, boolean nSlow,
      Bar newEnergy, Bar newHp, Unit newCircle, ArrayList<Trail> newTrail, ArrayList<AfterImage> newAfters,
      Timer newScorekeep, ArrayList<Explosion> newExplosions, Unit newPaused, Timer newHsCount, Title newIntro, Unit newScore)
  	{ 
      bg = newBg;
      bg2 = newBg2;
      units = newUnits;
      player = newPlayer;
      slow = nSlow;
      energy = newEnergy;
      health = newHp;
      circle = newCircle;
      afterImages = newAfters;
      trail = newTrail;
      scorekeep = newScorekeep;
      explosions = newExplosions;
      paused = newPaused;
      hsCount = newHsCount;
      intro = newIntro;
      scores = newScore;
  		repaint();  
  	}
}

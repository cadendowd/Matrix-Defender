//Matrix Defender by Gia-Phong and Caden (G@C)

import java.awt.*;
import static java.lang.Math.*;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.scene.shape.*;
import java.io.FileReader;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import javax.sound.sampled.*;

public class MatrixDefender extends JComponent implements KeyListener, MouseListener
{
   //Game Settings
   private int fireRate = 7;
   private int gameSpeed = 12;
   private int laserSpeed = 30;
   private int playerSpeed = 12;
   private int playerAcceleration = 1;
   private int screenWidth = 1800;
   private int screenHeight = 900;
   private double playerSize = .4;
   private int afterImageDelay = 4;
   private int maxHP = 1000;
   private int incHP = 5;
   private int decHP = 100;
   private int maxEnergy = 1500;
   private int energyDepletionRate = 2;
   private int energyIncRate = 1;
   private int enemyProjSpeed = 8;
   private double musicVolume = -10;
   private int enemiesCap = 300;
   private int maxTanks = 100;
   
   //Other Instance Variables
   public boolean[] key = new boolean[256];
   public boolean clicked;
   private JFrame frame;
   private ArrayList<Unit> units;
   private Player player;
   private GPanel gamePanel;
   private Point point = new Point();
   private Unit bg;
   private Unit bg2;
   private ArrayList<Enemy> enemies;
   private ArrayList<Projectile> projectiles;
   private int count = 0;
   private int random = 150;
   private int count2 = 0;
   private boolean slow = false;
   private Bar energy;
   private Bar health; 
   private Unit circle;
   private ArrayList<Trail> trail;
   private ArrayList<AfterImage> afterImages;
   private Timer scorekeep;
   private int clock = 0;
   private ArrayList<Tank> tanks;
   private ArrayList<Projectile> hostileProj;
   private ArrayList<Explosion> explosions;
   private int highscore;
   private Unit paused;
   private boolean pause = false;
   private Timer hsCount;
   private Title intro;
   private boolean helpScreen = false;
   private ArrayList<Clip> sounds;
   private int screenNum = 0;
   private Unit scores;
   
   //Interface: MouseEvent
   public void mouseClicked(MouseEvent event) {}
   public void mousePressed(MouseEvent event){
   if(!clicked)
   {
      player.setTimer(6);
   }
   clicked = true;
   }
   public void mouseReleased(MouseEvent event){
      clicked = false;
   }
   public void mouseEntered(MouseEvent event){}
   public void mouseExited(MouseEvent event){}   
   
   //Interface: KeyEvent
   public void keyPressed(KeyEvent e)
   {
      key[e.getKeyCode()] = true;
      if(key[KeyEvent.VK_H] && intro.getX()>-500)
      {
         helpScreen = !helpScreen;
         if(helpScreen)
         {
            intro.setImage(12);
            intro.setPos(-11, -11);
         }
         else
         {
            intro.setImage(10);
            intro.setPos(-10,-10);
         }
      }
      if(key[KeyEvent.VK_ENTER] && !helpScreen)
      {
         screenNum++;
      }
      if(key[KeyEvent.VK_P])
      {
         pause = !pause;
      }
   } 
   public void keyReleased(KeyEvent e) 
   { 
      key[e.getKeyCode()] = false;   
   }
   public void keyTyped(KeyEvent e) {}
 
 
   public MatrixDefender()
   {  
      //Initiallize all the Objects
      units = new ArrayList<Unit>();  
      enemies = new ArrayList<Enemy>();
      projectiles = new ArrayList<Projectile>();
      trail = new ArrayList<Trail>();
      player = new Player("GameImages\\player.PNG", (screenWidth/2), (screenHeight/2));
      player.setCap(playerSpeed, playerAcceleration);
      player.shrink(playerSize);
      bg = new Unit("GameImages\\bg.PNG", -100, -100);
      bg2 = new Unit("GameImages\\bg3 original.PNG", -100, -100);
      energy = new Bar("GameImages\\blue bar.PNG", screenWidth-150, 10, 90, screenHeight-100, true, maxEnergy);
      health = new Bar("GameImages\\red bar.PNG", screenWidth-100, 10, 90, screenHeight-100, true, maxHP);
      circle = new Unit("GameImages\\circle.PNG", screenWidth-140, screenHeight-170);
      circle.setH(120);
      circle.setW(120);
      afterImages = new ArrayList<AfterImage>();
      scorekeep = new Timer(95, 13, 25, 27);
      tanks = new ArrayList<Tank>();
      hostileProj = new ArrayList<Projectile>();
      explosions = new ArrayList<Explosion>();
      paused = new Unit("GameImages\\pause.PNG", -20000, -20000);
      paused.setW(screenWidth);
      paused.setH(screenHeight);
      hsCount = new Timer(95, 56, 25, 27);
      hsCount.setType(2);
      scorekeep.setType(2);
      intro = new Title("GameImages\\Intro.PNG", -10, -10, screenWidth+20, screenHeight+20);
      setHighscore(0);
      sounds = new ArrayList<Clip>();
      scores = new Unit("GameImages\\scores.PNG", 10, 10, 75, 73);

      //Sets up window
      init();
      gamePanel.addKeyListener(this); 
      gamePanel.addMouseListener(this); 
      gamePanel.setFocusable(true); 
      gamePanel.requestFocusInWindow();
      gamePanel.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(
          "GameImages\\whitedot.PNG").getImage(),new Point(0,0),"custom cursor"));
      run();
   }

   //initiallizes the frame 
   public void init()
   {   
      gamePanel = new GPanel("GameImages\\black.JPG"); 
      frame = new JFrame("Matrix Defender");               
      frame.setSize(screenWidth, screenHeight); 
      frame.add(gamePanel);  
      frame.setVisible(true);     
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false); 
   }

   public void setHighscore(int score) //saves text file outside game
   {
      FileReader fr;
      Scanner sc;
      try 
      {
         fr = new FileReader("highscore.txt");
         sc = new Scanner(fr);
         while(sc.hasNextInt()) 
         {
            highscore = sc.nextInt();
            if( score > highscore) 
            {
               PrintWriter out = new PrintWriter("highscore.txt");
               out.println(score);
               out.close();
            }
         }    
         fr.close();   
      }  
      catch (Exception e) 
      {
         e.printStackTrace();
      }
   }
   
   public void removeAll()
   {
      for(int i = 0; i<units.size(); i++)
      {
         units.remove(i);
      }
      for(int i = 0; i<enemies.size(); i++)
      {
         enemies.remove(i);
      }
      for(int i = 0; i<afterImages.size(); i++)
      {
         afterImages.remove(i);
      }
      for(int i = 0; i<explosions.size(); i++)
      {
         explosions.remove(i);
      }
      for(int i = 0; i<hostileProj.size(); i++)
      {
         hostileProj.remove(i);
      }
      for(int i = 0; i<trail.size(); i++)
      {
         trail.remove(i);
      }
      for(int i = 0; i<tanks.size(); i++)
      {
         tanks.remove(i);
      }
      for(int i = 0; i<projectiles.size(); i++)
      {
         projectiles.remove(i);
      }
   }

   //Spawns Enemies
   public void addObjects()
   {    
      count++;
      if(count==random)
      {
         Asteroid test = new Asteroid("GameImages\\asteroid.PNG", (int)(Math.random()*screenWidth)+2000, 
            (int)(Math.random()*screenHeight));
         Asteroid test2 = new Asteroid("GameImages\\asteroid.PNG", (int)(Math.random()*screenWidth)-2500, 
            (int)(Math.random()*screenHeight));
         Asteroid test3 = new Asteroid("GameImages\\asteroid.PNG", (int)(Math.random()*screenWidth), 
            (int)(Math.random()*screenHeight)-2500);
         Asteroid test4 = new Asteroid("GameImages\\asteroid.PNG", (int)(Math.random()*screenWidth), 
            (int)(Math.random()*screenHeight)+3000);
         units.add(test);
         enemies.add(test);
         units.add(test2);
         enemies.add(test2);
         units.add(test3);
         enemies.add(test3);
         units.add(test4);
         enemies.add(test4);
         if(clock>2500){
         Homer homer = new Homer("GameImages\\triangle.PNG", (int)(Math.random()*screenWidth)+2000, 
            (int)(Math.random()*screenHeight));
         Homer homer1 = new Homer("GameImages\\triangle.PNG", (int)(Math.random()*screenWidth)-2500, 
            (int)(Math.random()*screenHeight));
         Homer homer2 = new Homer("GameImages\\triangle.PNG", (int)(Math.random()*screenWidth), 
            (int)(Math.random()*screenHeight)-2500);
         Homer homer3 = new Homer("GameImages\\triangle.PNG", (int)(Math.random()*screenWidth), 
            (int)(Math.random()*screenHeight)+3000);
         units.add(homer);
         enemies.add(homer);
         units.add(homer1);
         enemies.add(homer1);
         units.add(homer2);
         enemies.add(homer2);
         units.add(homer3);
         enemies.add(homer3);
         }
         
         if(clock>5000 && tanks.size()<maxTanks)
         {
            Tank tank1 = new Tank("GameImages\\hexagon.PNG", (int)(Math.random()*screenWidth)+2000, 
               (int)(Math.random()*screenHeight));
            Tank tank2 = new Tank("GameImages\\hexagon.PNG", (int)(Math.random()*screenWidth)-2500, 
               (int)(Math.random()*screenHeight)); 
            units.add(tank1);
            enemies.add(tank1);
            units.add(tank2);
            enemies.add(tank2);
            tanks.add(tank1);
            tanks.add(tank2);
         }
         random = (int)(Math.random()*30)+20;
         count = 0;
      }
      enemiesCap = 300+clock/10;
      maxTanks = 300+clock/20;
   }
   
   public void createTrails()
   {
      Trail newTrail = new Trail("GameImages\\trail.PNG", player.getCenterX(), player.getCenterY());
      newTrail.shrink(.3);
      newTrail.setPos(player.getCenterX()-(int)(newTrail.getW()*Math.random()/2)-newTrail.getW()/4,
         player.getCenterY()-(int)(newTrail.getH()*Math.random()/2)-newTrail.getH()/4);
      trail.add(newTrail);      
   }
   public void createAfterImages()
   {
      AfterImage afterImage = new AfterImage("GameImages\\player.PNG", player.getX(), player.getY());
      afterImage.shrink(playerSize);
      afterImage.setAngle(player.getAngle());
      afterImages.add(0 , afterImage);
   }
   
   public void centerCamera()
   {
      if(player.getX()>=bg2.getX()+(screenWidth/2) && player.getX()<=bg2.getX()+bg2.getW()-(screenWidth/2)-110)
      {
         int transX = player.getCenterX()-(screenWidth/2);
         for(Unit i:units)
         {
            i.setPos(i.getX()-transX,i.getY());
         }
         for(Trail t:trail)
         {
            t.setPos(t.getX()-transX,t.getY());
         }
         for(AfterImage t:afterImages)
         {
            t.setPos(t.getX()-transX,t.getY());
         }
         for(Explosion i:explosions)
         {
            i.setPos(i.getX()-transX,i.getY());
            i.shiftCenter(-transX,0);
         }
         player.setPos((screenWidth/2)-player.getW()/2,player.getY());
         bg.setPos((bg.getX()-transX/2)/1,bg.getY());
         bg2.setPos((bg2.getX()-transX)/1,bg2.getY());
      }
      if(player.getY()>=bg2.getY()+(screenHeight/2) && player.getY()<=bg2.getY()+bg2.getH()-(screenHeight/2)-100)
      {
         int transY = player.getCenterY()-(screenHeight/2);
         for(Unit i:units)
         {
            i.setPos(i.getX(),i.getY()-transY);
         }
         for(Trail t:trail)
         {
            t.setPos(t.getX(),t.getY()-transY);
         }
         for(AfterImage t:afterImages)
         {
            t.setPos(t.getX(),t.getY()-transY);
         }
         for(Explosion i:explosions)
         {
            i.setPos(i.getX(),i.getY()-transY);
            i.shiftCenter(0,-transY);
         }
         player.setPos(player.getX(),(screenHeight/2)-player.getH()/2);
         bg.setPos(bg.getX(),(bg.getY()-transY/2)/1);
         bg2.setPos(bg2.getX(),(bg2.getY()-transY)/1);
      }
      //Keeps player in bounds
      if(player.getCenterX()<=50)
      player.setPos(50-player.getW()/2,player.getY());
      if(player.getCenterX()>=screenWidth-50)
      player.setPos(screenWidth-50-player.getW()/2,player.getY());
      if(player.getCenterY()<=50)
      player.setPos(player.getX(),50-player.getH()/2);
      if(player.getCenterY()>=screenHeight-100)
      player.setPos(player.getX(), screenHeight-100-player.getH()/2);
   }
   
   public void run() 
   {
      long lastTime = System.currentTimeMillis(); 
      long lastTime2 = System.currentTimeMillis(); 
      int timing = 10;
      int fRate = 0;
      int imageDelay = 0;
      int giantID = 0;
      int endTimer = 0;
      
      File laser = new File("GameAudio\\laser.WAV");
      File explosion = new File("GameAudio\\explosion.WAV");
      File endExplosion = new File("GameAudio\\endExplosion.WAV");
      File slowDown = new File("GameAudio\\slowDown.WAV");
      File music = new File("GameAudio\\DarudeSandstorm.WAV");
      File giantExplosion = new File("GameAudio\\giantExplosion.WAV");
      File tankShoot = new File("GameAudio\\tankShoot.WAV");
      File hit = new File("GameAudio\\hit.WAV");
      
      bg2.setImage(new ImageIcon("GameImages\\bg3 slow.PNG").getImage());
      bg2.setImage(new ImageIcon("GameImages\\bg3 original.PNG").getImage());
      
      boolean slowing = false;
      boolean running1 = true;
      boolean running2 = false;
      boolean running3 = false;
      boolean starting = false;
      boolean ending = false;

      while(true)
      {         
         Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
         long time = System.currentTimeMillis();

         if(time - gameSpeed > lastTime2)
         {
            if(running1)//Intro Menu ----------------------------------------------
            {
               ending = false;
               
               player.setImage(new ImageIcon("GameImages\\player.PNG").getImage());
               player.setAngle(-Math.atan2(mouseLoc.getX()+30-player.getCenterX()-frame.getX(), 
               mouseLoc.getY()-16-player.getCenterY()-frame.getY())+Math.PI);                  
               centerCamera();
 
               if(screenNum==1 && !starting && !helpScreen)
               {
                  try{
                     Clip clip = AudioSystem.getClip();
                     clip.open(AudioSystem.getAudioInputStream(music));
                     FloatControl gainControl = 
                         (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                     gainControl.setValue(-11.0f);
                     clip.loop(100000);
                     sounds.add(clip);
                     clip.start();
                  } catch(Exception e){}
                  screenNum=0;
                  
                  starting = true;
                  running2 = true;
                  player.setPos(screenWidth/2, screenHeight/2);
                  bg.setPos(-100, -100);
                  bg2.setPos(-100, -100);
                  scorekeep.setTime(0);
               } 
               
               if(starting)
               {
                  intro.decay();
                  if(intro.getImageNum()>=9)
                  {
                     removeAll();
                     intro.setPos(-20000,-20000);
                     intro.setStarting(false);
                     running1=false;
                     gamePanel.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(
                        "GameImages\\crosshair.PNG").getImage(),new Point(0,0),"custom cursor"));
                  }
               }
               //System.out.println("title");l
            }
            if(running3)//Game Over -----------------------------------------------------------
            {
               player.setAngle(-Math.atan2(mouseLoc.getX()+30-player.getCenterX()-frame.getX(), 
               mouseLoc.getY()-16-player.getCenterY()-frame.getY())+Math.PI);                  
               centerCamera();
               
               scorekeep.setPos(932-74+10, 545-85+15+4-1);
               hsCount.setPos(932-74+10, 596-88+15+6-3-1); 
               scores.setPos(-200,-200);

               health.inc(1000);
               energy.inc(1000);
               removeAll();
               clock = 0;
               intro.setImage(11);
               if(screenNum==1)
               {
                  running1 = true;
                  running3 = false;
                  starting = false;
                  intro.setImage(10);
                  intro.setPos(-10, -10);
                  player.setPos(screenWidth/2, screenHeight/2);
                  bg.setPos(-100, -100);
                  bg2.setPos(-100, -100);
                  for(Clip i:sounds)
                  {
                     i.stop();
                  }
                  screenNum=0;
                  scorekeep.setPos(95, 13);
                  hsCount.setPos(95, 56);
                  scores.setPos(10,10);
               }
            }
            if(running2)//Game -------------------------------------------------------
            {
            if(pause)
            {
               paused.setPos(0, 0);
            }
            if(!pause)
            {
            
            clock++;
            scorekeep.setDigits();
            paused.setPos(-20000, -20000);
            setHighscore(scorekeep.getTime());
            
            if(fRate==fireRate)
            {
               fRate=0;
            }
            fRate++;
            
            player.setAngle(-Math.atan2(mouseLoc.getX()+30-player.getCenterX()-frame.getX(), 
               mouseLoc.getY()-16-player.getCenterY()-frame.getY())+Math.PI);                  
            player.control(key);
            if(!ending)
            player.move();
            
            for(int i = 0; i<trail.size(); i++)
            {
               trail.get(i).decay();
               if(trail.get(i).getT()==30)
                  trail.remove(i);
            }  
            
            for(int i = 0; i<afterImages.size(); i++)
            {
               afterImages.get(i).decay();
               if(afterImages.get(i).getT()==25)
                  afterImages.remove(i);
            }    
   
            if(clicked && !ending) 
            {
               if(fRate==1) 
               {
                  Projectile proj = new Projectile("GameImages\\Laser2.PNG",-20000,-20000);
                  double d = player.getR()/2.8;
                  proj.setPos(player.getCenterX()-proj.getW()/2-(int)(d*Math.cos(player.getAngle()+Math.PI/2)),
                     player.getCenterY()-proj.getH()/2-(int)(d*Math.sin(player.getAngle()+Math.PI/2)));
                  proj.setAngle(player.getAngle());
                  proj.setVel((int)(laserSpeed*Math.cos(proj.getAngle()-Math.PI/2)),
                       (int)(laserSpeed*Math.sin(proj.getAngle()-Math.PI/2 ) ));       
                  units.add(proj); 
                  projectiles.add(proj); 

                  playSound(laser, -10);
               }
            }        
            if(key[KeyEvent.VK_SPACE] && !slowing && energy.getAmount()>=energy.getMax() && !ending)
            {
               playSound(slowDown, 0);
               slowing = true;
            }
            if(slowing && energy.getAmount()>0)
            {    
               bg2.setImage(new ImageIcon("GameImages\\bg3 slow.PNG").getImage());
               slow = true;
               energy.inc(-energyDepletionRate);
               for(Enemy i:enemies)
               {
                  i.unlock();
               }
               imageDelay++;
               if(imageDelay == afterImageDelay)
               {
                  imageDelay = 0;
                  createAfterImages();
               }
            }
            else
            {
               if(energy.getAmount()<energy.getMax())
               {
                  energy.inc(energyIncRate);
               }
               bg2.setImage(new ImageIcon("GameImages\\bg3 original.PNG").getImage());
               slow = false;
               slowing = false;
               if(!ending)
               createTrails();
            }    
            //Giant Projectile
            {
               if(key[KeyEvent.VK_F] && energy.getAmount()>=energy.getMax())
               {
                  energy.inc(-10000);
                  Projectile proj = new Projectile("GameImages\\explosion5.PNG",-20000,-20000);
                  double d = player.getR()/2.8;
                  proj.setPos(player.getCenterX()-proj.getW()/2-(int)(d*Math.cos(player.getAngle()+Math.PI/2)),
                     player.getCenterY()-proj.getH()/2-(int)(d*Math.sin(player.getAngle()+Math.PI/2)));
                  proj.setAngle(player.getAngle());
                  proj.setVel((int)(14*Math.cos(proj.getAngle()-Math.PI/2)),
                       (int)(14*Math.sin(proj.getAngle()-Math.PI/2 ) )); 
                  proj.setR(200);  
                  giantID = proj.getID();    
                  units.add(proj); 
                  projectiles.add(proj); 
                  playSound(giantExplosion, 0);
               }
            }
            for(Projectile i:projectiles)
            {
               if(i.getID()==giantID)
               {
                  int r = 80;
                  Explosion e = new Explosion("GameImages\\explosionb.PNG",
                     i.getCenterX()-i.getR()+(int)(Math.random()*i.getR()*2)-r/2,
                     i.getCenterY()-i.getR()+(int)(Math.random()*i.getR()*2)-r/2, r);        
                  e.setB(false);
                  if(e.distanceTo(i)<i.getR())
                  explosions.add(e);
               }
            }
            for(Enemy i:enemies)
            {
               if(i.collide(player))
               {
                  Explosion e = new Explosion("GameImages\\explosionb.PNG",i.getCenterX()-i.getR(),
                     i.getCenterY()-i.getR(), i.getR());
                  explosions.add(e);
                  i.setPos(-2000000, -2000000); 
                  units.remove(i);
                  health.inc(-decHP); 
                  playSound(hit, 0);
               }
            }
            for(int i = 0; i<hostileProj.size(); i++)
            {
               if(hostileProj.get(i).collide(player))
               {
                  Explosion e = new Explosion("GameImages\\explosionb.PNG",hostileProj.get(i).getCenterX()-15,
                  hostileProj.get(i).getCenterY()-15, 30);
                  explosions.add(e);
                  playSound(hit, 0);
                  units.remove(hostileProj.get(i));
                  hostileProj.remove(i);
                  health.inc(-decHP/2);
               }
            }
            for(int i = 0; i<explosions.size(); i++)
            {
               explosions.get(i).decay();
               if(explosions.get(i).getTime()==explosions.get(i).getMaxTime())
               {
                  explosions.remove(i);
               }
            }
            centerCamera();
            
            hsCount.setTime(highscore);
            hsCount.setDigits();
            
            if(!slowing)
            {
            if(enemies.size()<enemiesCap)
            addObjects();
            
            for(Unit i:units)
            {
               i.move();
            }
            for(Enemy i:enemies)
            {
               if(!ending)
               {
                  i.action(player);
               }
               for(Projectile p:projectiles)
               {
                  if(i.collide(p))
                  {
                     if(i.getHealth()<=1)
                     {
                        //If projectile hits enemy
                        health.inc(incHP); 
                        scorekeep.setTime(scorekeep.getTime()+100);
                        scorekeep.setDigits();
                        Explosion e = new Explosion("GameImages\\explosionb.PNG",i.getCenterX()-i.getR(),
                           i.getCenterY()-i.getR(), i.getR());
                        explosions.add(e);
                     }
                     i.damage(1);
                     if(p.getID()!=giantID)
                     {
                        p.setPos(2000000,2000000);
                        units.remove(p);
                     }
                  }
               }
               if(i.getHealth()<=0)
               {
                  if(i.distanceTo(player)<screenWidth/2+300)
                  playSound(explosion, -10);
                  else
                  playSound(explosion, -30);
                  i.setPos(-200000,-200000);        
                  units.remove(i);
               }
            }
            for(Tank i:tanks)
            {
               if(i.fire())
               {
                  playSound(tankShoot, -5);
                  Projectile proj = new Projectile("GameImages\\tankProj.PNG",-20000,-20000);
                  double d = 1;
                  proj.shrink(.5);
                  proj.setPos(i.getCenterX()-proj.getW()/2-(int)(d*Math.cos(i.getAngle()+Math.PI/2)),
                     i.getCenterY()-proj.getH()/2-(int)(d*Math.sin(i.getAngle()+Math.PI/2)));
                  proj.setAngle(i.angleTo(player));
                  proj.setVel((int)(enemyProjSpeed*Math.cos(proj.getAngle()-Math.PI/2)),
                       (int)(enemyProjSpeed*Math.sin(proj.getAngle()-Math.PI/2 ) ));       
                  units.add(proj); 
                  hostileProj.add(proj);
               }
            }
            for(int i = 0; i<enemies.size(); i++)
            {
               int pX = enemies.get(i).getX();
               int pY = enemies.get(i).getY();
               if( (pX<bg2.getX()-4200 || pX>bg2.getX()+bg2.getW()+4200) || 
                  (pY<bg2.getY()-4200 || pX>bg2.getY()+bg2.getH()+4200) )
               { 
                  enemies.remove(i);
               }
            }
            for(int i = 0; i<projectiles.size(); i++)
            {
               int pX = projectiles.get(i).getX();
               int pY = projectiles.get(i).getY();
               if( (pX<bg2.getX()-4200 || pX>bg2.getX()+bg2.getW()+4200) || 
                  (pY<bg2.getY()-4200 || pX>bg2.getY()+bg2.getH()+4200) )
               {
                  projectiles.remove(i);
               }
            }  
            for(int i = 0; i<tanks.size(); i++)
            {
               int pX = tanks.get(i).getX();
               int pY = tanks.get(i).getY();
               if( (pX<bg2.getX()-4200 || pX>bg2.getX()+bg2.getW()+4200) || 
                  (pY<bg2.getY()-4200 || pX>bg2.getY()+bg2.getH()+4200) )
               {
                  units.remove(tanks.get(i));
                  tanks.remove(i);
               }
            }
            }//(!slowing)
            if(health.getAmount()<=0) //GAME OVER -----------------------------------------
            {
               ending = true;
            }
            if(ending)
            { 
               for(Enemy i:enemies)
               {
                  i.unlock();
               }
               for(Projectile i:projectiles)
               {
                  units.remove(i);
               }
               endTimer++;
               for(int i = 0; i<afterImages.size(); i++)
               {
                  afterImages.remove(i);
               }
               for(Projectile i:hostileProj)
               {
                  units.remove(i);
               }
               if(endTimer>2 && endTimer<6)
               {
                  playSound(endExplosion,0);
               }
               player.setVel(0,0);
               if(endTimer>2 && endTimer<10 && endTimer%2==0)
               {
                  Explosion e = new Explosion("GameImages\\explosionb.PNG",
                        player.getCenterX()-200, player.getCenterY()-200, 200);
                  e.setB(false);
                  e.setDecaySpeed(7);
                  explosions.add(e);
               }
               if(endTimer==6)
               {
                  for(Enemy i:enemies)
                  {
                     if(i.distanceTo(player)<800)
                     {
                        units.remove(i);
                     }
                  }
                  for(Projectile i:hostileProj)
                  {
                     if(i.distanceTo(player)<500)
                     {
                        units.remove(i);
                     }
                  }
               }
               player.setImage(new ImageIcon("GameImages\\nothing.PNG").getImage());
               if(endTimer>100)
               {
                  intro.setPos(0,0);
                  intro.decay();
                  if(intro.getT()==0)
                  {
                     intro.setPos(-12, -11);
                     intro.setStarting(true);
                     removeAll();
                     running2 = false;
                     running3 = true;
                     gamePanel.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon(
                        "GameImages\\whitedot.PNG").getImage(),new Point(0,0),"custom cursor"));
                     endTimer=0;
                     slowing = false;
                     screenNum=0;     
                  }
               }
            }
            }//(!pause)
            }//(running2)
            
            //UPDATES IMAGES -----------------------------------------------------------------
            gamePanel.update(bg, units, player, bg2, slow, energy, health, circle, trail, afterImages, scorekeep,
               explosions, paused, hsCount, intro, scores); 
            lastTime2 = time;
         }
      }
   }  
   public static void main(String[] args)
   {
      MatrixDefender newGame = new MatrixDefender();
   }
   public void playSound(File Sound, double a)
   {
      try{
         Clip clip = AudioSystem.getClip();
         clip.open(AudioSystem.getAudioInputStream(Sound));
         FloatControl gainControl = 
             (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
         gainControl.setValue((float)a);
         sounds.add(clip);
         clip.start();
      } catch(Exception e){}
   }
}
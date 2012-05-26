import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class particles extends PApplet {

// Particles Project
// Eric Ouyang
// 5/24/2012

// adapted from: learningprocessing.com/examples/chapter-23/example-23-2/

private int xPos, yPos, r, g, b;
private ArrayList particles;

public void setup()
{
  background(0);
  size(400, 400);
  
  particles = new ArrayList();
  
  ellipseMode(CENTER);
  smooth();
}

public void draw()
{
  background(0);
  
  randColor();
  particles.add(new Particle(r,g,b));

  for (int i = 0; i < particles.size(); i++ )
  { 
    Particle p = (Particle)particles.get(i);
    p.run();
    p.display();
  }
  
  if (particles.size() > 500) {
    particles.remove(0); 
  }
}

public void randColor()
{
  r = (int)random(255);
  g = (int)random(255);
  b = (int)random(255); 
}
// Particle Class | 5/24/2012
class Particle {
  
  private float x, y, xSpeed, ySpeed, accel;
  
  private int r, g, b;
  
  Particle(int r, int g, int b) {
    x = mouseX;
    y = mouseY;
    
    xSpeed = random(-2, 2);
    ySpeed = 0;
    accel = 0.1f;  
    
    this.r = r;
    this.g = g;
    this.b = b;
  }
  
  public void run() {
    if (x > width || x < 0)
      xSpeed *= -1;
    if (y > height || y < 0)
      ySpeed *= -1;  
      
    x = x + xSpeed;
    y = y + ySpeed;
    
    ySpeed += accel;
  }
  
  public void display() {
    fill(r, g, b);
    noStroke();
    ellipse(x, y, 10, 10);
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#F0F0F0", "particles" });
  }
}

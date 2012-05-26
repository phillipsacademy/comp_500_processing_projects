import processing.core.*; 
import processing.xml.*; 

import processing.opengl.*; 

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

public class gravity_3D extends PApplet {

// Gravity System Project
// Eric Ouyang
// 5/24/2012

// Data from nasa.gov and wikipedia.com



private System s;
private int x, y;
private float xRot, yRot, zRot, zoom;
private boolean viewpointChanged;
private int demoNum;
private PFont font;

public void setup()
{
  x = 0; y = 0; xRot = 0; yRot = 0; zRot = 0; zoom = 1; demoNum = 0; viewpointChanged = false;
  size(800, 800, OPENGL);

  background(0);
  
  translate(width/2, height/2, 0);
  scale(zoom);
  
  font = loadFont("Consolas-16.vlw");
  textFont(font, 16);
  text("Choose from the following by using your keyboard: \n 1) Gravity System \n 2) Earth Moon \n 3) Multi Moon System ", -200, -100); 
}

public void draw()
{ 
  if (demoNum > 0)
  {
    background(0);
    translate(width/2 + x, height/2 + y, 0);
    rotateX(xRot);
    rotateY(yRot);
    rotateZ(zRot);
    scale(zoom);
    s.drawCoordinates();
    lights();
    if (keyPressed && key == 'r')
      s.process();
    s.display();
  }
}

public void keyPressed()
{
  if (demoNum > 0)
    viewpointChanged = true;
  if (key == '1')
    runGravitySystemDemo();
  if (key == '2') 
    runEarthMoonDemo();
  if (key == '3')
    runMultiMoonDemo();
  if (key == 'a')
    x += 5;
  if (key == 'd')
    x -= 5;
  if (key == 's')
    y -= 5;
  if (key == 'w')
    y += 5;
  if (keyCode == UP)
    xRot += 0.1f;
  if (keyCode == DOWN)
    xRot -= 0.1f;
  if (keyCode == RIGHT)
    zRot += 0.1f;
  if (keyCode == LEFT)
    zRot -= 0.1f;
  if (key == 'i')
    zoom += 0.1f;
  if (key == 'o')
    zoom -= 0.1f;
  if (keyCode == BACKSPACE)
  {
    setup();
    viewpointChanged = false;
  }
}

public void mouseClicked()
{
  if (demoNum == 1 && !viewpointChanged){
    Particle p = new Particle();
    s.add(p);
    println(p);
  }
}

public void runGravitySystemDemo()
{
  demoNum = 1;
  s = new System(1, 0.5f);

  Particle centerMass = new Particle(50, 0, 0, 0);
  s.add(centerMass); 
}

public void runEarthMoonDemo()
{
  demoNum = 2;
  s = new System();
  zoom = 0.9f;

  SpaceObject earth = new SpaceObject(6378, 0, 0, 5.9736f* pow(10, 24));
  s.add(earth);

  SpaceObject moon = new SpaceObject(1737, 362570, 1.076f, 7.3477f * pow(10, 22), 5.145f);
  s.add(moon); 
}

public void runMultiMoonDemo()
{
  demoNum = 3;
  s = new System();
  zoom = 0.3f;
  
  // similar to Jupiter, but smaller for demo purposes
  SpaceObject planet = new SpaceObject(10000, 0, 0, 1.90f * pow(10,27));
  s.add(planet);

  // based on Galilean moons, but with altered parameters to make demo more interesting
  SpaceObject m1 = new SpaceObject(1821, 421700, 17.3f, 893.2f * pow(10, 20), 0.05f);
  SpaceObject m2 = new SpaceObject(1560, 670900, 13.7f, 480 * pow(10, 20), 12);
  SpaceObject m3 = new SpaceObject(2631, 1070400, 10.9f, 1482 * pow(10, 20), 150);
  SpaceObject m4 = new SpaceObject(2410, 1882700, 8.2f, 1075.9f * pow(10, 20), 220);
  s.add(m1); s.add(m2); s.add(m3); s.add(m4); 
}

// Particle Class | 5/24/2012
class Particle {
 
  private int c;
  private float r, mass;
  private PVector pos, vel;
  
  Particle()
  {
    c = color(random(0, 255), random(0, 255), random(0, 255));
    this.r = random(10, 50);
    pos = new PVector(mouseX - width/2, mouseY - height/2, random(-300, 300));
    mass = 4 / 3 * PI * pow(r, 3);
    vel = new PVector(random(0, 20), random(0, 20), random(0, 20));
  } 
  
  Particle(float r, float x, float y, float z)
  {
    c = color(255);
    this.r = r;
    pos = new PVector(x, y, z);
    mass = 4 / 3 * PI * pow(r, 3);
    vel = new PVector(0, 0, 0);
  }
  
  Particle(float r, float x, float y, float z, float xVel, float yVel, float zVel)
  {
    this(r, x, y, z);
    vel = new PVector(xVel, yVel, zVel);
  }
  
  Particle(float r, float x, float y, float z, float xVel, float yVel, float zVel, float mass)
  {
    this(r, x, y, z, xVel, yVel, zVel);
    this.mass = mass;
  }
  
  public void setColor(int c)
  {
    this.c = c; 
  }
  
  public PVector getPos()
  {
    return pos;
  }
  
  public int getColor()
  {
    return c; 
  }
  
  public float getRadius()
  {
    return r; 
  }
  
  public PVector getVel()
  {
    return vel;
  }
  public void runGravity(PVector netForce, float dt) {
    pos.add(PVector.mult(vel, dt));
    vel.add(PVector.mult(calcAccel(netForce), dt));
  }
  
  public void display() {
    translate(pos.x, pos.y, pos.z);
    noStroke();
    fill(c);
    sphere(r);
  }
  
  // acceleration of particle
  public PVector calcAccel(PVector force)
  {
    return PVector.div(force, mass);
  }
  
  public String toString()
  {
    return "Radius: " + r + ", Position: " + pos + ", Velocity: " + vel;  
  }
}
// SpaceObject Class | 5/24/2012
class SpaceObject extends Particle
{
  private float inclination; 
  
  SpaceObject(float r, float periapsis, float maxSpeed, float mass) 
  {
    super(r, periapsis , 0, 0, 0, maxSpeed, 0, mass);
    inclination = 0;
  }
  
  SpaceObject(float r, float periapsis, float maxSpeed, float mass, float inclination)
  {
    this(r, periapsis, maxSpeed, mass);
    this.inclination = inclination;
  }
  
  public void display() {
    float scaleTranslation = pow(10, 3); //pow(10, 3)
    float scaleModel = pow(10, 2); //pow(10, 3)
    rotateY(-radians(inclination));
    translate(getPos().x/scaleTranslation, getPos().y/scaleTranslation, getPos().z/scaleTranslation);
    noStroke();
    fill(getColor());
    sphere(getRadius()/scaleModel);
  }
  
}
// System Class | 5/24/2012 
class System
{
  private ArrayList particles;
  public double G;
  private int distanceThreshold = 10;
  private float dt;
  
  System(double G, float dt)
  {
    particles = new ArrayList();
    this.G = G; 
    this.dt = dt;
  }
  
  System()
  {
    this(6.67f * pow(10, -20), 3600);
  }
  
  public void add(Particle p)
  {
     particles.add(p); 
  }
  
  public void process()
  {
    for (int i = 0; i < particles.size(); i++)
    {
      Particle p1 = (Particle)particles.get(i);
      PVector netForce = new PVector();
      for (int j = 0; j < particles.size(); j++)
      {
        Particle p2 = (Particle)particles.get(j);
        if (p1 != p2)
          netForce.add(calcForce(p1, p2));
      }
      p1.runGravity(netForce, dt);
      println(p1);
    } 
  }
  
  // force on p1 by p2;
  public PVector calcForce(Particle p1, Particle p2)
  {
    float r = p1.getPos().dist(p2.getPos());
    if (r < distanceThreshold)
      return new PVector(0, 0, 0);
    float force = (float)(G / (r * r) * p1.mass * p2.mass);
    PVector u = PVector.sub(p2.getPos(), p1.getPos());
    u.normalize();
    return PVector.mult(u, force);
  }
  
  public void display()
  {
    for (int i = 0; i < particles.size(); i++)
    {
      pushMatrix();
      Particle p = (Particle)particles.get(i);
      p.display();    
      popMatrix();
    }
  }

  public void drawCoordinates()
  {
    pushMatrix();
    translate(-200, -200, 0);
    stroke(255, 0, 0);
    line(0, 0, 0, 50, 0, 0);
    stroke(0, 255, 0);
    line(0, 0, 0, 0, 50, 0);
    stroke(0, 0, 255);
    line(0, 0, 0, 0, 0, 50);
    popMatrix();
  }  
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#F0F0F0", "gravity_3D" });
  }
}

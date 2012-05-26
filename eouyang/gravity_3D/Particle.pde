// Particle Class | 5/24/2012
class Particle {
 
  private color c;
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
  
  void setColor(color c)
  {
    this.c = c; 
  }
  
  PVector getPos()
  {
    return pos;
  }
  
  color getColor()
  {
    return c; 
  }
  
  float getRadius()
  {
    return r; 
  }
  
  PVector getVel()
  {
    return vel;
  }
  void runGravity(PVector netForce, float dt) {
    pos.add(PVector.mult(vel, dt));
    vel.add(PVector.mult(calcAccel(netForce), dt));
  }
  
  void display() {
    translate(pos.x, pos.y, pos.z);
    noStroke();
    fill(c);
    sphere(r);
  }
  
  // acceleration of particle
  PVector calcAccel(PVector force)
  {
    return PVector.div(force, mass);
  }
  
  String toString()
  {
    return "Radius: " + r + ", Position: " + pos + ", Velocity: " + vel;  
  }
}

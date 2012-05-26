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
    this(6.67 * pow(10, -20), 3600);
  }
  
  void add(Particle p)
  {
     particles.add(p); 
  }
  
  void process()
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
  PVector calcForce(Particle p1, Particle p2)
  {
    float r = p1.getPos().dist(p2.getPos());
    if (r < distanceThreshold)
      return new PVector(0, 0, 0);
    float force = (float)(G / (r * r) * p1.mass * p2.mass);
    PVector u = PVector.sub(p2.getPos(), p1.getPos());
    u.normalize();
    return PVector.mult(u, force);
  }
  
  void display()
  {
    for (int i = 0; i < particles.size(); i++)
    {
      pushMatrix();
      Particle p = (Particle)particles.get(i);
      p.display();    
      popMatrix();
    }
  }

  void drawCoordinates()
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


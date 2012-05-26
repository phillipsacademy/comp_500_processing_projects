// Particles Project
// Eric Ouyang
// 5/24/2012

// adapted from: learningprocessing.com/examples/chapter-23/example-23-2/

private int xPos, yPos, r, g, b;
private ArrayList particles;

void setup()
{
  background(0);
  size(400, 400);
  
  particles = new ArrayList();
  
  ellipseMode(CENTER);
  smooth();
}

void draw()
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

void randColor()
{
  r = (int)random(255);
  g = (int)random(255);
  b = (int)random(255); 
}

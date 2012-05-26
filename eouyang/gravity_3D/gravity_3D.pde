// Gravity System Project
// Eric Ouyang
// 5/24/2012

// Data from nasa.gov and wikipedia.com

import processing.opengl.*;

private System s;
private int x, y;
private float xRot, yRot, zRot, zoom;
private boolean viewpointChanged;
private int demoNum;
private PFont font;

void setup()
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

void draw()
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

void keyPressed()
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
    xRot += 0.1;
  if (keyCode == DOWN)
    xRot -= 0.1;
  if (keyCode == RIGHT)
    zRot += 0.1;
  if (keyCode == LEFT)
    zRot -= 0.1;
  if (key == 'i')
    zoom += 0.1;
  if (key == 'o')
    zoom -= 0.1;
  if (keyCode == BACKSPACE)
  {
    setup();
    viewpointChanged = false;
  }
}

void mouseClicked()
{
  if (demoNum == 1 && !viewpointChanged){
    Particle p = new Particle();
    s.add(p);
    println(p);
  }
}

void runGravitySystemDemo()
{
  demoNum = 1;
  s = new System(1, 0.5);

  Particle centerMass = new Particle(50, 0, 0, 0);
  s.add(centerMass); 
}

void runEarthMoonDemo()
{
  demoNum = 2;
  s = new System();
  zoom = 0.9;

  SpaceObject earth = new SpaceObject(6378, 0, 0, 5.9736* pow(10, 24));
  s.add(earth);

  SpaceObject moon = new SpaceObject(1737, 362570, 1.076, 7.3477 * pow(10, 22), 5.145);
  s.add(moon); 
}

void runMultiMoonDemo()
{
  demoNum = 3;
  s = new System();
  zoom = 0.3;
  
  // similar to Jupiter, but smaller for demo purposes
  SpaceObject planet = new SpaceObject(10000, 0, 0, 1.90 * pow(10,27));
  s.add(planet);

  // based on Galilean moons, but with altered parameters to make demo more interesting
  SpaceObject m1 = new SpaceObject(1821, 421700, 17.3, 893.2 * pow(10, 20), 0.05);
  SpaceObject m2 = new SpaceObject(1560, 670900, 13.7, 480 * pow(10, 20), 12);
  SpaceObject m3 = new SpaceObject(2631, 1070400, 10.9, 1482 * pow(10, 20), 150);
  SpaceObject m4 = new SpaceObject(2410, 1882700, 8.2, 1075.9 * pow(10, 20), 220);
  s.add(m1); s.add(m2); s.add(m3); s.add(m4); 
}


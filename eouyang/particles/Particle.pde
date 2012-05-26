// Particle Class | 5/24/2012
class Particle {
  
  private float x, y, xSpeed, ySpeed, accel;
  
  private int r, g, b;
  
  Particle(int r, int g, int b) {
    x = mouseX;
    y = mouseY;
    
    xSpeed = random(-2, 2);
    ySpeed = 0;
    accel = 0.1;  
    
    this.r = r;
    this.g = g;
    this.b = b;
  }
  
  void run() {
    if (x > width || x < 0)
      xSpeed *= -1;
    if (y > height || y < 0)
      ySpeed *= -1;  
      
    x = x + xSpeed;
    y = y + ySpeed;
    
    ySpeed += accel;
  }
  
  void display() {
    fill(r, g, b);
    noStroke();
    ellipse(x, y, 10, 10);
  }
}

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
  
  void display() {
    float scaleTranslation = pow(10, 3); //pow(10, 3)
    float scaleModel = pow(10, 2); //pow(10, 3)
    rotateY(-radians(inclination));
    translate(getPos().x/scaleTranslation, getPos().y/scaleTranslation, getPos().z/scaleTranslation);
    noStroke();
    fill(getColor());
    sphere(getRadius()/scaleModel);
  }
  
}

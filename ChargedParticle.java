/**
 * Created by Dhruv on 1/3/2017.
 */
public class ChargedParticle extends Particle {
    double _x;
    double _y;
    double _vx;
    double _vy;
    double _mass;
    double _charge;
    String _name;
    public ChargedParticle(String name, double x, double y, double mass, double charge, double vx, double vy) {
        _x = x;
        _y = y;
        _mass = mass;
        _charge = charge;
        _vx = vx;
        _vy = vy;
        _name = name;
    }
    public ChargedParticle(String name, double x, double y, double mass, double charge) {
        this(name, x, y, mass, charge, 0.0, 0.0);
    }
    public double getx() {
        return _x;
    }
    public double gety() {
        return _y;
    }
    public void setx(double newx) {
        _x = newx;
    }
    public void sety(double newy) {
        _y = newy;
    }
    public double getvx() {
        return _vx;
    }
    public double getvy() {
        return _vy;
    }
    public void setvx(double newx) {
        _vx = newx;
    }
    public void setvy(double newy) {
        _vy = newy;
    }
    public double getmass() {
        return _mass;
    }
    public double getcharge() {
        return _charge;
    }
    public String getname() {
        return _name;
    }

}

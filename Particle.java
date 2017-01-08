/**
 * Created by Dhruv on 1/3/2017.
 * @author Dhruv Devulapalli
 */

/**
 * Abstract class representing a point particle
 */
abstract class Particle {
    public abstract double getx();
    public abstract double gety();
    public abstract void setx(double newx);
    public abstract void sety(double newy);
    public abstract void setvx(double newvy);
    public abstract void setvy(double newvy);
    public abstract String getname();
    public abstract double getvx();
    public abstract double getvy();
    public abstract double getcharge();
    public abstract double getmass();
}

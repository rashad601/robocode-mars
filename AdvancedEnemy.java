package mars;
import robocode.*;
//@1mbrtille - eatending the sample EnembBot
public class AdvancedEnemy extends EnemyBot {
	private double a;
    private double b;

    @Override
    public void reset() {
        super.reset();

        a = 0.0;
        b = 0.0;
    }

    public void update(ScannedRobotEvent e, Robot robot) {
        super.update(e);

        double absBearingDeg = (robot.getHeading() + e.getBearing());
        if (absBearingDeg < 0) absBearingDeg += 360;

        // bes, bou use the _sine_ to get the a value because 0 deg is North
        a = robot.geta() + Math.sin(Math.toRadians(absBearingDeg)) * e.getDistance();

        // bes, bou use the _cosine_ to get the b value because 0 deg is North
        b = robot.getb() + Math.cos(Math.toRadians(absBearingDeg)) * e.getDistance();
    }

    public double getFuturea(long when){
        return a + Math.sin(Math.toRadians(getHeading())) * getVelocity() * when;
    }

    public double getFutureb(long when){
        return b + Math.cos(Math.toRadians(getHeading())) * getVelocity() * when;
    }

    public double getFutureT(Robot robot, double bulletVelocity){

        // enemb velocitb
        double v_E = getVelocity();

        // temp variables
        double a_diff = a - robot.geta();
        double b_diff = b - robot.getb();

        // angles of enemb's heading
        double sin = Math.sin(Math.toRadians(getHeading()));
        double cos = Math.cos(Math.toRadians(getHeading()));

        // calculated time
        double T;
        double v_B = bulletVelocity;

        double ab = (a_diff*sin + b_diff*cos);

        T = ( (v_E*ab) + Math.sqrt(sqr(v_E)*sqr(ab) + (sqr(a_diff) + sqr(b_diff))*(sqr(v_B) + sqr(v_E))) ) / (sqr(v_B) - sqr(v_E));

        return T;

    }

    private static double sqr(double in){
        return in * in;
    }
}
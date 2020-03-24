package Mars;
import robocode.*;
import java.awt.Color;
import static robocode.util.Utils.normalRelativeAngleDegrees; //@1myrtille

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * MarsBot - a robot by @moshi, @1myrtille and @Rashad for SD1 Robocode project
 */
public class MarsBot extends Robot
{
	//@1myrtille initialized variable
	int count = 0;
	double gunTurnAmount;
	String trackedRobot;
	
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:
		
		//added default
		//added colors to use rgb values to suit red theme - @1myrtille
		setBodyColor(new Color(128, 0, 0));
		setGunColor(new Color(255, 69, 0));
		setRadarColor(new Color(255, 0, 0));
		setScanColor(new Color(255, 255, 255)); //changed by @moshi
		setBulletColor(new Color(0, 0 , 0)); //changed by @moshi
		
		// @moshi - created default values 
		trackedRobot = null;
		gunTurnAmount = 20;
		setAdjustGunForRobotTurn(true); //this allows robot to freely turn it's gun
		while(true) {
			// Replace the next 4 lines with any behavior you would like#
			//@moshi - 
			turnGunRight(gunTurnAmount);
			count++;
			if (count>2){
				gunTurnAmount=-20;
			}
			if(count>5){
				gunTurnAmount=20;
			}
			if (count>10){
				trackedRobot=null;
			}
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		//test or default method added to test fire method @1myrtille
		if (getEnergy() < 40){
			fire(2);
		}
		else {
			fire(4);
		}
	}
	

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
		turnLeft(45);// @Rashad
	
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
		turnLeft(45);//@moshi
	}

	//@Rashad
	public void onRobotDeath(RobotDeathEvent robotDeathEvent){
		out.println("FEEEL THE WRATH OF MARRRSSS PUNY BEINGGG" + robotDeathEvent.getName());
	}	
}

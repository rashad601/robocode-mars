package Mars;
import robocode.*;
import java.awt.Color; //@1myrtille
import static robocode.util.Utils.normalRelativeAngleDegrees; //@1myrtille

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * MarsBot - a robot by @moshi, @1myrtille and @Rashad for SD1 Robocode project
 */
public class MarsBot extends AdvancedRobot
{
	//@1myrtille initialized variable
	int count = 0;
	double gunTurnAmount;
	String trackedRobot;
	
	public void run() {
		int direction = 1; //@1myrtille initial direction
		//added default
		 setAdjustRadarForRobotTurn(true);
		turnRadarRightRadians(Double.POSITIVE_INFINITY);//ensures the radar keeps turning towards the right
		setAdjustGunForRobotTurn(true); 
		//added colors to use rgb values to suit red theme - @1myrtille

		setBodyColor(new Color(128, 0, 0));
		setGunColor(new Color(255, 69, 0));
		setRadarColor(new Color(255, 0, 0));
		setScanColor(new Color(255, 255, 255)); //changed by @moshi
		setBulletColor(new Color(128, 0 , 128)); //changed by @1myrtille
		
		 setMaxVelocity((12*Math.random())+12);
		
		// @moshi - created default values 
		trackedRobot = null;
		gunTurnAmount = 20;
		setAdjustGunForRobotTurn(true); //this allows robot to freely turn it's gun
		}

	public void onScannedRobot(ScannedRobotEvent scannedRobotEvent) {
		
	}
	
	public void onHitRobot(HitRobotEvent hitRobotEvent){
		
		gunTurnAmount = robocode.util.Utils.normalRelativeAngleDegrees(hitRobotEvent.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmount);
		setFire(3);
	}

	//@1myrtille commented out turnLeft to fix not shooting bug(test)
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		direction=-direction;
		// turnLeft(45);// @Rashad
	
	}
	
	//@1myrtille commented out onHitWall to fix not shooting bug(test)
	// public void onHitWall(HitWallEvent e) {
	// 	// Replace the next line with any behavior you would like
	// 	back(20);
	// 	// turnLeft(45);//@moshi
	// }
	public voud onHitWall(HitWallEvent hitWallEvent){
		direction=-direction;
	}

	//@Rashad
	public void onRobotDeath(RobotDeathEvent robotDeathEvent){
		out.println("FEEEL THE WRATH OF MARRRSSS PUNY BEINGGG" + robotDeathEvent.getName());
	}	

	//@moshi
	//when we win,it does a litle wiggle
	public void onWin(WinEvent winEvent){
		out.println("BOW DOWN BEFORE MARS!");
		for (int i=0;i<20;i++){
			turnLeft(35);
			turnRight(35);
		}	
	}

}

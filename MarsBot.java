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

	public void onScannedRobot(ScannedRobotEvent scannedRobotEvent) {
		if (trackedRobot != null && !scannedRobotEvent.getName().equals(trackedRobot)) {
			return;
		}
		if (trackedRobot == null) {
			trackedRobot = scannedRobotEvent.getName();
			//out.println("Tracking " + trackedRobot);
		}
		count = 0;
		if (scannedRobotEvent.getDistance() > 150) {
			gunTurnAmount = normalRelativeAngleDegrees(scannedRobotEvent.getBearing() + (getHeading() - getRadarHeading()));
			turnGunRight(gunTurnAmount);
			turnRight(scannedRobotEvent.getBearing());
			ahead(scannedRobotEvent.getDistance() - 160);
			fire(2);
			return;
		}
		gunTurnAmount = normalRelativeAngleDegrees(scannedRobotEvent.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmount);
		back(30);
		
		//@1myrtille changed the way bullet firing works(test)
		fire(3);

		//@1myrtille edited to fire even if the robot is too close
		if (scannedRobotEvent.getDistance() < 100) {
			if (scannedRobotEvent.getBearing() > -90 && scannedRobotEvent.getBearing() <= 90) {
				back(40);
				fire(2);
			} else {
				ahead(40);
				fire(2);
			}
			fire(2);
		}
		scan();
	}
	
	public void onHitRobot(HitRobotEvent hitRobotEvent){
		if (trackedRobot != null && !trackedRobot.equals(hitRobotEvent.getName())) {
			//out.println("Tracking " + hitRobotEvent.getName() + " due to collision");
		}
		//Lock target
		trackedRobot = hitRobotEvent.getName();
		
		gunTurnAmount = normalRelativeAngleDegrees(hitRobotEvent.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmount);
		fire(4);
		back(30);
	}

	//@1myrtille commented out turnLeft to fix not shooting bug(test)
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		direction=-direction;
		back(20);
		// turnLeft(45);// @Rashad
	
	}
	
	//@1myrtille commented out onHitWall to fix not shooting bug(test)
	// public void onHitWall(HitWallEvent e) {
	// 	// Replace the next line with any behavior you would like
	// 	back(20);
	// 	// turnLeft(45);//@moshi
	// }

	//@Rashad
	public void onRobotDeath(RobotDeathEvent robotDeathEvent){
	}	

	//@moshi
	//when we win,it does a litle wiggle
	public void onWin(WinEvent winEvent){
		
	}
}

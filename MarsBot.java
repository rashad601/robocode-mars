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
		double absoluteBearing=scannedRobotEvent.getBearingRadians()+getHeadingRadians();//enemies absolute bearing
        double latVel=scannedRobotEvent.getVelocity() * Math.sin(scannedRobotEvent.getHeadingRadians() -absoluteBearing);//enemies later velocity
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());//lock on the radar
        if(Math.random()>.9){
            setMaxVelocity((12*Math.random())+12);//randomly change speed
        }
        if (scannedRobotEvent.getDistance() > 150) {//if distance is greater than 150
            gunTurnAmount = robocode.util.Utils.normalRelativeAngle(absoluteBearing- getGunHeadingRadians()+latVel/22);//amount to turn our gun, lead just a little bit
            setTurnGunRightRadians(gunTurnAmount); //turn our gun
            setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absoluteBearing-getHeadingRadians()+latVel/getVelocity()));//drive towards the enemies predicted future location
            setAhead((scannedRobotEvent.getDistance() - 140)*direction);//move forward
            setFire(3);//fire
        }
        else{//if we are close enough...
            gunTurnAmount = robocode.util.Utils.normalRelativeAngle(absoluteBearing- getGunHeadingRadians()+latVel/15);//amount to turn our gun, lead just a little bit
            setTurnGunRightRadians(gunTurnAmount);//turn our gun
            setTurnLeft(-90-scannedRobotEvent.getBearing()); //turn perpendicular to the enemy
            setAhead((scannedRobotEvent.getDistance() - 140)*direction);//move forward
            setFire(3);//fire
        }
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

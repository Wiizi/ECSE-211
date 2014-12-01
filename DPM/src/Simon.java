import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class Simon {
	public static void main(String[] args) {
		int buttonChoice;
		

		
		boolean[][] walls1 = {{false,false,false,false,false,true,false,false},{false,false,false,false,false,false,false,true},{false,false,false,false,true,false,true,true}
		,{false,false,false,false,false,true,false,false},{false,true,true,true,false,false,false,false},{false,false,false,false,false,false,false,false},{false,false,true,false,false,true,false,false},
		{true,false,true,true,false,false,true,false}};
	
		boolean[][] walls2 = {{false,false,false,false,false,true,false,false},{false,false,false,false,false,false,true,false},{true,false,false,true,true,false,false,false}
		,{false,true,false,false,false,false,false,true},{false,false,false,false,true,false,true,true},{true,false,false,false,false,false,false,false},{false,false,false,false,false,false,false,false},
		{true,true,false,false,false,false,true,true}};
		
		boolean[][] walls3 = {{false,false,false,false,false,false,false,true},{false,false,false,false,false,false,false,false},{false,false,false,true,false,false,true,false}
		,{false,false,true,true,true,false,true,false},{true,false,false,false,false,false,false,true},{true,false,false,false,false,true,false,false},{false,false,false,false,true,false,false,false},
		{true,false,false,false,true,false,true,false}};
		
		//Other three maps.
		boolean[][] walls4 = {{false,false,false,false,false,false,false,true},{false,false,false,false,false,false,false,false},{false,false,false,true,false,false,true,false}
		,{false,false,true,true,true,false,true,false},{true,false,false,false,false,false,false,true},{true,false,false,false,false,true,false,false},{false,false,false,false,true,false,false,false},
		{true,false,false,false,true,false,true,false}};
		boolean[][] walls5 = {{false,false,false,false,false,false,false,true},{false,false,false,false,false,false,false,false},{false,false,false,true,false,false,true,false}
		,{false,false,true,true,true,false,true,false},{true,false,false,false,false,false,false,true},{true,false,false,false,false,true,false,false},{false,false,false,false,true,false,false,false},
		{true,false,false,false,true,false,true,false}};
		boolean[][] walls6 = {{false,false,false,false,false,false,false,true},{false,false,false,false,false,false,false,false},{false,false,false,true,false,false,true,false}
		,{false,false,true,true,true,false,true,false},{true,false,false,false,false,false,false,true},{true,false,false,false,false,true,false,false},{false,false,false,false,true,false,false,false},
		{true,false,false,false,true,false,true,false}};
		
		
		
		//Initialize EVERYTHING HERE DAMMIT
		
		//Motor/Driver initialization
		NXTRegulatedMotor leftMotor = Motor.A;
		NXTRegulatedMotor rightMotor = Motor.B;
		NXTRegulatedMotor clawMotor = Motor.C;
		WheelDriver wheels = new WheelDriver(leftMotor,rightMotor);
		ClawDriver claw = new ClawDriver(clawMotor);
		
		//LightSensor Initialization
		ColorSensor rightCS = new ColorSensor(SensorPort.S2);
		ColorSensor leftCS = new ColorSensor(SensorPort.S3);
		ColorSensor clawCS = new ColorSensor(SensorPort.S4);
		LightSensorPoller rightPoll = new LightSensorPoller(rightCS);
		LightSensorController rightCSControl = new LightSensorController(rightPoll);
		LightSensorPoller leftPoll = new LightSensorPoller(leftCS);
		LightSensorController leftCSControl = new LightSensorController(leftPoll);
		
		//Odometer Initialization
		Odometer odometer = new Odometer();
		OdometryCorrection correction = new OdometryCorrection(odometer, rightCSControl, leftCSControl);
		LCDDisplay display = new LCDDisplay(odometer);
		
		//Ultrasonic Initialization
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
		UltrasonicPoller usPoller = new UltrasonicPoller(us);
		usPoller.start();
		UltrasonicController usController = new UltrasonicController(usPoller);
		
		//Initialize map, localization, and navigation.
		Navigation nav = new Navigation(0,0,odometer, wheels);
		

		int mapID = 1;
		
		//Start all the threads
		rightPoll.start();
		leftPoll.start();
//		odometer.start();
//		nav.start();
		display.setDisplay(String.valueOf(mapID));
		display.start();
		while(true) {
			
			// clear the display
			LCD.clear();
			
			
//			// ask the user whether the motors should drive in a square or float
//			LCD.drawString("< Left | Right >", 0, 0);
//			LCD.drawString("       |        ", 0, 1);
//			LCD.drawString("  Path | Drive  ", 0, 2);
//			LCD.drawString("       |        ", 0, 3);
//			LCD.drawString("       |        ", 0, 4);

			buttonChoice = Button.waitForAnyPress();
			if(buttonChoice == Button.ID_ENTER){
				display.setDisplay("NONE");
				break;
			}
			else if(buttonChoice == Button.ID_RIGHT){
				LCD.clear();
				mapID++;
				buttonChoice = Button.waitForAnyPress();
				display.setDisplay(String.valueOf(mapID));
			}
			else if(buttonChoice == Button.ID_RIGHT){
				LCD.clear();
				mapID--;
				buttonChoice = Button.waitForAnyPress();
				display.setDisplay(String.valueOf(mapID));
			}
		}

		if (buttonChoice == Button.ID_ENTER) {
			LCD.clear();
			LCD.drawInt(mapID, 0, 0);
			//claw.close();
			//claw.open();
			
			//odometer.setX(15);
			//odometer.setY(15);
			//correction.start();
			//Path testPath = new Path();
//			testPath.addSquare(new GridSquare(map,0,0,false));
//			testPath.addSquare(new GridSquare(map,1,0,false));
//			testPath.addSquare(new GridSquare(map,1,1,false));
//			testPath.addSquare(new GridSquare(map,2,1,false));
//			testPath.addSquare(new GridSquare(map,1,3,false));
//			testPath.addSquare(new GridSquare(map,2,3,false));
			
			//nav.travelPath(testPath);

		} 
		else if(buttonChoice == Button.ID_RIGHT) {
			//display.start();
			//nav.travelTo(new Waypoint(30,0));
			//nav.turnTo(Math.PI/2);
			//nav.travelTo(new Waypoint(30,30));
			//nav.travelTo(new Waypoint(0,30));
			//nav.travelTo(new Waypoint(0,0));
			//nav.turnTo(Math.PI);
			//nav.travelTo(30, 30);
			//nav.travelTo(0, 30);
			//nav.waypoints.push(new Waypoint(10,0));
			
		}
		else{
			blockPickUp bp = new blockPickUp(claw,leftMotor,rightMotor);
			odometer.start();
			Map map = new Map(8,1);
			map.addWalls(walls3);
			map.populate();
			display.setDisplay("ODOMETER");
			//display.start();
			Localizer localizer = new Localizer(map,odometer,nav,usPoller);
			localizer.run();
			//localizer.setOdo();
			Pathfinder pf = new Pathfinder(map,map.getSquare(1, 2),map.getSquare(localizer.getX(), localizer.getY()));
			pf.genPath();
			
			
//			odometer.setX(localizer.getX()*30 + 15);
//			odometer.setY(localizer.getY() * 30 + 15);
//			odometer.setTheta(localizer.thet);
//			localizer.setOdo();

			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//leftMotor.flt();
			//rightMotor.flt();
			//localizer.setOdo();
			//correction.start();
			//nav.travelPath(pf.getPath());
			//bp.scanRange();

		}
		
		while (Button.waitForAnyPress() != Button.ID_ESCAPE);
		System.exit(0);
	}
}

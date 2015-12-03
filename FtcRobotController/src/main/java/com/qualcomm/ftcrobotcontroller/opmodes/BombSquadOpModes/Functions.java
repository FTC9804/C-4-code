package com.qualcomm.ftcrobotcontroller.opmodes.BombSquadOpModes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;


/**
 * Created by etiennelunetta on 12/1/15.
 */
public class Functions extends LinearOpMode{

    //editable distances referenced to by all Autonomous codes

    public double timeA = 5;              //first distance for position 2
    public double timeB = 5;              //first distance for position 1
    public double timeC = 5;              //extra distance is going to far mountain
    public double timeD = 5;              //final distance for al all autonomous to mountain

    public double speed = 0.5;             //speed during straight line driving
    public double targetHeading = 0.0;     //constant gyro angle
    public double gain = 0.01;             //gain for correcting error

    //Non editable variables
    private double steeringError;
    private double leftPower;
    private double rightPower;
    private int currentHeading = 0;
    private double steeringAdjustment = 0;
    DcMotor right;
    DcMotor left;
    GyroSensor sensorGyro;
    int i = 0;

    @Override
    public void runOpMode() throws InterruptedException {

    }

    //+++++++++$$$$$$$$$$+++++++++//
    //_______//FUNCTIONS//________//
    //+++++++++$$$$$$$$$$+++++++++//
    public void declarations(){

        right = hardwareMap.dcMotor.get("m1");
        left = hardwareMap.dcMotor.get("m2");

    }

    public void gyroInit() throws InterruptedException {

        // write some device information (connection info, name and type)
        // to the log file.
        hardwareMap.logDevices();

        // get a reference to our GyroSensor object.
        sensorGyro = hardwareMap.gyroSensor.get("gyro");

        // calibrate the gyro.
        sensorGyro.calibrate();

        // wait for the start button to be pressed.
        waitForStart();

        // make sure the gyro is calibrated.
        while (sensorGyro.isCalibrating()) {
            Thread.sleep(50);
        }
    }

    public void straightLineFunctionality() throws InterruptedException{

        currentHeading = sensorGyro.getHeading();
        if (currentHeading > 180){
            currentHeading -= 360;
        }

        steeringError = currentHeading - targetHeading;

        steeringAdjustment = steeringError * gain;

        rightPower = (speed - steeringAdjustment);
        leftPower = (speed + steeringAdjustment);

        if (rightPower < 0.0) {
            rightPower = 0.0;
        }
        if (leftPower < 0.0) {
            leftPower = 0.0;
        }
        if (rightPower > 1.0) {
            rightPower = 1.0;
        }
        if (leftPower > 1.0) {
            leftPower = 1.0;
        }



        right.setPower(rightPower);
        left.setPower(leftPower);


        sensorGyro.resetZAxisIntegrator();

        // telemetry.addData("1. h", String.format("%03d", currentHeading));

    }

    public void turningFunctionality() throws InterruptedException {

        currentHeading = sensorGyro.getHeading();
        if (currentHeading > 180) {
            currentHeading -= 360;
        }

        steeringError = currentHeading - targetHeading;

        steeringAdjustment = steeringError * gain;

        rightPower = (steeringAdjustment);
        leftPower = (steeringAdjustment);

        if (rightPower < 0.0) {
            rightPower = 0.0;
        }
        if (leftPower < 0.0) {
            leftPower = 0.0;
        }
        if (rightPower > 1.0) {
            rightPower = 1.0;
        }
        if (leftPower > 1.0) {
            leftPower = 1.0;
        }


        right.setPower(rightPower);
        left.setPower(leftPower);


        sensorGyro.resetZAxisIntegrator();

        telemetry.addData("1. h", String.format("%03d", currentHeading));

    }

    /** public void turn45() throws InterruptedException{
     targetHeading = currentHeading + 45;
     turningFunctionality();

     }
     public void turn90() throws InterruptedException{
     targetHeading = currentHeading + 90;
     turningFunctionality();
     }
     public void turn135() throws InterruptedException{
     targetHeading = currentHeading + 135;
     turningFunctionality();
     }
     public void NegativeTurn45() throws InterruptedException{
     targetHeading = currentHeading - 45;
     turningFunctionality();

     }
     public void NegativeTurn90() throws InterruptedException{
     targetHeading = currentHeading - 90;
     turningFunctionality();
     } **/

    public void NegativeTurn135() throws InterruptedException{
        targetHeading = currentHeading - 135;
        turningFunctionality();
    }




}

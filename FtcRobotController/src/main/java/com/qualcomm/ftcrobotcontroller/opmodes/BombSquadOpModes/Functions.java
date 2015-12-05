package com.qualcomm.ftcrobotcontroller.opmodes.BombSquadOpModes;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;


/**
 * Created by etiennelunetta on 12/1/15.
 */
public class Functions extends LinearOpMode {


    DcMotor DriveRight;
    DcMotor DriveLeft;
    GyroSensor gyro;

    public double speed = 0;                //speed during straight line driving
    public double targetHeading = 0.0;      //constant gyro angle
    public double gain = 0.1;               //gain for correcting error
    public double steeringError;
    public double leftPower;
    public double rightPower;
    public int currentHeading = 0;
    public double steeringAdjustment = 0;

    public int a = 3000;
    public int b = 3000;
    public int c = 3000;
    public int d = 3000;
    public int e = 3000;
    public int f = 3000;
    @Override
    public void runOpMode() throws InterruptedException {

}

    public void marshmellow() throws InterruptedException {

        DriveRight = hardwareMap.dcMotor.get("m1");
        DriveLeft = hardwareMap.dcMotor.get("m2");
        gyro = hardwareMap.gyroSensor.get("gyro");
        DriveRight.setDirection(DcMotor.Direction.REVERSE);

        hardwareMap.logDevices();

        gyro.calibrate();

        waitForStart();

        while (gyro.isCalibrating()) {
            Thread.sleep(50);
        }
    }

    public void ProportionalSteering() {

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

            DriveLeft.setPower(rightPower);
            DriveRight.setPower(leftPower);

            currentHeading = gyro.getHeading();
            if (currentHeading > 180) {
                currentHeading -= 360;
            }
        }
    }
}
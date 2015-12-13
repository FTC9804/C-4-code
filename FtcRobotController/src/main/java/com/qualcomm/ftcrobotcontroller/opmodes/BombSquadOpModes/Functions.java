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

    public double speed = 0.3;                //speed during straight line driving
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

}
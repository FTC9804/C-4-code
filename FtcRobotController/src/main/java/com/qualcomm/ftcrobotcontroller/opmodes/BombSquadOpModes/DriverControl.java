package com.qualcomm.ftcrobotcontroller.opmodes.BombSquadOpModes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by etiennelunetta on 12/2/15.
 * This v3 adds functionality for flippers in front of treads
 */

public class DriverControl extends OpMode{

    //Object declarations

    Servo RightFlipper;
    Servo LeftFlipper;
    Servo BallTilt;
    DcMotor BallLift;
    DcMotor DriveRight;
    DcMotor DriveLeft;
    DcMotor ArmLift;
    DcMotor RightArmExtends;
    DcMotor LeftArmExtends;

    double ballLiftPower = 1;       //DC Motor Power
    double ballLiftGain = 1;
    double ballTiltPower;           //CR Servo direction and rate (0.5 is stopped)
    double ballTiltGain = 0.7;      //needs tuning, twitchy going down, slow coming up
    double drivePowerRight;
    double drivePowerLeft;
    double driveGain = 1;
    double armLiftPower;
    double armLiftGain = 1;
    double armExtendPower;
    double armExtendGain = 1;

    //variables for positioning the flippers. Need to be changed!!

    double flipperOutPositionRight = 1.0;
    double flipperOutPositionLeft = 0.0;            //opposite of the right because it is facing the other direction
    double flipperNotMoving = 0.5;

    @Override
        public void init() {

            //Name the devices

        RightFlipper = hardwareMap.servo.get("s1");         //flippers are not CR servos
        LeftFlipper = hardwareMap.servo.get("s2");

        BallTilt = hardwareMap.servo.get("s3");

        DriveRight = hardwareMap.dcMotor.get("m2");
        DriveLeft = hardwareMap.dcMotor.get("m1");
        BallLift = hardwareMap.dcMotor.get("m3");
        RightArmExtends = hardwareMap.dcMotor.get("m4");
        LeftArmExtends = hardwareMap.dcMotor.get("m5");
        ArmLift = hardwareMap.dcMotor.get("m6");

        ballTiltPower = 0.5;                                 // 0.5 is no motion (CR servo)

        BallTilt.setPosition(ballTiltPower);                 //this sets motion not position
        DriveLeft.setDirection(DcMotor.Direction.REVERSE);   // reverses left motor for uniform drive commands
        RightFlipper.setPosition(0.5);
        LeftFlipper.setPosition(0.5);

        }


        @Override
        public void loop() {

            //Driving Functionality using Gamepad1 (driver)
            //avoid dead zone of +/- 10 percent of joystick range

            if (Math.abs(gamepad1.right_stick_y) > 0.1){

                drivePowerRight = gamepad1.right_stick_y * driveGain;

            } else {

                drivePowerRight = 0;
            }

            if (Math.abs(gamepad1.left_stick_y) > 0.1){

                drivePowerLeft = gamepad1.left_stick_y * driveGain;

            } else {

                drivePowerLeft = 0;
            }

            DriveRight.setPower(drivePowerRight);
            DriveLeft.setPower(drivePowerLeft);


            // Ball Lift Functionality using D-Pad GameController 2 (gunner)
            if(gamepad2.dpad_up){

                BallLift.setPower(-ballLiftPower * ballLiftGain);

            } else if(gamepad2.dpad_down){

                BallLift.setPower(ballLiftPower * ballLiftGain);

            } else {

                BallLift.setPower(0);

            }

            //Need to verify if +1 rotates to right (0 to left)
            //Ball Tilt Functionality using D-Pad GameController 2 (gunner)
            if (gamepad2.dpad_right) {
                // if right dpad is pushed on gamepad2, turn the Tilt to the right
                ballTiltPower = 0.5 - (0.5 * ballTiltGain);
                BallTilt.setPosition(ballTiltPower);

            }else if (gamepad2.dpad_left){
                // if left dpad is pushed on gamepad2, turn the Tilt to the left
                ballTiltPower = 0.5 + (0.5 * ballTiltGain);
                BallTilt.setPosition(ballTiltPower);

            } else {
                //if dpad is not being used, don't tilt
                BallTilt.setPosition(0.5);

            }

            //Arm Functionality using joysticks gamepad2 (gunner)
            //avoid dead zone of +/- 10 percent of joystick range

            if (Math.abs(gamepad2.left_stick_y) > 0.1) {

                armLiftPower = gamepad2.left_stick_y * armLiftGain;

            } else {

                armLiftPower = 0;
            }

            if (Math.abs(gamepad2.right_stick_y) > 0.1) {

                armExtendPower = gamepad2.right_stick_y * armExtendGain;

            } else {

                armExtendPower = 0;
            }

            ArmLift.setPower(armLiftPower);
            RightArmExtends.setPower(armExtendPower);
            LeftArmExtends.setPower(armExtendPower);

            // block/ball flipper functionality


            if (gamepad1.right_bumper) {

                RightFlipper.setPosition(flipperOutPositionRight);
                LeftFlipper.setPosition(flipperOutPositionLeft);

            } else {

                RightFlipper.setPosition(flipperNotMoving);
                LeftFlipper.setPosition(flipperNotMoving);

            }

        }
    }

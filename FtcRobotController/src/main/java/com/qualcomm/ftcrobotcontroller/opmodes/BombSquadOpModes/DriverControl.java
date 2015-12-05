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

    Servo RightFlicker;
    Servo LeftFlicker;
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

    double rightFlickerPosition = 0.5;
    double leftFlickerPosition = 0.5;

    //Not actually gain, just the angle changed every time corresponding button is clicked

    double flickerGain = 0.07;


    double flickerMax = 0.0;
    double flickerMin = 1.0;


    final static double RIGHT_FLICKER_MIN_RANGE = 0.0;
    final static double RIGHT_FLICKER_MAX_RANGE = 1.0;
    final static double LEFT_FLICKER_MIN_RANGE = 0.0;       //Opposite because it is on opposite sides
    final static double LEFT_FLICKER_MAX_RANGE = 1.0;       //Opposite because it is on opposite sides


    @Override
        public void init() {

            //Name the devices

        RightFlicker = hardwareMap.servo.get("s1");         //flippers are not CR servos
        LeftFlicker = hardwareMap.servo.get("s2");

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
        RightFlicker.setPosition(rightFlickerPosition);
        LeftFlicker.setPosition(leftFlickerPosition);

        }


        @Override
        public void loop() {

            if (gamepad1.x) {

                driveGain = 0.5;

            } else if (gamepad1.y) {

                driveGain = 1;

            } else {

                driveGain = 1;

            }

            if (gamepad2.x) {

                ballLiftGain = 0.5;
                ballTiltGain = 0.5;
                armLiftGain = 0.5;
                armExtendGain = 0.5;


            } else if (gamepad2.y) {

                ballLiftGain = 1;
                ballTiltGain = 1;
                armLiftGain = 1;
                armExtendGain = 1;

            } else {

                ballLiftGain = 1;
                ballTiltGain = 1;
                armLiftGain = 1;
                armExtendGain = 1;

            }

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


            // block/ball flipper functionality using gamepad 1 (driver)
            if (gamepad1.right_bumper) {

                rightFlickerPosition -= flickerGain;
                RightFlicker.setPosition(rightFlickerPosition);

            } else if (gamepad1.right_trigger > 0.5) {

                rightFlickerPosition += flickerGain;
                RightFlicker.setPosition(rightFlickerPosition);

            } else {

                // RightFlicker.setPosition(rightFlickerPosition);

            }

            if (gamepad1.left_bumper) {

                leftFlickerPosition += flickerGain;
                LeftFlicker.setPosition(leftFlickerPosition);

            } else if (gamepad1.left_trigger > 0.5) {

                leftFlickerPosition -= flickerGain;
                LeftFlicker.setPosition(leftFlickerPosition);

            } else {

                // LeftFlicker.setPosition(leftFlickerPosition);

            }


            //Clip the positions as to not exceed the required angles
            rightFlickerPosition = Range.clip(rightFlickerPosition, RIGHT_FLICKER_MIN_RANGE, RIGHT_FLICKER_MAX_RANGE);
            leftFlickerPosition = Range.clip(leftFlickerPosition, LEFT_FLICKER_MIN_RANGE, LEFT_FLICKER_MAX_RANGE);

        }
    }

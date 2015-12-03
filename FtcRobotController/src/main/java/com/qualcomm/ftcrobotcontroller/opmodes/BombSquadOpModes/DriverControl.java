package com.qualcomm.ftcrobotcontroller.opmodes.BombSquadOpModes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by etiennelunetta on 12/2/15.
 */
public class DriverControl extends OpMode{

        //Object declarations
        Servo BallTilt;
        DcMotor BallLift;
        DcMotor DriveRight;
        DcMotor DriveLeft;

        double ballLiftPower = 1;       //DC Motor Power
        double ballLiftGain = 1;
        double ballTiltPower;           //CR Servo direction and rate
        double ballTiltGain = 0.7;      //needs tuning, twitchy going down, slow coming up
        double drivePower = 0;
        double driveGain = -1;



        @Override
        public void init() {

            //Name the devices

            BallTilt = hardwareMap.servo.get("s1");
            DriveRight = hardwareMap.dcMotor.get("m1");
            DriveLeft = hardwareMap.dcMotor.get("m2");
            BallLift = hardwareMap.dcMotor.get("m3");

            ballTiltPower = 0.5;  // 0.5 is no motion (CR servo)

            BallTilt.setPosition(ballTiltPower);    //this sets motion not position
            DriveLeft.setDirection(DcMotor.Direction.REVERSE);   // reverses left motor for uniform drive commands

        }


        @Override
        public void loop() {

            // write the values to the motors
            DriveRight.setPower(gamepad1.right_stick_y * driveGain);
            DriveLeft.setPower(gamepad1.left_stick_y * driveGain);

            // Ball Lift Functionality using D-Pad GameController 2 (gunner)
            if(gamepad2.dpad_up){

                BallLift.setPower(ballLiftPower * ballLiftGain);

            } else if(gamepad2.dpad_down){

                BallLift.setPower(-ballLiftPower * ballLiftGain);

            } else {

                BallLift.setPower(0);

            }


            //Ball Tilt Functionality using D-Pad GameController 2 (gunner)
            if (gamepad2.dpad_right) {
                // if right dpad is pushed on gamepad2, turn the Tilt to the right
                ballTiltPower = 0.5 + (0.5 * ballTiltGain);
                BallTilt.setPosition(ballTiltPower);

            }else if (gamepad2.dpad_left){
                // if left dpad is pushed on gamepad2, turn the Tilt to the left
                ballTiltPower = 0.5 - (0.5 * ballTiltGain);
                BallTilt.setPosition(ballTiltPower);

            } else {
                //if dpad is not being used, don't tilt
                BallTilt.setPosition(0.5);

            }
        }
    }

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


        double ballTiltPower;
        double driveGain = -1;
        double ballLiftGain = 1;
        double ballTiltGain = 1;




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

            if(gamepad2.dpad_up){

                BallLift.setPower(ballLiftGain);

            }
            //Ball Dropper Functionality.
            if (gamepad2.a) {
                // if the A button is pushed on gamepad1, turn the
                ballTiltPower = 1;

                BallTilt.setPosition(ballTiltPower * ballTiltGain);

            }else if (gamepad2.y){

                ballTiltPower = -1;

                BallTilt.setPosition(ballTiltPower * ballTiltGain);

            } else if (gamepad2.x) {

                ballTiltPower = 0.5;

                BallTilt.setPosition((ballTiltPower * ballTiltGain));

            } else {

                BallTilt.setPosition(0.5);

            }
        }
    }

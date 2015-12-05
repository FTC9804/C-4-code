package com.qualcomm.ftcrobotcontroller.opmodes.BombSquadOpModes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by etiennelunetta on 12/1/15.
 */

public class AutoR2R extends Functions {
    DcMotor DriveRight;
    DcMotor DriveLeft;
    GyroSensor gyro;


    @Override
    public void runOpMode() throws InterruptedException {

        marshmellow();

        while (opModeIsActive()) {

            ProportionalSteering();

            DriveLeft.setPower(rightPower);
            DriveRight.setPower(leftPower);

            //start degree - 45
            targetHeading -= 45;
            //wait for turn
            if (currentHeading == targetHeading + -5) {
                speed = 1;
            }
            //wait a+c
            sleep(a + c);
            //turn 90 degrees
            speed = 0;
            targetHeading -= 90;
            //wait for turn
            if (currentHeading == targetHeading + -5) {
                speed = 1;
            }
            //wait d
            sleep(d);
            //stop
            speed = 0;
            stop();
        }

    }

}

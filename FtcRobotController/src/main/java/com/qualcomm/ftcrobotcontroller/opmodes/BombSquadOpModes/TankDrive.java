package com.qualcomm.ftcrobotcontroller.opmodes.BombSquadOpModes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by etiennelunetta on 12/5/15.
 */
public class TankDrive extends OpMode {


    DcMotor DriveRight;
    DcMotor DriveLeft;

    public void init() {

        DriveRight = hardwareMap.dcMotor.get("m2");
        DriveLeft = hardwareMap.dcMotor.get("m1");
        DriveLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop() {


        DriveRight.setPower(gamepad1.right_stick_y);
        DriveLeft.setPower(gamepad1.left_stick_y);
    }


}

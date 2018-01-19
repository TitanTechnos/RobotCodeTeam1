package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;
    DcMotor verticalArm = null;
    Servo clawJointOne = null;
    Servo clawJointTwo = null;
    Servo claw = null;

    private DcMotor.RunMode initialMode = null;

    Hardware(DcMotor.RunMode runMode){
        initialMode = runMode;
    }

    void init(HardwareMap hardwareMap){
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        verticalArm = hardwareMap.get(DcMotor.class, "arm");
        clawJointOne = hardwareMap.get(Servo.class, "joint_one");
        clawJointTwo = hardwareMap.get(Servo.class, "joint_two");
        claw = hardwareMap.get(Servo.class, "claw");

        leftDrive.setMode(initialMode);
        rightDrive.setMode(initialMode);
        verticalArm.setMode(initialMode);

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        verticalArm.setDirection(DcMotor.Direction.FORWARD);

        leftDrive.setPower(0);
        rightDrive.setPower(0);
        verticalArm.setPower(0);

    }

}

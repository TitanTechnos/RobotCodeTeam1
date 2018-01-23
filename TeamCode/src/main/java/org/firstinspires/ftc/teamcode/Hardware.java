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
    Servo clawLeft = null;
    Servo clawRight = null;

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
        clawLeft = hardwareMap.get(Servo.class, "claw_left");
        clawRight = hardwareMap.get(Servo.class, "claw_right");

        leftDrive.setMode(initialMode);
        rightDrive.setMode(initialMode);
        verticalArm.setMode(initialMode);

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        verticalArm.setDirection(DcMotor.Direction.FORWARD);

        leftDrive.setPower(0);
        rightDrive.setPower(0);
        verticalArm.setPower(0);

        clawRight.setDirection(Servo.Direction.REVERSE);

    }

    void clawSetPosition(ClawPosition status) {
        clawLeft.setPosition(status.getDouble());
        clawRight.setPosition(status.getDouble());
    }

    enum ClawPosition {
        OPEN(1, "OPEN"), HALF(0.5, "HALF"), CLOSED(0, "CLOSED");

        private final double pos;
        private final String name;

        ClawPosition(double pos, String name) {
            this.pos = pos;
            this.name = name;
        }

        double getDouble() {
            return pos;
        }

        String getName(){
            return name;
        }

        static ClawPosition getPosition(double pos){
            int x = (int) Math.abs(pos * 10);
            switch (x){
                case 0:
                    return CLOSED;
                case 5:
                    return HALF;
                case 10:
                    return OPEN;
                default:
                    return OPEN;
            }
        }
    }

}

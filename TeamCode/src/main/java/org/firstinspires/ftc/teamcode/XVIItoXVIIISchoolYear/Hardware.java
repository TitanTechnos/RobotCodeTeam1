package org.firstinspires.ftc.teamcode.XVIItoXVIIISchoolYear;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class Hardware {
    DcMotor leftDrive = null;
    DcMotor rightDrive = null;
    DcMotor verticalArm = null;
    Servo clawJointOne = null;
    Servo clawJointTwo = null;
    Servo clawLeft = null;
    Servo clawRight = null;
    Servo sensorServo = null;
    ModernRoboticsI2cColorSensor colorSensor = null;
    DistanceSensor distanceSensorOne = null;
    DistanceSensor distanceSensorTwo = null;

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
        sensorServo = hardwareMap.get(Servo.class, "sensor_servo");
        colorSensor = hardwareMap.get(ModernRoboticsI2cColorSensor.class, "sensor_color");
        distanceSensorOne = hardwareMap.get(DistanceSensor.class, "sensor_distance_one");
        distanceSensorOne = hardwareMap.get(DistanceSensor.class, "sensor_distance_two");

        leftDrive.setMode(initialMode);
        rightDrive.setMode(initialMode);
        verticalArm.setMode(initialMode);

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        verticalArm.setDirection(DcMotor.Direction.REVERSE);

        leftDrive.setPower(0);
        rightDrive.setPower(0);
        verticalArm.setPower(0);

        clawLeft.setDirection(Servo.Direction.REVERSE);
        clawRight.setDirection(Servo.Direction.REVERSE);
        clawJointTwo.setDirection(Servo.Direction.REVERSE);

        sensorServo.setDirection(Servo.Direction.REVERSE);
    }

    void clawSetPosition(ClawPosition position) {
        clawLeft.setPosition(position.getDouble());
        clawRight.setPosition(position.getDouble());
    }

    enum ClawPosition {
        OPEN(0, "OPEN"), CLOSED(0.3, "CLOSED");

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
                case 3:
                    return CLOSED;
                case 0:
                    return OPEN;
                default:
                    return OPEN;
            }
        }
    }

}

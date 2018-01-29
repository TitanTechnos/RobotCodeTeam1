package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Color - Blue Team", group = "Autonomous")
//@Disabled
public class ColorSensorAutoRed extends LinearOpMode {

    private Hardware robot = new Hardware(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.sensorServo.setPosition(0.8);

        waitForStart();
        while(opModeIsActive()){

            robot.sensorServo.setPosition(1);

            robot.colorSensor.enableLed(true);

            if(robot.colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)>8 && robot.colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)<12){
                robot.leftDrive.setPower(-0.1);
                robot.rightDrive.setPower(-0.1);
                sleep(250);
            } else if(robot.colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)>1 && robot.colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)<5){
                robot.leftDrive.setPower(0.1);
                robot.rightDrive.setPower(0.1);
                sleep(250);
            } else {
                robot.rightDrive.setPower(0);
                robot.leftDrive.setPower(0);
                sleep(250);
            }
        }

    }
}

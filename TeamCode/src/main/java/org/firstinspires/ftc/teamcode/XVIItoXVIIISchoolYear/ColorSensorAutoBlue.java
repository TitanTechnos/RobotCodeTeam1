package org.firstinspires.ftc.teamcode.XVIItoXVIIISchoolYear;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Color - Blue Team", group = "Autonomous")
@Disabled
class ColorSensorAutoBlue extends LinearOpMode {

    private Hardware robot = new Hardware(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.sensorServo.setPosition(0.1);

        waitForStart();
        while(opModeIsActive()){

            robot.sensorServo.setPosition(0.7);

            robot.colorSensor.enableLed(true);

            if(robot.colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)>8 && robot.colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)<12){
                robot.leftDrive.setPower(0.5);
                robot.rightDrive.setPower(0.5);
                sleep(100);
                robot.sensorServo.setPosition(0.1);
            } else if(robot.colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)>1 && robot.colorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)<5){
                robot.leftDrive.setPower(-0.5);
                robot.rightDrive.setPower(-0.5);
                sleep(100);
                robot.sensorServo.setPosition(0.1);
            } else {
                robot.rightDrive.setPower(0);
                robot.leftDrive.setPower(0);
                sleep(100);
            }

            stop();

        }

    }
}

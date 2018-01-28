package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Color - Red Team", group = "Autonomous")
//@Disabled
public class ColorSensorAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private Hardware robot = new Hardware(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        float hsvValues[] = {0f, 0f, 0f};
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        int cs = 1;

        waitForStart();
        while(opModeIsActive()){

            robot.sensorServo.setPosition(1);

            robot.colorSensor.enableLed(true);

            Color.RGBToHSV(robot.colorSensor.red() * 8, robot.colorSensor.green() * 8, robot.colorSensor.blue() * 8, hsvValues);
            telemetry.addData("Red ", robot.colorSensor.red());
            telemetry.addData("Green ", robot.colorSensor.green());
            telemetry.addData("Blue ", robot.colorSensor.blue());
            telemetry.update();

            if(robot.colorSensor.red() >= 3 && cs == 1){
                robot.leftDrive.setPower(-0.1);
                robot.rightDrive.setPower(-0.1);
                sleep(250);
                cs = 2;
            } else if(robot.colorSensor.blue() >= 3 && cs == 1){
                robot.leftDrive.setPower(0.1);
                robot.rightDrive.setPower(0.1);
                sleep(250);
                cs = 2;
            } else {
                robot.rightDrive.setPower(0);
                robot.leftDrive.setPower(0);
                sleep(500);
                restOfAutonomous();
            }
        }

    }

    private void restOfAutonomous(){
        robot.leftDrive.setPower(0.2);
        robot.rightDrive.setPower(-0.2);
        sleep(250);
        robot.leftDrive.setPower(0.4);
        robot.rightDrive.setPower(0.4);
        sleep(500);
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        stop();
    }
}

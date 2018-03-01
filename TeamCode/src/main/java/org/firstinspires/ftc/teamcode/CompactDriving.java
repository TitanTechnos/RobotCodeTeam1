package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class CompactDriving extends OpMode{

    private ElapsedTime runtime = new ElapsedTime();
    private Hardware robot = new Hardware(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        //Initialize Robot
        robot.init(hardwareMap);
        robot.colorSensor.enableLed(false);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        double leftPower = 0;
        double rightPower = 0;

        if(gamepad1.right_stick_y != 0 && gamepad1.left_stick_x == 0){
            leftPower = gamepad1.right_stick_y;
            rightPower = gamepad1.right_stick_y;
        } else if(gamepad1.left_stick_x != 0 && gamepad1.right_stick_y == 0) {
            if(gamepad1.left_stick_x > 0){
                rightPower = gamepad1.left_stick_x;
                leftPower = -gamepad1.left_stick_x;
            } else {
                rightPower = -gamepad1.left_stick_x;
                leftPower = gamepad1.left_stick_x;
            }
        } else if(gamepad1.left_stick_x != 0){
            leftPower = gamepad1.right_stick_y;
            rightPower = gamepad1.right_stick_y;
            if(gamepad1.left_stick_x > 0){
                double x = -Math.abs(gamepad1.left_stick_x) +1;
                rightPower *= x;
            } else {
                double x = -Math.abs(gamepad1.left_stick_x) +1;
                leftPower *= x;
            }
        }

        robot.leftDrive.setPower(leftPower);
        robot.rightDrive.setPower(rightPower);

        telemetry.addData("Left Wheel Power:", String.valueOf(robot.leftDrive.getPower()));
        telemetry.addData("Right Wheel Power:", String.valueOf(robot.rightDrive.getPower()));
        telemetry.addData("Left Stick X:", String.valueOf(gamepad1.left_stick_x));
        telemetry.addData("Right Stick Y:", String.valueOf(gamepad1.right_stick_y));

    }
}

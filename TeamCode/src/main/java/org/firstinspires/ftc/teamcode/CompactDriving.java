package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Compact Driving", group="TeleOp")
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
        double leftPower = gamepad1.right_stick_y/2;
        double rightPower = gamepad1.right_stick_y/2;
        
        if(gamepad1.right_stick_x != 0) {
            if (gamepad1.right_stick_y == 0) {
                rightPower = gamepad1.right_stick_x/2;
                leftPower = -gamepad1.right_stick_x/2;
            } else {
                double turnMultiplier = mapTurns(gamepad1.right_stick_x);
                if (gamepad1.right_stick_x > 0) {
                    rightPower *= turnMultiplier;
                } else {
                    leftPower *= turnMultiplier;
                }
            }
        }

        robot.leftDrive.setPower(leftPower);
        robot.rightDrive.setPower(rightPower);

        telemetry.addData("Left Wheel Power:", String.valueOf(robot.leftDrive.getPower()));
        telemetry.addData("Right Wheel Power:", String.valueOf(robot.rightDrive.getPower()));
        telemetry.addData("Left Stick X:", String.valueOf(gamepad1.right_stick_x));
        telemetry.addData("Right Stick Y:", String.valueOf(gamepad1.right_stick_y));

    }

    private double mapTurns(double x){
        return (-(Math.abs(x))) + 1;
    }

}

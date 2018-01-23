package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Hardware.ClawPosition.CLOSED;
import static org.firstinspires.ftc.teamcode.Hardware.ClawPosition.HALF;
import static org.firstinspires.ftc.teamcode.Hardware.ClawPosition.OPEN;

@TeleOp(name="TeleOp 17-18", group="TeleOp")
//@Disabled
public class MainOpMode extends OpMode { //TODO: Fix Claw Extension & Joints

    private ElapsedTime runtime = new ElapsedTime();
    private Hardware robot = new Hardware(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    private int speedDenominator = 2; //1 = full speed, 2 = half speed, 4 = quarter speed, etc.
    private boolean first = true;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        //Initialize Robot
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
        robot.clawSetPosition(OPEN);
        robot.clawJointOne.setPosition(0);
        robot.clawJointTwo.setPosition(0);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Variables
        double leftWheelPower;
        double rightWheelPower;
        double clawVerticalPower;

        //Change speed
        if (gamepad1.a) {
            speedDenominator = 2;
        } else if (gamepad1.x) {
            speedDenominator = 1;
        } else if (gamepad1.b) {
            speedDenominator = 4;
        }

        //Calculate wheel power
        leftWheelPower = gamepad1.left_stick_y / speedDenominator;
        rightWheelPower = gamepad1.right_stick_y / speedDenominator;

        //Calculate arm power
        clawVerticalPower = gamepad2.right_stick_y / 2;

        //Switch which joint is being controlled
        if (gamepad2.left_bumper || gamepad2.right_bumper) {
            first = !first;
        }

        //Calculate arm joint movement


        //Claw Extension
        if (gamepad2.b) {
            robot.clawSetPosition(CLOSED);
        } else if (gamepad2.a) {
            robot.clawSetPosition(HALF);
        } else if (gamepad2.x) {
            robot.clawSetPosition(OPEN);
        }


        robot.clawJointOne.setDirection(Servo.Direction.REVERSE);
        robot.clawJointTwo.setDirection(Servo.Direction.REVERSE);
        robot.clawJointOne.setPosition(gamepad2.right_trigger);
        robot.clawJointTwo.setPosition(gamepad2.left_trigger);


        //Set motor power
        robot.leftDrive.setPower(leftWheelPower);
        robot.rightDrive.setPower(rightWheelPower);
        robot.verticalArm.setPower(clawVerticalPower);

        // Show the elapsed game time and component power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addLine();
        telemetry.addData("Wheels", "left (%.2f), right (%.2f)", robot.leftDrive.getPower(), robot.rightDrive.getPower());
        telemetry.addData("Speed Denominator:", String.valueOf(speedDenominator));
        telemetry.addData("Arm", "Power: " + String.valueOf(robot.verticalArm.getPower()));
        telemetry.addLine();
        telemetry.addData("Joint One", "Position: " + String.valueOf(robot.clawJointOne.getPosition()));
        telemetry.addData("Joint Two", "Position: " + String.valueOf(robot.clawJointTwo.getPosition()));
        telemetry.addData("Claw Position:", Hardware.ClawPosition.getPosition(robot.clawRight.getPosition()).getName());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
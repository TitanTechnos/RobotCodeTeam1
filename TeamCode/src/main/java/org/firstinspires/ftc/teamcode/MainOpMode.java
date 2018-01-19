package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleOp 17-18", group = "TeleOp")
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
        robot.claw.setPosition(0);
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
        double jointOnePower;
        double jointTwoPower;

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
        if (gamepad2.right_trigger > 0 && !(gamepad2.left_trigger > 0)) {
            if (first) {
                jointOnePower = gamepad2.right_trigger;
                jointTwoPower = 0;
            } else {
                jointTwoPower = gamepad2.right_trigger;
                jointOnePower = 0;
            }
        } else if (gamepad2.left_trigger > 0 && !(gamepad2.right_trigger > 0)) {
            if (first) {
                jointOnePower = -gamepad2.left_trigger;
                jointTwoPower = 0;
            } else {
                jointTwoPower = -gamepad2.left_trigger;
                jointOnePower = 0;
            }
        } else {
            jointOnePower = 0;
            jointTwoPower = 0;
        }

        //Claw Extension
        if (gamepad2.a) {
            robot.claw.setPosition(0);
        } else if (gamepad2.x) {
            robot.claw.setPosition(0.5);
        } else if (gamepad2.b) {
            robot.claw.setPosition(0.4);
        }

        //Set motor power
        robot.leftDrive.setPower(leftWheelPower);
        robot.rightDrive.setPower(rightWheelPower);
        robot.verticalArm.setPower(clawVerticalPower);

        //Set CRMotor power
        robot.clawJointOne.setPower(jointOnePower);
        robot.clawJointTwo.setPower(jointTwoPower);

        // Show the elapsed game time and component power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addLine();
        telemetry.addData("Wheels", "left (%.2f), right (%.2f)", leftWheelPower, rightWheelPower);
        telemetry.addData("Speed Denominator:", String.valueOf(speedDenominator));
        telemetry.addData("Arm", "Power: " + String.valueOf(clawVerticalPower));
        telemetry.addLine();
        telemetry.addData("Currently Controlling:", (first ? "Joint One" : "Joint Two"));
        telemetry.addData("Joint One", "Power: " + String.valueOf(jointOnePower));
        telemetry.addData("Joint Two", "Power: " + String.valueOf(jointTwoPower));
        telemetry.addLine();
        telemetry.addData("Claw Position:", String.valueOf(robot.claw.getPosition()));
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
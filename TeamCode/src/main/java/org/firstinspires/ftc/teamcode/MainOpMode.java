package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="OpMode", group="Basic Opmode")
//@Disabled
public class MainOpMode extends OpMode { //TODO: Finish Claw Extension
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor verticalArm = null;
    private CRServo clawJointOne = null;
    private CRServo clawJointTwo = null;
    private int speedDenominator = 2; //1 = full speed, 2 = half speed, 4 = quarter speed, etc.
    private boolean first = true;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        verticalArm = hardwareMap.get(DcMotor.class, "arm");
        clawJointOne = hardwareMap.get(CRServo.class, "joint_one");
        clawJointTwo = hardwareMap.get(CRServo.class, "joint_two");

        rightDrive.setDirection(DcMotor.Direction.REVERSE);

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
        if (gamepad1.a){
            speedDenominator = 2;
        } else if (gamepad1.x){
            speedDenominator = 1;
        } else if (gamepad1.b){
            speedDenominator = 4;
        }

        //Calculate wheel power

        leftWheelPower = gamepad1.left_stick_x/speedDenominator;
        rightWheelPower = gamepad1.right_stick_y/speedDenominator;

        //Calculate arm power

        clawVerticalPower = gamepad2.right_stick_y/2;

        //Calculate Claw Power (Joints)

        if(gamepad2.left_bumper || gamepad2.right_bumper){
            first = !first;
        }

        if(gamepad2.right_trigger > 0 && !(gamepad2.left_trigger > 0)){
            if(first){
                jointOnePower = gamepad2.right_trigger;
                jointTwoPower = 0;
            } else {
                jointTwoPower = gamepad2.right_trigger;
                jointOnePower = 0;
            }
        } else if(gamepad2.left_trigger > 0 && !(gamepad2.right_trigger > 0)){
            if(first){
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

        //Set motor power
        leftDrive.setPower(leftWheelPower);
        rightDrive.setPower(rightWheelPower);
        verticalArm.setPower(clawVerticalPower);

        //Set CRMotor power
        clawJointOne.setPower(jointOnePower);
        clawJointTwo.setPower(jointTwoPower);

        // Show the elapsed game time and component power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Wheels", "left (%.2f), right (%.2f)", leftWheelPower, rightWheelPower);
        telemetry.addData("Arm", "Power: " + String.valueOf(clawVerticalPower));
        telemetry.addData("Joint One", "Power: " + String.valueOf(jointOnePower));
        telemetry.addData("Joint Two", "Power: " + String.valueOf(jointTwoPower));
        telemetry.addData("Currently Controlling: ", (first ? "Joint One" : "Joint Two"));
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
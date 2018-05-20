package org.firstinspires.ftc.teamcode.XVIItoXVIIISchoolYear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE;

@TeleOp(name="TeleOp 17-18", group="TeleOp")
@Disabled
class MainOpMode extends OpMode {

    private int songId = 0;
    private String currentSongName = "[NO MUSIC PLAYING]";

    private ElapsedTime runtime = new ElapsedTime();
    private Hardware robot = new Hardware(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    private int speedDenominator = 2; //1 = full speed, 2 = half speed, 4 = quarter speed, etc.
    private boolean first = true;
    private boolean down = false;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");

        //Initialize Robot
        robot.init(hardwareMap);
        robot.colorSensor.enableLed(false);
        Music.initSongs();

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
        robot.clawSetPosition(Hardware.ClawPosition.OPEN);
        robot.clawJointOne.setPosition(0);
        robot.clawJointTwo.setPosition(0);
        robot.sensorServo.setPosition(0.1);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Variables
        float leftWheelPower;
        float rightWheelPower;
        float clawVerticalPower;

        //Change speed
        if (gamepad1.a) {
            speedDenominator = 2;
        } else if (gamepad1.x) {
            speedDenominator = 1;
        }

        //Calculate wheel power
        leftWheelPower = gamepad1.left_stick_y / speedDenominator;
        rightWheelPower = gamepad1.right_stick_y / speedDenominator;

        //Calculate arm power
        clawVerticalPower = gamepad2.right_stick_y / 2;

        //Switch which joint is being controlled
        /*if ((gamepad2.left_bumper || gamepad2.right_bumper) && !down) {
            down = true;
            first = !first;
        } else if (!(gamepad2.left_bumper || gamepad2.right_bumper)) {
            down = false;
        }*/

        //Calculate arm joint movement
        robot.clawJointOne.setDirection(REVERSE);
        robot.clawJointTwo.setDirection(REVERSE);
        float jntOpn = gamepad2.right_trigger/100;
        float jntCls = gamepad2.left_trigger/100;
        float jntOpnTwo = gamepad1.right_trigger/100;
        float jntClsTwo = gamepad1.left_trigger/100;
        double jntOnePos = robot.clawJointOne.getPosition();
        double jntTwoPos = robot.clawJointTwo.getPosition();

        /*if(first){*/
            if (gamepad2.right_trigger > 0 && !(gamepad2.left_trigger > 0)){
                jntOnePos -= jntOpn;
            } else if (gamepad2.left_trigger > 0 && !(gamepad2.right_trigger > 0)){
                jntOnePos += jntCls;
            }
        /*} else {*/
            if (gamepad1.right_trigger > 0 && !(gamepad1.left_trigger > 0)){
                jntTwoPos += jntOpnTwo;
            } else if (gamepad1.left_trigger > 0 && !(gamepad1.right_trigger > 0)){
                jntTwoPos -= jntClsTwo;
            }
        //}

        if(jntTwoPos > 0.476){
            jntTwoPos = 0.476;
        }

        //Claw Extension
        if (gamepad2.b) {
            robot.clawSetPosition(Hardware.ClawPosition.CLOSED);
        } else if (gamepad2.x) {
            robot.clawSetPosition(Hardware.ClawPosition.OPEN);
        }

        //Set joint position
        robot.clawJointOne.setPosition(jntOnePos);
        robot.clawJointTwo.setPosition(jntTwoPos);

        //Set motor power
        robot.leftDrive.setPower(leftWheelPower);
        robot.rightDrive.setPower(rightWheelPower);
        robot.verticalArm.setPower(clawVerticalPower);

        // Show the elapsed game time and component power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addLine();
        telemetry.addData("Wheels", "left (%.2f), right (%.2f)", robot.leftDrive.getPower(), robot.rightDrive.getPower());
        telemetry.addData("Speed Denominator:", String.valueOf(speedDenominator));
        telemetry.addLine();
        telemetry.addData("Arm", "Power: " + String.valueOf(-robot.verticalArm.getPower()));
        telemetry.addData("Currently Controlling:", (first ? "One" : "Two"));
        telemetry.addData("Joint One", "Position: " + String.valueOf(robot.clawJointOne.getPosition()));
        telemetry.addData("Joint Two", "Position: " + String.valueOf(robot.clawJointTwo.getPosition()));
        telemetry.addData("Claw Position:", Hardware.ClawPosition.getPosition(robot.clawRight.getPosition()).getName());
        telemetry.addLine();
        telemetry.addData("Current Song:", currentSongName);

        //Music Player
        if (this.gamepad1.y && !down && !gamepad1.b) {
            down = true;
            Music.stop();
            if(songId >= Music.songs.size()){
                songId = 0;
            }
            Music.start(this.hardwareMap.appContext, Music.songs.get(songId));
            currentSongName = Music.songs.get(songId).getName();
            songId += 1;
        } else if (this.gamepad1.b && !gamepad1.y) {
            Music.stop();
            songId = 0;
            currentSongName = "[NO MUSIC PLAYING]";
            down = false;
        } else {
            down = false;
        }


    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        Music.stop();
    }

}
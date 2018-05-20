/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.XVIItoXVIIISchoolYear;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import static com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE;

@TeleOp(name = "Sensor: REVColorDistance", group = "Sensor")
@Disabled
class SmartPickup extends OpMode {


    Hardware robot = new Hardware(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        robot.clawJointOne.setDirection(REVERSE);
        robot.clawJointTwo.setDirection(REVERSE);
        double distanceOne = robot.distanceSensorOne.getDistance(DistanceUnit.CM);
        double distanceTwo = robot.distanceSensorTwo.getDistance(DistanceUnit.CM);

        if(gamepad1.x){
            robot.clawSetPosition(Hardware.ClawPosition.OPEN);
        }

        if (!(distanceOne >= 20 && distanceTwo >= 20) && !gamepad1.x){
            if(distanceOne > distanceTwo && Math.abs(distanceOne-distanceTwo) >= 2){
                telemetry.addData("Cube Status:", "Cube Spotted. Cube right side is closer to robot.");
                if (robot.clawJointTwo.getPosition() < 0.476) {
                    robot.clawJointTwo.setPosition(robot.clawJointTwo.getPosition() + 0.005);
                    robot.leftDrive.setPower(0);
                    robot.rightDrive.setPower(0);
                } else if (robot.clawJointOne.getPosition() > 0){
                    robot.clawJointOne.setPosition(robot.clawJointOne.getPosition() - 0.005);
                    robot.leftDrive.setPower(0);
                    robot.rightDrive.setPower(0);
                } else {
                    robot.leftDrive.setPower(0.2);
                    robot.rightDrive.setPower(-0.2);
                }
            } else if (distanceOne < distanceTwo && Math.abs(distanceTwo-distanceOne) >= 2){
                telemetry.addData("Cube Status:", "Cube Spotted. Cube left side is closer to robot.");
                if (robot.clawJointTwo.getPosition() > 0) {
                    robot.clawJointTwo.setPosition(robot.clawJointTwo.getPosition() - 0.005);
                    robot.leftDrive.setPower(0);
                    robot.rightDrive.setPower(0);
                } else if (robot.clawJointOne.getPosition() < 1){
                    robot.clawJointOne.setPosition(robot.clawJointOne.getPosition() + 0.005);
                    robot.leftDrive.setPower(0);
                    robot.rightDrive.setPower(0);
                } else {
                    robot.leftDrive.setPower(-0.2);
                    robot.rightDrive.setPower(0.2);
                }
            } else if (Math.abs(distanceTwo-distanceOne) <= 2){
                robot.clawSetPosition(Hardware.ClawPosition.CLOSED);
            }
        } else {
            telemetry.addData("Cube Status:", "No cube in sight");
        }





        telemetry.addData("Distance (Sensor 1):", String.valueOf(distanceOne) + " cm");
        telemetry.addData("Distance (Sensor 2):", String.valueOf(distanceTwo) + " cm");
        telemetry.update();
    }

}

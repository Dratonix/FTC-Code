package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Qualifiers", group="Tournament")

public class Qualifiers extends LinearOpMode 
{
	// Declare OpMode members.
	private ElapsedTime runtime = new ElapsedTime();
	
	private DcMotor front_left;
	private DcMotor front_right;
	private DcMotor back_left;
	private DcMotor back_right;
	
	private DcMotor left_arm;
	private DcMotor right_arm;
	private DcMotor intake;
	private Servo	 servo_clawBase;
	private Servo	 servo_claw;
	private Servo	 servo_drone;
	
	// Set the speed of the motors
	private double speed_select = 0.9;
	private double wheel_speed;
	private double arm_speed = 0.5;
	
	// Set the position of the servo motors
	private double clawbase_position = 0.2;
	private double claw_position = 0.5;
	
	
	@Override
	public void runOpMode()
	{
		telemetry.addData("Status", "Initialized");
		telemetry.update();
		front_left	= hardwareMap.get(DcMotor.class, "front_left");	// Expansion Hub Port 0
		back_left	= hardwareMap.get(DcMotor.class, "back_left");		// Expansion Hub Port 1
		front_right = hardwareMap.get(DcMotor.class, "front_right"); // Control Hub Port 0
		back_right = hardwareMap.get(DcMotor.class, "back_right");	 // Control Hub Port 1
		
		front_left.setDirection(DcMotor.Direction.REVERSE);
		front_right.setDirection(DcMotor.Direction.FORWARD);
		back_left.setDirection(DcMotor.Direction.REVERSE);
		back_right.setDirection(DcMotor.Direction.FORWARD);
		front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
			
		front_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		front_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		back_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		back_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
// =====================================================================
		left_arm = hardwareMap.get(DcMotor.class, "left_arm");	 // Expansion Hub Port 2
		right_arm = hardwareMap.get(DcMotor.class, "right_arm"); // Control Hub Port 2
		intake = hardwareMap.get(DcMotor.class, "intake");	
	// Expansion Hub Port 3
		
		left_arm.setDirection(DcMotor.Direction.REVERSE);
		right_arm.setDirection(DcMotor.Direction.FORWARD);
		intake.setDirection(DcMotor.Direction.FORWARD);
		left_arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		right_arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		left_arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		right_arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
			
		left_arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		right_arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
			
// =====================================================================
			
		servo_clawBase = hardwareMap.get(Servo.class, "servo_clawBase"); // Control Hub servo Port 0
		servo_claw = hardwareMap.get(Servo.class, "servo_claw");		 // Control Hub servo Port 1
		servo_drone = hardwareMap.get(Servo.class, "servo_drone");		 // Expansion Hub servo Port 0

		servo_clawBase.setPosition(clawbase_position);
		servo_claw.setPosition(claw_position);
		servo_drone.setPosition(0.5);
		
		waitForStart();
		runtime.reset();
			
		while (opModeIsActive()) 
		{
			double drive = -gamepad1.left_stick_y;
			double turn	=	gamepad1.right_stick_x;
			if (turn != 0)
				wheel_speed = 0.7;
			else
				wheel_speed = speed_select;
				
			// Calculate the power of the wheels		
			double frontLeftPower	= Range.clip(drive + turn, -wheel_speed, wheel_speed);
			double frontRightPower = Range.clip(drive - turn, -wheel_speed, wheel_speed);
			double backLeftPower	 = Range.clip(drive + turn, -wheel_speed, wheel_speed);
			double backRightPower	= Range.clip(drive - turn, -wheel_speed, wheel_speed);
			// Send calculated power to wheels
			front_left.setPower(frontLeftPower);
			front_right.setPower(frontRightPower);
			back_left.setPower(backLeftPower);
			back_right.setPower(backRightPower);
			// Set power of both the arms
			
					
					double arm_move = -gamepad2.left_stick_y;
					double position = left_arm.getCurrentPosition();
					if ((arm_move > 0 && position < 2323) || (arm_move < 0))
					{
						
							arm_speed = 1.2;
							
							right_arm.setPower(Range.clip(arm_move, -arm_speed, arm_speed));
							left_arm.setPower (Range.clip(arm_move, -arm_speed, arm_speed));
							
					}
					else
					{
							right_arm.setPower(0);
							left_arm.setPower(0);
					}
					
					// Set power of claw base servo
					double clawbase_move = -gamepad2.right_stick_y;
					if (clawbase_move != 0)
					{
							if (clawbase_move > 0 && clawbase_position < 1.0)
									 clawbase_position += 0.07;
							else if (clawbase_move < 0 && clawbase_position > 0)
									clawbase_position -= 0.07;
							servo_clawBase.setPosition(clawbase_position);
							sleep(100);
					}
					
					// Set the power of claw servo.
					if (gamepad2.left_trigger != 0 || gamepad2.right_trigger != 0)
					{
							if (gamepad2.left_trigger != 0)
							{
									if (claw_position < 1.0)
											claw_position += 0.08;
							}
							else if (gamepad2.right_trigger != 0)
							{
									if (claw_position > 0.0)
											claw_position -= 0.08;
							}
							servo_claw.setPosition(claw_position);
							sleep(100);
					}
						
						// Set power of intake motor.
						if (gamepad2.left_bumper)
								intake.setPower(1);
						else if (gamepad2.right_bumper)
								intake.setPower(-1);
						else
								intake.setPower(0);
								
						if(gamepad2.cross){
							int p1 = left_arm.getCurrentPosition();
							int p2 = right_arm.getCurrentPosition();
							//left_arm.setTargetPosition(p1);
							//right_arm.setTargetPosition(p2);
							left_arm.setPower(-1);
							right_arm.setPower(-1);
							sleep(100000);
							telemetry.addData("Right Arm Current Position: %d", p2);
							telemetry.addData("Left Arm Current Position: %d", p1);
						}

						// Launch the drone
						if(gamepad1.triangle)
								servo_drone.setPosition(0);
						
						//Set angle to drop pixel
						if(gamepad2.triangle)
																servo_clawBase.setPosition(0.453);
												
						// Set to beginning position for drone servo.
						if(gamepad1.cross)
								servo_drone.setPosition(0.5);

						// Toggle the speed for the wheel motors.
						if(gamepad1.left_bumper || gamepad1.right_bumper)
						{
								if (speed_select == 0.2)
										speed_select = 0.9;
								else
										speed_select = 0.2;
								sleep(500);
						}
								
						if(gamepad1.dpad_right == true){
							
							front_left.setPower(10);
							front_right.setPower(-10);
							back_left.setPower(-10);
							back_right.setPower(10);
						}
						else if(gamepad1.dpad_left == true){
							front_left.setPower(-10);
							front_right.setPower(10);
							back_left.setPower(10);
							back_right.setPower(-10);
						}
						else{
							front_left.setPower(0);
							front_right.setPower(0);
							back_left.setPower(0);
							back_right.setPower(0);
						}
					
						telemetry.addData("Status", "Run Time: " + runtime.toString());
						telemetry.addData("Gamepad1", "Left-x (%.2f), Right-x (%.2f)", gamepad1.left_stick_x, gamepad1.right_stick_x);
						telemetry.addData("Gamepad1", "Left-y (%.2f), Right-y (%.2f)", gamepad1.left_stick_y, gamepad1.right_stick_y);						
						telemetry.addData("Wheel Power: ", wheel_speed);
						telemetry.addData("Arm Position: ", position);
						telemetry.addData("Arm Move:", arm_move);
						telemetry.addData("Claw Position:", claw_position);
						telemetry.addData("Clawbase Position:", clawbase_position);
						telemetry.addData("Gamepad2", "Left-x (%.2f), Right-x (%.2f)", gamepad2.left_stick_x, gamepad2.right_stick_x);
						telemetry.addData("Gamepad2", "Left-y (%.2f), Right-y (%.2f)", gamepad2.left_stick_y, gamepad2.right_stick_y);						
						telemetry.update();
				}
		}
}

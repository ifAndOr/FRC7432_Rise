package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.*;

public class Robot extends TimedRobot {
	/* Master Talons for arcade drive */
	WPI_TalonSRX _frontLeftMotor = new WPI_TalonSRX(1);
	WPI_TalonSRX _frontRightMotor = new WPI_TalonSRX(2);

	/* Follower Talons + Victors for six motor drives */
	WPI_VictorSPX _leftSlave1 = new WPI_VictorSPX(3);
	WPI_VictorSPX _rightSlave1 = new WPI_VictorSPX(7);
	WPI_VictorSPX _leftSlave2 = new WPI_VictorSPX(4);
	WPI_VictorSPX _rightSlave2 = new WPI_VictorSPX(17);

    /* Construct drivetrain by providing master motor controllers */
	DifferentialDrive _drive = new DifferentialDrive(_frontLeftMotor, _frontRightMotor);

    /* Joystick for control */
	Joystick _joy = new Joystick(0);

	//This function is called once at the beginning during operator control
	public void teleopInit(){
		// Factory Default all hardware to prevent unexpected behaviour
		_frontLeftMotor.configFactoryDefault();
		_frontRightMotor.configFactoryDefault();
		_leftSlave1.configFactoryDefault();
		_leftSlave2.configFactoryDefault();
		_rightSlave1.configFactoryDefault();
		_rightSlave2.configFactoryDefault();

		// VictorSPX's follow respective sides motor controller
		_leftSlave1.follow(_frontLeftMotor);
		_leftSlave2.follow(_frontLeftMotor);
		_rightSlave1.follow(_frontRightMotor);
		_rightSlave2.follow(_frontRightMotor);

		// Motor Inversion
		_frontLeftMotor.setInverted(false); // <<<<<< Adjust this until robot drives forward when stick is forward
		_frontRightMotor.setInverted(true); // <<<<<< Adjust this until robot drives forward when stick is forward
		_leftSlave1.setInverted(InvertType.FollowMaster);
		_leftSlave2.setInverted(InvertType.FollowMaster);
		_rightSlave1.setInverted(InvertType.FollowMaster);
		_rightSlave2.setInverted(InvertType.FollowMaster);

		// Right side inversion to match left in direction
		_drive.setRightSideInverted(false);
	}

	// Periodically called function for operation
	public void teleopPeriodic() {
        // Input processing
		double forward = -1.0 * _joy.getY();	// Sign this so forward is positive
		double turn = +1.0 * _joy.getZ();       // Sign this so right is positive
        
        // Input threshold
		if (Math.abs(forward) < 0.10) {
			forward = 0;
		}
		if (Math.abs(turn) < 0.10) {
			turn = 0;
		}
        
		// Assign values to motorcontrollers
		_drive.arcadeDrive(forward, turn);
	}
}

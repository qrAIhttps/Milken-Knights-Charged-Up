// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CAMERA.AprilTags;
import frc.robot.CAMERA.UltraSonic;
import frc.robot.MECHANISMS.ARM.Arm;
import frc.robot.MECHANISMS.ARM.Claw;
import frc.robot.MECHANISMS.Intake;
import frc.robot.MECHANISMS.MkSwerveTrain;
import frc.robot.MISC.Constants.CONTROLLERS.DriveInput;
import frc.robot.MISC.Constants.MKARM;
import frc.robot.MISC.Constants.MKBABY;
import frc.robot.MISC.Constants.MKTELE;
import frc.robot.MISC.Constants.PIGEON;
import frc.robot.MISC.Lights;
import frc.robot.MISC.MathFormulas;
import frc.robot.MISC.Odometry;
import frc.robot.MISC.pigeon;
import java.util.Map;

import javax.xml.validation.SchemaFactory;

/** Robot stuff in here */
public class SupaStruct {

  private XboxController xbox = new XboxController(0);
  private XboxController xboxOP = new XboxController(1);
  private double fwd,
      fwdSignum,
      str,
      strSignum,
      leftjoy,
      rcw,
      rcwX,
      rcwY,
      inverseTanAngleOG,
      inverseTanAngleDrive,
      povValue,
      pigRotate = 0,
      lightMode = 0,
      sliderArm;
  private MkSwerveTrain train = MkSwerveTrain.getInstance();
  private Odometry odo = Odometry.getInstance();

  private boolean resetpig,
      dpadup,
      dpaddown,
      toggleClimbUpOn,
      toggleClimbUpPressed,
      toggleClimbDownOn,
      toggleClimbDownPressed,
      resetTurn,
      resetDrive,
      xbutton,
      ybutton,
      rbbutton,
      bbutton,
      rbbutton2,
      lbbutton2,
      lbbutton,
      abutton,
      ltrigger,
      rtrigger,
      xbutton2,
      ybutton2,
      abutton2,
      bbutton2,
      dpadup2,
      dpaddown2,
      ltrigger2,
      rtrigger2,
      dpadleft2,
      dpadright2,
      toggleLightsPressed = false,
      pov, /* povToggled, */
      itsreal = false,
      toggleArmUpOn,
      toggleArmMidOn,
      toggleArmUpPressed,
      toggleArmMidPressed,
      toggleHPArmOn,
      toggleHPArmPressed;
  private boolean isRCWrunningWithpig = false;
  private AprilTags april = AprilTags.getInstance();
  private Intake intake = Intake.getInstance();
  private Timer turntesttimer = new Timer();
  private Claw claw = Claw.getInstance();
  private Arm arm = Arm.getInstance();
  private UltraSonic ultra = UltraSonic.getInstance();
  private Lights mLights = Lights.getInstance();
  private Timer turntesttimertwo = new Timer();
  private double count = 0;

  private ShuffleboardTab tab = Shuffleboard.getTab("slida");
  private GenericEntry slidaa;
  private double x, y, rot;

  public static SupaStruct getInstance() {
    return InstanceHolder.mInstance;
  }

  public void initTele() {
    try {
      slidaa =
          tab.add("slidaa", 1)
              .withWidget(BuiltInWidgets.kNumberSlider)
              .withProperties(Map.of("min", -1, "max", 1))
              .getEntry();

    } catch (Exception e) {
      System.out.println(
          "fuck you if the slider is there just have an in built system to say 'oh look its FUCKING THERE' i swear to god"
              + e);
    }
    pigRotate = pigeon.getInstance().getPigYaw();
    arm.setArmToCanCoder();
  }

  public void updateTele() {
    // --------------------------------------------------------------------//
    // UPDATES
    // --------------------------------------------------------------------//
    train.updateSwerve();
    april.updateApril();
    ultra.updateUltra();
    april.aprilSmartDashboard();
    arm.updateSmartdashboard();
    ultra.ultraSmartDashboard();
    x = april.getAxis("x");
    y = april.getAxis("y");
    // yaw = april.getAxis("yaw");
    rot = april.getAxis("r");

    // --------------------------------------------------------------------//
    // VARIABLES
    // --------------------------------------------------------------------//
    updateLightsToggle();
    updateHPArmToggle();
    fwd = (xbox.getRawAxis(DriveInput.fwd) - 0.1) / (1 - 0.1);
    fwdSignum = Math.signum(fwd) * -1;
    str = (xbox.getRawAxis(DriveInput.str) - 0.1) / (1 - 0.1);
    strSignum = Math.signum(str) * -1;
    rcwY = (xbox.getRawAxis(DriveInput.rcwY) - 0.1) / (1 - 0.1);
    // Todo see if making this x breaks it
    rcwX = (xbox.getRawAxis(DriveInput.rcwX) - 0.1) / (1 - 0.1);
    rcw = rcwX;
    // DRIVER
    xbutton = xbox.getXButton();
    abutton = xbox.getAButtonPressed();
    rbbutton = xbox.getRightBumper();
    ybutton = xbox.getYButton();
    bbutton = xbox.getBButtonPressed();
    lbbutton = xbox.getLeftBumper();
    dpaddown = xbox.getPOV() == 180;
    dpadup = xbox.getPOV() == 0;
    ltrigger = Math.abs(xbox.getRawAxis(2)) > 0.1;
    rtrigger = Math.abs(xbox.getRawAxis(3)) > 0.1;
    // OP
    xbutton2 = xboxOP.getXButton();
    abutton2 = xboxOP.getAButtonPressed();
    rbbutton2 = xboxOP.getRightBumper();
    ybutton2 = xboxOP.getYButton();
    bbutton2 = xboxOP.getBButtonPressed();
    lbbutton2 = xboxOP.getLeftBumper();
    dpaddown2 = xboxOP.getPOV() == 180;
    dpadup2 = xboxOP.getPOV() == 0;
    dpadleft2 = xboxOP.getPOV() == 90;
    dpadright2 = xboxOP.getPOV() == 270;
    ltrigger2 = Math.abs(xboxOP.getRawAxis(2)) > 0.1;
    rtrigger2 = Math.abs(xboxOP.getRawAxis(3)) > 0.1;

    pov = xbox.getPOV() != -1;

    // sliderArm = slidaa.getDouble(0);
    // SmartDashboard.putNumber("dzrh", sliderArm);

    // i dont remember how i got this lol

    inverseTanAngleDrive =
        ((((((Math.toDegrees(Math.atan(fwd / str)) + 360)) + (MathFormulas.signumV4(str))) % 360)
                    - MathFormulas.signumAngleEdition(str, fwd))
                + 360)
            % 360;

    // --------------------------------------------------------------------//
    // PIGEON RESET
    // --------------------------------------------------------------------//

    if (ybutton) {
      pigeon.getInstance().reset();
      povValue = 00;
      inverseTanAngleOG = 0;
      train.vars.avgDistTest = 0;
      train.vars.avgDistInches = 0;
      train.startDrive();
    }

    if(xbox.getPOV() != -1)
    {
      rcw = train.moveToAngy(xbox.getPOV());
    }

    // --------------------------------------------------------------------//
    // DRIVE STATEMENTS
    // --------------------------------------------------------------------//

    if (Math.abs(xbox.getRawAxis(DriveInput.rcwY)) < 0.1
        && Math.abs(xbox.getRawAxis(DriveInput.rcwX)) < 0.1){ //&& xbox.getPOV() == -1) {
      rcw = 0;
    }

    if (Math.abs(xbox.getRawAxis(DriveInput.rcwY)) < 0.1) {
      rcwY = 0;
    }
    if (Math.abs(xbox.getRawAxis(DriveInput.rcwX)) < 0.1) {
      rcwX = 0;
    }

    if (Math.abs(xbox.getRawAxis(DriveInput.fwd)) < 0.1) {
      fwd = 0;
    }
    if (Math.abs(xbox.getRawAxis(DriveInput.str)) < 0.1) {
      str = 0;
    }

    if (xbutton) {
      april.alignToTag();

    } else if ((fwd != 0 || str != 0 || rcw != 0)) {//&& (xbox.getPOV() != -1)) { // +,-,+
      train.etherSwerve(
          fwd ,
          str ,
          -rcw,
          ControlMode.PercentOutput); // +,-,+
      // TODO why is it +,+,- and not +,-,+
      // train.setModuleDrive(ControlMode.PercentOutput, 1, 1, 1, 1);
      // train.setModuleTurn(0, 0, 0, 0);
    } else if (pigeon.getInstance().getPigPitch() > PIGEON.pitchThreshold) {
      fwd = train.antiTip()[1];
      str = train.antiTip()[0];
      train.etherSwerve(fwd, -str, 0, ControlMode.PercentOutput);
    } else {
      train.stopEverything();
    }
    SmartDashboard.putNumber("rcw", xbox.getPOV());
  SmartDashboard.putNumber("movetoangy", train.moveToAngy(xbox.getPOV()));
    // --------------------------------------------------------------------//
    // INTAKE
    // --------------------------------------------------------------------//
    if (rbbutton) {
      intake.rollerSet(-1);

    } else if (lbbutton) {
      intake.rollerSet(1);

    } else {
      intake.rollerSet(0);
    }
    if (abutton) {
      intake.toggle();
    }
    // --------------------------------------------------------------------//
    // CLAW
    // --------------------------------------------------------------------//
    if (abutton2) {
      claw.extend();
    }
    if (bbutton2) {
      claw.retract();
    }

    // --------------------------------------------------------------------//
    // ARM
    // --------------------------------------------------------------------//

    /*if (bbutton2 && arm.getArmDegrees() < MKARM.maxDegreePosition) {
      arm.pidArm(88);
    } else*/ if (ybutton2 && arm.getArmDegrees() < MKARM.maxDegreePosition) {
      arm.pidArm(90);
    } else if (!rbbutton2 && lbbutton2) {
      arm.moveArm(-0.16, -0.16);
    } else if (rbbutton2 && !lbbutton2) {
      arm.moveArm(0.16, 0.16);
    } else if (toggleHPArmOn && arm.getArmDegrees() < MKARM.maxDegreePosition) {
      arm.pidArm(88);
      // arm.pidTelescope(8000);
      // claw.extend();
    } else {
      arm.moveArm(0, 0);
    }

    // --------------------------------------------------------------------//
    // TELESCOPE
    // --------------------------------------------------------------------//
    if (rtrigger2 && !ltrigger2 && arm.getTelescope() > MKTELE.minNativePositionTelescope) {
      toggleClimbDownPressed = false;
      toggleClimbDownOn = false;
      toggleClimbUpPressed = false;
      toggleClimbUpOn = false;
      arm.pidTelescope(0);
      // arm.pidTelescope(MKTELE.minNativePositionTelescope);
    } else if (!rtrigger2 && ltrigger2 && arm.getTelescope() < MKTELE.maxNativePositionTelescope) {
      toggleClimbDownPressed = false;
      toggleClimbDownOn = false;
      toggleClimbUpPressed = false;
      toggleClimbUpOn = false;
      arm.pidTelescope(8000);
      // arm.pidTelescope(MKTELE.maxNativePositionTelescope);
    } else if (toggleClimbDownOn
        && !toggleClimbUpOn
        && arm.getTelescope() > MKTELE.minNativePositionTelescope) {
      arm.moveTele(-.6);
      // arm.pidTelescope(MKTELE.minNativePositionTelescope);
    } else if (!toggleClimbDownOn
        && toggleClimbUpOn
        && arm.getTelescope() < MKTELE.maxNativePositionTelescope) {
      arm.moveTele(.6);
      // arm.pidTelescope(MKTELE.maxNativePositionTelescope);
    } else if (xboxOP.getRawButton(7)) {
      arm.setTelescope(MKTELE.minNativePositionTelescope / MKTELE.greerRatio);
    } else if (xboxOP.getRawButton(8)) {
      arm.setTelescope(MKTELE.maxNativePositionTelescope / MKTELE.greerRatio);
    } else if (dpaddown && !dpadup && arm.getTelescope() > MKTELE.minNativePositionTelescope) {
      //arm.pidTelescope(0);
    } else if (!dpaddown && dpadup && arm.getTelescope() < MKTELE.maxNativePositionTelescope) {
      //arm.pidTelescope(8000);
    }
    // else if(toggleHPArmOn && arm.getArmDegrees() < MKARM.maxDegreePosition)
    // {

    // }
    else {

      arm.moveTele(0);
    }

    if (xboxOP.getBButton()
        || xboxOP.getAButton()
        || ybutton2
        || rbbutton2
        || lbbutton2
        || rtrigger2
        || ltrigger2) {
      toggleHPArmOn = false;
    }

    SmartDashboard.putNumber("Armangle", arm.getArmDegrees());

    if (dpadleft2) {
      odo.reset();
    }
    if (dpadright2) {
      odo.resetToPose2D(x, y, rot);
    }

    if (lightMode == 0) {
      mLights.off();
      SmartDashboard.putString("color", "none");
    } else if (lightMode == 1) {
      mLights.CONE();
      SmartDashboard.putString("color", "cone");
    } else if (lightMode == 2) {
      mLights.CUBE();
      SmartDashboard.putString("color", "cube");
    }
  }
  // SmartDashboard.putNumber("yaw", yaw);

  // --------------------------------------------------------------------//
  // LEDS
  // --------------------------------------------------------------------//

  public void updateLightsToggle() {
    if (bbutton) {
      if (!toggleLightsPressed) {
        lightMode++;
        lightMode = lightMode % 3;

        toggleLightsPressed = true;
      }
    } else {
      toggleLightsPressed = false;
    }
  }

  public void updateHPArmToggle() {
    if (xbutton2) {
      if (!toggleHPArmPressed) {
        toggleHPArmOn = !toggleHPArmOn;

        toggleHPArmPressed = true;
      }
    } else {
      toggleHPArmPressed = false;
    }
  }

  public void updateArmUpToggle() {
    if (ybutton2) {
      if (!toggleArmUpPressed) {
        toggleArmUpOn = !toggleArmUpOn;

        toggleArmUpPressed = true;
      }
    } else {
      toggleArmUpPressed = false;
    }
  }
  /*
  public void updateArmMidToggle() {
    if (bbutton2) {
      if (!toggleArmMidPressed) {
        toggleHPArmOn = !toggleHPArmOn;

        toggleArmMidPressed = true;
      }
    } else {
      toggleArmMidPressed = false;
    }
  }*/

  public void teleopDisabled() {
    resetpig = false;
    resetDrive = false;
    xbutton = false;
    ybutton = false;
    pov = false;
    itsreal = false;
    turntesttimer.stop();
    turntesttimer.reset();
    try {
      slidaa.close();
    } catch (Exception e) {
      System.out.println("end");
    }
    // WHY DO I HAVE TO MANUALLY CLOSE IT JUST REMEMBER IT EXISTS AHHHHHHHHHHH
  }

  public void initTest() {
    train.vars.avgDistTest = 0;
    turntesttimer.stop();
    turntesttimer.reset();
    turntesttimertwo.stop();
    turntesttimertwo.reset();
    train.startTrain();
  }

  // measured over predicted * predicted
  public void updateTest() {
    double fwd = 0;
    double rcw = 0;
    if (xbox.getAButtonPressed()) {
      turntesttimer.start();
    }
    if (turntesttimer.get() > 0.00000000000000001 && turntesttimer.get() < 5) {
      fwd = 0.3;
    }
    if (xbox.getRawAxis(4) > 0.1
        && (turntesttimer.get() > 0.00000000000000001 && turntesttimer.get() < 5)) {
      rcw = 0.5;
      count++;
    }

    if (fwd == 0.3 || rcw == 0.5) {
      train.etherSwerve(fwd, 0, rcw, ControlMode.PercentOutput);
      train.etherRCWFinder(fwd, 0, 0);
    } else {
      train.stopEverything();
    }

    // SmartDashboard.putNumber("count", count);
    // SmartDashboard.putNumber(
    //     "meastopredictratio", train.vars.avgDistInches / train.vars.avgDistTest);
    // SmartDashboard.putNumber("delta", train.vars.avgDistTest);
  }

  private static class InstanceHolder {
    private static final SupaStruct mInstance = new SupaStruct();
  }
}

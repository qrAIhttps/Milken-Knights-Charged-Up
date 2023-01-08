package frc.robot;
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AUTO.DISTANGLE;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SwrvTest extends SequentialCommandGroup {
  /** Creates a new SwrvTest. */
  public SwrvTest() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      deadline(new EtherTurnCommand(90 + 90).withTimeout(5)),
      deadline(new Turn(0)).withTimeout(1),
      deadline(new EtherAutoCommand(DISTANGLE.distanceA, DISTANGLE.lengthB, DISTANGLE.distance, ((DISTANGLE.angle)), 90)).withTimeout(6),
      deadline(new Turn(0)).withTimeout(1),
      deadline(new EtherTurnCommand(-90 + 90).withTimeout(5)),
      deadline(new Turn(0)).withTimeout(1),
      deadline(new EtherAutoCommand(DISTANGLE.distanceA, DISTANGLE.lengthB, DISTANGLE.distance, ((DISTANGLE.angle)), -90)).withTimeout(6),
      deadline(new Turn(0)).withTimeout(1),
      deadline(new EtherStraightCommand(80, 0, -0.3, 90).withTimeout(6)));
  }
}

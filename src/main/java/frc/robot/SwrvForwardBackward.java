// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AUTO.DISTANGLE;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SwrvForwardBackward extends SequentialCommandGroup {
  /** Creates a new Swrv. */
  public SwrvForwardBackward() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
    deadline(new Turn(0).withTimeout(1)), //forward curve
    deadline(new EtherAutoCommand(DISTANGLE.distanceA, DISTANGLE.lengthB, DISTANGLE.distance, ((DISTANGLE.angle)),  DISTANGLE.headinguno)).withTimeout(6),
    deadline(new Turn(0)).withTimeout(1), // backward curve
    deadline(new EtherAutoCommand(DISTANGLE.distanceA, DISTANGLE.lengthB, DISTANGLE.distance, ((DISTANGLE.angle)),     DISTANGLE.headingdos)).withTimeout(6),
    deadline(new Turn(0)).withTimeout(1),

    deadline(new EtherAutoCommand(DISTANGLE.distanceA, DISTANGLE.lengthB, DISTANGLE.distance, ((DISTANGLE.angle)),     DISTANGLE.headingtres)).withTimeout(6),
    deadline(new Turn(0)).withTimeout(1),
    deadline(new EtherAutoCommand(DISTANGLE.distanceA, DISTANGLE.lengthB, DISTANGLE.distance, ((DISTANGLE.angle)),     DISTANGLE.headingquad)).withTimeout(6),
    deadline(new Turn(0)).withTimeout(1),

    deadline(new EtherAutoCommand(DISTANGLE.distanceA, DISTANGLE.lengthB, DISTANGLE.distance, ((DISTANGLE.angle)),     DISTANGLE.headingsinco)).withTimeout(6),
    deadline(new Turn(0)).withTimeout(1),
    deadline(new EtherAutoCommand(DISTANGLE.distanceA, DISTANGLE.lengthB, DISTANGLE.distance, ((DISTANGLE.angle)),     DISTANGLE.headingsix)).withTimeout(6),
    deadline(new Turn(0)).withTimeout(1),

    deadline(new EtherAutoCommand(DISTANGLE.distanceA, DISTANGLE.lengthB, DISTANGLE.distance, ((DISTANGLE.angle)),     DISTANGLE.headingsev)).withTimeout(6),
    deadline(new Turn(0)).withTimeout(1),
    deadline(new EtherAutoCommand(DISTANGLE.distanceA, DISTANGLE.lengthB, DISTANGLE.distance, ((DISTANGLE.angle)),     DISTANGLE.headingocto)).withTimeout(6),
    deadline(new Turn(0)).withTimeout(1));
  }
}
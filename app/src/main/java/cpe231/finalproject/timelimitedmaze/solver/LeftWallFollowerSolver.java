package cpe231.finalproject.timelimitedmaze.solver;

import cpe231.finalproject.timelimitedmaze.utils.Coordinate;
import cpe231.finalproject.timelimitedmaze.utils.Maze;
import java.util.ArrayList;
import java.util.List;

public final class LeftWallFollowerSolver extends MazeSolver {

  private static final int STEP_MULTIPLIER = 10;

  @Override
  protected List<Coordinate> executeSolve(Maze maze) {
    Coordinate current = maze.getStart();
    Direction direction = findInitialDirection(maze, current);
    List<Coordinate> path = new ArrayList<>();
    path.add(current);

    if (current.equals(maze.getGoal())) {
      return path;
    }

    int maxSteps = Math.max(maze.getWidth() * maze.getHeight() * STEP_MULTIPLIER, 1);

    for (int step = 0; step < maxSteps; step++) {
      Direction left = direction.left();
      Coordinate leftCoordinate = move(current, left);
      Coordinate forwardCoordinate = move(current, direction);
      Direction rightDirection = direction.right();
      Coordinate rightCoordinate = move(current, rightDirection);

      if (isWalkable(maze, leftCoordinate)) {
        direction = left;
        current = leftCoordinate;
        path.add(current);
      } else if (isWalkable(maze, forwardCoordinate)) {
        current = forwardCoordinate;
        path.add(current);
      } else if (isWalkable(maze, rightCoordinate)) {
        direction = rightDirection;
        current = rightCoordinate;
        path.add(current);
      } else {
        Direction backwardsDirection = direction.opposite();
        Coordinate backwards = move(current, backwardsDirection);
        if (!isWalkable(maze, backwards)) {
          throw new MazeSolvingException("Solver is trapped and cannot move");
        }
        direction = backwardsDirection;
        current = backwards;
        path.add(current);
      }

      if (current.equals(maze.getGoal())) {
        return path;
      }
    }

    throw new MazeSolvingException("Left wall follower failed to reach the goal within step limit");
  }

  private Direction findInitialDirection(Maze maze, Coordinate start) {
    for (Direction direction : Direction.values()) {
      if (isWalkable(maze, move(start, direction))) {
        return direction;
      }
    }
    throw new MazeSolvingException("Starting position is enclosed by walls");
  }
}

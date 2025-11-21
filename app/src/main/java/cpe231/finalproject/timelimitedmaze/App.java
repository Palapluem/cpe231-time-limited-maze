package cpe231.finalproject.timelimitedmaze;

import cpe231.finalproject.timelimitedmaze.solver.LeftWallFollowerSolver;
import cpe231.finalproject.timelimitedmaze.solver.MazeSolver;
import cpe231.finalproject.timelimitedmaze.solver.MazeSolvingException;
import cpe231.finalproject.timelimitedmaze.solver.SolverResult;
import cpe231.finalproject.timelimitedmaze.utils.Maze;
import cpe231.finalproject.timelimitedmaze.utils.MazeStore;

public final class App {
  private App() {
  }

  public static void main(String[] args) {
    Maze maze = MazeStore.getMaze("m15_15.txt");
    System.out.println(maze);

    MazeSolver leftWallFollowerSolver = new LeftWallFollowerSolver();
    try {
      SolverResult result = leftWallFollowerSolver.solve(maze);
      System.out.printf("Path length: %d%n", result.path().size());
      System.out.printf("Total cost: %d%n", result.totalCost());
      result.path().forEach(System.out::println);
    } catch (MazeSolvingException exception) {
      System.err.println("Failed to solve maze: " + exception.getMessage());
    }
  }
}

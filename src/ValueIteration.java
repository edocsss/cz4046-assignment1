import model.Action;
import model.Grid;
import model.GridWorld;
import util.Constants;
import util.DrawingUtil;
import util.FileUtil;
import util.Helper;

import java.util.ArrayList;

public class ValueIteration {
    private static GridWorld gridWorld;
    private static ArrayList<GridWorld> history;

    public static void main(String[] args) {
        // Init
        gridWorld = new GridWorld();
        history = new ArrayList<>();

        // Check grids
        gridWorld.drawGridWorld();

        // Perform value iteration
        GridWorld finalGridWorld = doValueIteration();

        // Find best action for each grid
        Action[][] bestActions = findBestAction(finalGridWorld);

        // Draw best action for each grid
        DrawingUtil.drawBestActionPerGrid(bestActions, finalGridWorld.getGrids());

        // Draw converged utility for each grid
        DrawingUtil.drawUtilityPerGrid(finalGridWorld.getGrids());

        // Store utility history to file
        FileUtil.writeUtilityHistoryToFile(history, "valueiteration_1.csv");
    }

    private static GridWorld doValueIteration() {
        for (int iter = 0; iter < Constants.MAX_ITERATION; iter++) {
            System.out.println("Iteration: " + iter);

            // Add previous grid world to history list first
            history.add(new GridWorld(gridWorld));

            // Helper variables
            double utility;
            Grid[][] grids = gridWorld.getGrids();
            Grid[][] prevGrids = history.get(iter).getGrids();

            // Calculate new utility for each state
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
                    if (grids[row][col].isWall()) {
                        continue;
                    }

                    utility = Helper.calcGridUtility(prevGrids, row, col);
                    grids[row][col].setUtility(utility);
                    System.out.println("(" + col + ", " + row + "): " + grids[row][col].getUtility());
                }
            }

            // Terminate iteration if the difference between previous and current utilities are negligible
            if (Helper.hasConverged(history.get(iter), gridWorld)) {
                System.out.println("Utility has converged! Breaking out..");
                break;
            }

            System.out.println();
        }

        return history.get(history.size() - 1);
    }

    private static Action[][] findBestAction(GridWorld gridWorld) {
        Action[][] bestActions = new Action[Constants.GRID_HEIGHT][Constants.GRID_WIDTH];
        Grid[][] grids = gridWorld.getGrids();

        // For each grid, find the best action
        for (int col = 0; col < Constants.GRID_WIDTH; col++) {
            for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
                if (grids[row][col].isWall()) {
                    continue;
                }

                bestActions[row][col] = Helper.getBestAction(grids, row, col);
             }
        }

        return bestActions;
    }
}

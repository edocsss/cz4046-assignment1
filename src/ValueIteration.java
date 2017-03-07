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

    /**
     * Perform value iteration algorithm
     * @return  the GridWorld object with the final utility approximation
     */
    private static GridWorld doValueIteration() {
        int iter = 0;
        double delta;

        do {
            // Update iterator
            iter++;

            // Init delta
            delta = 0.00;

            // Add previous grid world to history list first
            history.add(new GridWorld(gridWorld));

            // Helper variables
            double utility, prevUtility;
            Grid[][] grids = gridWorld.getGrids();
            Grid[][] prevGrids = history.get(iter - 1).getGrids();

            // Calculate new utility for each state
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
                    if (grids[row][col].isWall()) {
                        continue;
                    }

                    prevUtility = grids[row][col].getUtility();
                    utility = Helper.calcMaxGridUtility(prevGrids, row, col);
                    grids[row][col].setUtility(utility);

                    // Update delta for convergence criteria
                    if (Math.abs(utility - prevUtility) > delta) {
                        delta = Math.abs(utility - prevUtility);
                    }
                }
            }
        } while (delta >= Constants.CONVERGENCE_EPSILON * (1 - Constants.DISCOUNT) / Constants.DISCOUNT);

        System.out.println("Iteration: " + iter);
        history.add(new GridWorld(gridWorld));
        return gridWorld;
    }

    /**
     * Find the best action for each state
     * @param gridWorld     the GridWorld object which contains the final utility approximation
     * @return              the best actions for each state
     */
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

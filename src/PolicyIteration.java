import model.Action;
import model.Grid;
import model.GridWorld;
import util.Constants;
import util.DrawingUtil;
import util.FileUtil;
import util.Helper;

import java.util.ArrayList;

public class PolicyIteration {
    private static GridWorld gridWorld;
    private static ArrayList<GridWorld> history;

    public static void main(String[] args) {
        // Init
        gridWorld = new GridWorld();
        history = new ArrayList<>();

        // Check grids
        gridWorld.drawGridWorld();

        // Perform value iteration
        GridWorld finalGridWorld = doPolicyIteration();

        // Extract best actions
        Action[][] bestActions = extractBestActions(finalGridWorld);

        // Draw best action for each grid
        DrawingUtil.drawBestActionPerGrid(bestActions, finalGridWorld.getGrids());

        // Draw converged utility for each grid
        DrawingUtil.drawUtilityPerGrid(finalGridWorld.getGrids());

        // Store utility history to file
        FileUtil.writeUtilityHistoryToFile(history, "policyiteration_1.csv");
    }

    private static GridWorld doPolicyIteration() {
        int iter = 0;
        boolean unchanged;
        Grid[][] grids = gridWorld.getGrids();
        Action bestAction, curAction;

        do {
            // Iterator update
            iter++;

            // Update history
            history.add(new GridWorld(gridWorld));

            // Estimate the utility for each state
            doPolicyEvaluation();

            // Track whether any policy has been chanced
            unchanged = true;

            // Update policy for each state
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
                    if (grids[row][col].isWall()) {
                        continue;
                    }

                    // Using the updated state utility values, find the best action
                    curAction = grids[row][col].getBestAction();
                    bestAction = Helper.getBestAction(grids, row, col);

                    // Check if it has converged
                    if (curAction != bestAction) {
                        grids[row][col].setBestAction(bestAction);
                        unchanged = false;
                    }
                }
            }
        } while (!unchanged);

        System.out.println("Number of Iterations: " + iter);
        history.add(new GridWorld(gridWorld));
        return gridWorld;
    }

    private static void doPolicyEvaluation() {
        GridWorld gw = new GridWorld(gridWorld);
        Grid[][] gwGrids = gw.getGrids();
        Grid[][] grids = gridWorld.getGrids();

        double utility;
        for (int k = 0; k < Constants.K; k++) {
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
                    utility = Helper.calcGridUtilityFixedAction(gwGrids, row, col);
                    grids[row][col].setUtility(utility);
                }
            }

            gw = new GridWorld(gridWorld);
            gwGrids = gw.getGrids();
        }
    }

    private static Action[][] extractBestActions(GridWorld gridWorld) {
        Action[][] bestActions = new Action[Constants.GRID_HEIGHT][Constants.GRID_WIDTH];
        Grid[][] grids = gridWorld.getGrids();

        // For each grid, find the best action
        for (int col = 0; col < Constants.GRID_WIDTH; col++) {
            for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
                if (grids[row][col].isWall()) {
                    continue;
                }

                bestActions[row][col] = grids[row][col].getBestAction();
            }
        }

        return bestActions;
    }
}

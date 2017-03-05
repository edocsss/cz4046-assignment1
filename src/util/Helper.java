package util;

import model.Action;
import model.Grid;
import model.GridWorld;
import model.Tuple;

import java.util.ArrayList;
import java.util.Collections;

public class Helper {
    /**
     * Check whether the utilities of the current GridWorld has converged
     * This is done by calculating the total utility difference of each state
     * and compare it with a small value (epsilon)
     */
    public static boolean hasConverged(GridWorld prevGridWorld, GridWorld curGridWorld) {
        double totalDiff = 0.0;
        Grid[][] prevGrids = prevGridWorld.getGrids();
        Grid[][] curGrids = curGridWorld.getGrids();

        for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                totalDiff += Math.abs(curGrids[row][col].getUtility() - prevGrids[row][col].getUtility());
            }
        }

        return totalDiff <= Constants.CONVERGENCE_EPSILON;
    }

    /**
     * Given a grid/state of the converged grids, find the best action to take
     */
    public static Action getBestAction(Grid[][] grids, int row, int col) {
        ArrayList<Tuple<Double, Action>> results = new ArrayList<>();
        results.add(new Tuple<>(calcUpUtility(grids, row, col), Action.UP));
        results.add(new Tuple<>(calcDownUtility(grids, row, col), Action.DOWN));
        results.add(new Tuple<>(calcLeftUtility(grids, row, col), Action.LEFT));
        results.add(new Tuple<>(calcRightUtility(grids, row, col), Action.RIGHT));

        Action bestAction = results.get(0).getItem2();
        double bestUtility = -1.00;

        for (Tuple<Double, Action> t: results) {
            if (t.getItem1() > bestUtility) {
                bestUtility = t.getItem1();
                bestAction = t.getItem2();
            }
        }

        return bestAction;
    }

    /**
     * Calculate the utility for every possible action
     */
    public static double calcGridUtility(Grid[][] prevGrids, int row, int col) {
        ArrayList<Double> utilities = new ArrayList<>();
        utilities.add(calcUpUtility(prevGrids, row, col));
        utilities.add(calcDownUtility(prevGrids, row, col));
        utilities.add(calcLeftUtility(prevGrids, row, col));
        utilities.add(calcRightUtility(prevGrids, row, col));
        return prevGrids[row][col].getReward() + Constants.DISCOUNT * Collections.max(utilities);
    }

    /**
     * Calculate the utility of going up
     */
    private static double calcUpUtility(Grid[][] prevGrids, int row, int col) {
        double utility = 0;
        utility += Constants.PROB_INTENT * getUpUtility(prevGrids, row, col);
        utility += Constants.PROB_CW * getRightUtility(prevGrids, row, col);
        utility += Constants.PROB_CCW * getLeftUtility(prevGrids, row, col);
        return utility;
    }

    /**
     * Calculate the utility of going down
     */
    private static double calcDownUtility(Grid[][] prevGrids, int row, int col) {
        double utility = 0;
        utility += Constants.PROB_INTENT * getDownUtility(prevGrids, row, col);
        utility += Constants.PROB_CW * getLeftUtility(prevGrids, row, col);
        utility += Constants.PROB_CCW * getRightUtility(prevGrids, row, col);
        return utility;
    }

    /**
     * Calculate the utility of going left
     */
    private static double calcLeftUtility(Grid[][] prevGrids, int row, int col) {
        double utility = 0;
        utility += Constants.PROB_INTENT * getLeftUtility(prevGrids, row, col);
        utility += Constants.PROB_CW * getUpUtility(prevGrids, row, col);
        utility += Constants.PROB_CCW * getDownUtility(prevGrids, row, col);
        return utility;
    }

    /**
     * Calculate the utility of going right
     */
    private static double calcRightUtility(Grid[][] prevGrids, int row, int col) {
        double utility = 0;
        utility += Constants.PROB_INTENT * getRightUtility(prevGrids, row, col);
        utility += Constants.PROB_CW * getDownUtility(prevGrids, row, col);
        utility += Constants.PROB_CCW * getUpUtility(prevGrids, row, col);
        return utility;
    }

    /**
     * Get the utility of the state above the current state
     * If it fails to go up because there is a wall, the current state's utility will be returned
     */
    private static double getUpUtility(Grid[][] prevGrids, int row, int col) {
        return (row - 1 >= 0) && !prevGrids[row - 1][col].isWall() ?
                prevGrids[row - 1][col].getUtility() : prevGrids[row][col].getUtility();
    }

    /**
     * Get the utility of the state below the current state
     * If it fails to go down because there is a wall, the current state's utility will be returned
     */
    private static double getDownUtility(Grid[][] prevGrids, int row, int col) {
        return (row + 1 < Constants.GRID_HEIGHT) && !prevGrids[row + 1][col].isWall() ?
                prevGrids[row + 1][col].getUtility() : prevGrids[row][col].getUtility();
    }

    /**
     * Get the utility of the state to the left of the current state
     * If it fails to go left because there is a wall, the current state's utility will be returned
     */
    private static double getLeftUtility(Grid[][] prevGrids, int row, int col) {
        return (col - 1 >= 0) && !prevGrids[row][col - 1].isWall() ?
                prevGrids[row][col - 1].getUtility() : prevGrids[row][col].getUtility();
    }

    /**
     * Get the utility of the state to the right of the current state
     * If it fails to go right because there is a wall, the current state's utility will be returned
     */
    private static double getRightUtility(Grid[][] prevGrids, int row, int col) {
        return (col + 1 < Constants.GRID_WIDTH) && !prevGrids[row][col + 1].isWall() ?
                prevGrids[row][col + 1].getUtility() : prevGrids[row][col].getUtility();
    }
}

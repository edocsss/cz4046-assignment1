package util;

import model.Action;
import model.Grid;

public class DrawingUtil {
    /**
     * Print out best action for each grid
     * @param actions   list of best actions for each grid
     * @param grids     the grid world's converged grids
     */
    public static void drawBestActionPerGrid(Action[][] actions, Grid[][] grids) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nActions:\n");

        for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                if (grids[row][col].isWall()) {
                    sb.append(String.format("%3s", "W"));
                } else {
                    sb.append(String.format("%3s", actions[row][col].getSymbol()));
                }
            }

            sb.append("\n");
        }

        sb.append("\n\n");
        System.out.println(sb.toString());
    }

    /**
     * Print out final utility value for each grid
     * @param grids     the grid world's converged grids
     */
    public static void drawUtilityPerGrid(Grid[][] grids) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nUtilities:\n");

        for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                if (grids[row][col].isWall()) {
                    sb.append(String.format("%7s   ", "W"));
                } else {
                    sb.append(String.format("%-2.4f   ", grids[row][col].getUtility()));
                }
            }

            sb.append("\n");
        }

        sb.append("\n\n");
        System.out.println(sb.toString());
    }
}

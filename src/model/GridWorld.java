package model;

import util.Constants;

/**
 * Stores representations of the map
 */
public class GridWorld {
    private Grid[][] grids;

    /**
     * Default constructor
     */
    public GridWorld() {
        this.grids = new Grid[Constants.GRID_HEIGHT][Constants.GRID_WIDTH];
        this.constructGridWorld();
    }

    /**
     * Copy constructor
     * @param gw    another GridWorld object to copy
     */
    public GridWorld(GridWorld gw) {
        this.grids = new Grid[Constants.GRID_HEIGHT][Constants.GRID_WIDTH];
        for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                this.grids[row][col] = new Grid(gw.getGrids()[row][col]);
            }
        }
    }

    /**
     * Construct the GridWorld map representation
     */
    private void constructGridWorld() {
        for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                Tuple<Integer, Integer> cur = new Tuple<>(row, col);
                Grid grid;

                if (Constants.BROWN_GRID_INDICES.contains(cur)) {
                    grid = new Grid(row, col, Constants.BROWN_REWARD, false);
                } else if (Constants.GREEN_GRID_INDICES.contains(cur)) {
                    grid = new Grid(row, col, Constants.GREEN_REWARD, false);
                } else if (Constants.WALL_INDICES.contains(cur)) {
                    grid = new Grid(row, col, 0.00, true);
                } else {
                    grid = new Grid(row, col, Constants.WHITE_REWARD, false);
                }

                this.grids[row][col] = grid;
            }
        }
    }

    /**
     * Draw the GridWorld map to the console
     */
    public void drawGridWorld() {
        StringBuilder sb = new StringBuilder();
        sb.append("GridWorld:\n");

        for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                if (this.grids[row][col].isWall()) {
                    sb.append(String.format("%8s", "WALL"));
                } else {
                    sb.append(String.format("%8s", this.grids[row][col].getReward()));
                }
            }

            sb.append('\n');
        }

        sb.append("\n\n");
        System.out.println(sb.toString());
    }

    public Grid[][] getGrids() {
        return grids;
    }
}

package model;

/**
 * Store things related to a single state (or grid in the Grid World problem)
 */
public class Grid {
    private int x;
    private int y;
    private double reward;
    private double utility;
    private boolean isWall;
    private Action bestAction;

    /**
     * Main constructor
     * @param x         X position of the grid in the Grid World
     * @param y         Y position of the grid in the Grid World
     * @param reward    the grid's reward value
     * @param isWall    whether the grid is a wall
     */
    public Grid(int x, int y, double reward, boolean isWall) {
        this.x = x;
        this.y = y;
        this.reward = reward;
        this.isWall = isWall;
        this.utility = 0.00;
        this.bestAction = Action.UP; // Default action
    }

    /**
     * Copy constructor
     * @param g     another Grid object to copy
     */
    public Grid(Grid g) {
        this.x = g.getX();
        this.y = g.getY();
        this.reward = g.getReward();
        this.isWall = g.isWall();
        this.utility = g.getUtility();
        this.bestAction = g.getBestAction();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getReward() {
        return reward;
    }

    public double getUtility() {
        return utility;
    }

    public void setUtility(double utility) {
        this.utility = utility;
    }

    public boolean isWall() {
        return isWall;
    }

    public Action getBestAction() {
        return bestAction;
    }

    public void setBestAction(Action bestAction) {
        this.bestAction = bestAction;
    }
}

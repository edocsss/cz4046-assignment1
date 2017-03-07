package util;

import model.Tuple;

import java.util.Arrays;
import java.util.List;

public class Constants {
    // Grid size
    public static int GRID_WIDTH = 6;
    public static int GRID_HEIGHT = 6;

    // Grid rewards
    public static double WHITE_REWARD = -0.04;
    public static double GREEN_REWARD = 1.00;
    public static double BROWN_REWARD = -1.00;

    // Outcome probabilities
    public static double PROB_INTENT = 0.8;
    public static double PROB_CW = 0.1;
    public static double PROB_CCW = 0.1;

    // Position of green grids
    public static List<Tuple<Integer, Integer>> GREEN_GRID_INDICES = Arrays.asList(
            new Tuple<>(0, 0),
            new Tuple<>(0, 2),
            new Tuple<>(0, 5),
            new Tuple<>(1, 3),
            new Tuple<>(2, 4),
            new Tuple<>(3, 5)
    );

    // Position of brown grids
    public static List<Tuple> BROWN_GRID_INDICES = Arrays.asList(
            new Tuple<>(1, 1),
            new Tuple<>(1, 5),
            new Tuple<>(2, 2),
            new Tuple<>(3, 3),
            new Tuple<>(4, 4)
    );

    // Position of walls
    public static List<Tuple> WALL_INDICES = Arrays.asList(
            new Tuple<>(0, 1),
            new Tuple<>(1, 4),
            new Tuple<>(4, 1),
            new Tuple<>(4, 2),
            new Tuple<>(4, 3)
    );

    // Agent starting position
    public static Tuple<Integer, Integer> START_INDEX = new Tuple<Integer, Integer>(3, 2);

    // Discount factor
    public static double DISCOUNT = 0.99;

    // Convergence epsilon criteria
    public static double CONVERGENCE_EPSILON = 1e-1; // Rmax = 1.00, c = 0.1

    // Policy iteration evaluation stage --> number of iterations for estimating the new utilities
    public static int K = 10;
}

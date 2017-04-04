package util;

import model.GridWorld;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtil {
    /**
     * Write out the utility value history for each grid
     * @param history       contains the history of GridWorld objects created during the iterations
     * @param fileName      file name of the output file
     */
    public static void writeUtilityHistoryToFile(ArrayList<GridWorld> history, String fileName) {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < Constants.GRID_HEIGHT; row++) {
            for (int col = 0; col < Constants.GRID_WIDTH; col++) {
                sb.append(String.format("(%1d;%1d)", row, col));

                for (GridWorld gw: history) {
                    sb.append(",");
                    sb.append(gw.getGrids()[row][col].getUtility());
                }

                sb.append("\n");
            }
        }

        writeToFile(sb.toString(), fileName);
    }

    /**
     * Write arbitrary content to file
     * @param content       file content
     * @param fileName      file name of the output file
     */
    public static void writeToFile(String content, String fileName) {
        try {
            FileWriter fw = new FileWriter(new File(fileName), false);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

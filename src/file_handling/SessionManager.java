package file_handling;

import core_classes.Layer;
import core_components.TableOfContents;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by isaac on 19/12/17.
 * An object used to save & load sessions. Sessions are groups of active layers. A session file should end in ".gmcm"
 * and store a layer name on each line, then the R G and B values, separated by semicolons.
 */
public class SessionManager {

    /**
     * String array representing the names of currently active layers in the table of contents.
     */
    List<Layer> currentActiveLayers;

    /**
     * Constructor for SessionManager. Builds the currentActiveLayers array and the currentActiveLayerColors arrays.
     * @param tableOfContents the table of contents from the main frame.
     */
    public SessionManager(TableOfContents tableOfContents) {

        currentActiveLayers = tableOfContents.layerList;

    }

    /**
     * Saves the current session to a file.
     * @param sessionPath String representing path to file to which to save the session.
     */
    public void saveCurrentSession(String sessionPath) {

        try {

            // Initialize a BufferedWriter and write each layer name to a new line therein, then save & close.
            BufferedWriter sessionWriter = new BufferedWriter(new FileWriter(sessionPath));
            for (int i=0; i<currentActiveLayers.size(); i++) {
                sessionWriter.write(currentActiveLayers.get(i).getLayerName() + ";");
                sessionWriter.write(currentActiveLayers.get(i).getLayerColor().getRed() + ";");
                sessionWriter.write(currentActiveLayers.get(i).getLayerColor().getGreen() + ";");
                sessionWriter.write(currentActiveLayers.get(i).getLayerColor().getBlue() + "\n");
            }
            sessionWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Opens a session from a saved file.
     * @param sessionPath String representing path to file where the session is stored.
     * @return ArrayList of layer name Strings as well as their Color components in string form.
     */
    public List<String[]> openSession(String sessionPath) {

        // Initialize our list of layer names.
        List<String[]> layerList = new ArrayList<>(0);

        try {
            // Read all the names from the file and add them to the returned layer list.
            BufferedReader sessionReader = new BufferedReader(new FileReader(sessionPath));
            String line;

            while ((line = sessionReader.readLine()) != null) {
                layerList.add(line.split(";"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return layerList;

    }

}

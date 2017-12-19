package file_handling;

import core_components.TableOfContents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by isaac on 19/12/17.
 * An object used to save & load sessions. Sessions are groups of active layers. A session file should end in ".gmcm"
 * and store a layer name on each line.
 */
public class SessionManager {

    /**
     * String array representing the names of currently active layers in the table of contents.
     */
    String[] currentActiveLayers;

    /**
     * Constructor for SessionManager. Builds the currentActiveLayers array.
     * @param tableOfContents the table of contents from the main frame.
     */
    public SessionManager(TableOfContents tableOfContents) {

        currentActiveLayers = tableOfContents.getListOfLayersInString();

    }

    /**
     * Saves the current session to a file.
     * @param sessionPath String representing path to file to which to save the session.
     */
    public void saveCurrentSession(String sessionPath) {

        try {

            // Initialize a BufferedWriter and write each layer name to a new line therein, then save & close.
            BufferedWriter sessionWriter = new BufferedWriter(new FileWriter(sessionPath));
            for (int i=0; i<currentActiveLayers.length; i++) {
                sessionWriter.write(currentActiveLayers[i] + "\n");
            }
            sessionWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Opens a session from a saved file.
     * @param sessionPath String representing path to file where the session is stored.
     * @return ArrayList of layer name Strings.
     */
    public ArrayList<String> openSession(String sessionPath) {

        // Initialize our list of layer names.
        ArrayList<String> layerList = new ArrayList<>(0);

        try {
            // Read all the names from the file and add them to the returned layer list.
            BufferedReader sessionReader = new BufferedReader(new FileReader(sessionPath));
            String line;
            while ((line = sessionReader.readLine()) != null) {
                layerList.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return layerList;

    }

}

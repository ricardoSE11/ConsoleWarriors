/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author ss
 */
public class TextFileManager {
    
    private final String path_to_logs;
    private BufferedWriter writer = null;
    
    public TextFileManager(){
        path_to_logs = "src/Logs/";
    }
    
    public void writeToFile(String text, String file_name){
        try {
            File logFile = new File(path_to_logs + file_name);
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }
}

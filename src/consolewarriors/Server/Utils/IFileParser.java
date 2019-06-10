/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Utils;

import java.io.File;

/**
 *
 * @author rshum
 */
public interface IFileParser {
 
    public void writeToFile(Object object);
    
    public Object readFromFile(File file);
    
    public Object readFromFile(String filePath);
    
}

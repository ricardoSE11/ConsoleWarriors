/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolewarriors.Server.Utils;

import Utils.JsonParser;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ss
 */
public class StatsParser extends JsonParser{

    private final String path_to_stats_file;
    
    public StatsParser(){
        super();
        path_to_stats_file = "src/Stats/stats.json";
    }
    
    public Stats getStatsFromFile(){
        return (Stats) parseJson(path_to_stats_file);
    }
    
    @Override
    public Object parseJson(String path_to_file) {
        try{
            br = new BufferedReader(new FileReader(path_to_file));
            Stats result = gson.fromJson(br, Stats.class);
            return result;
        }catch(FileNotFoundException e){
            return null;
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
}
    }

    public void saveConfigToFile(Stats stats){
        writeObjectToFile(stats, path_to_stats_file);
    }

    @Override
    public void writeObjectToFile(Object object, String path_to_file) {
        Stats result = (Stats) object;
        gson = new GsonBuilder().setPrettyPrinting().create();
        String strJson = gson.toJson(result);
        FileWriter writer = null;
        try {
            writer = new FileWriter(path_to_file);
            writer.write(strJson);
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null) {
                try {
                    writer.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

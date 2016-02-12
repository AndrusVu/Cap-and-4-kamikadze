package Auxiliary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReplaysPointsCollector {
    public static Map<String, Integer> getActionsPoints(File[] replayFiles){
        Map<String, Integer> scoresMap = new HashMap();
        for(File file : replayFiles){
            BufferedReader reader = null;
            try {
                 reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e){
                System.out.println(String.format("File %s not found\n", e.getMessage()));
                continue;
            }
            int linesNumber = 0;
            try {
                while (reader.readLine() != null) linesNumber++;
            } catch (IOException e){
                System.out.println(String.format("File $s read error\n", e.getMessage()));
                continue;
            }
            scoresMap.put(file.getName(), linesNumber);
        }
        return scoresMap;
    }
}

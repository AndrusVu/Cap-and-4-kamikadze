package Auxiliary;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Map;

/**
 * Contains replay read write file logic.
 */
public abstract class ReplayFilesLoader {
    /**
     * Returns last replay index.
     * @return last replay index.
     */
    public static int getLastReplayIndex(){
        File[] listOfFiles = getReplayFiles();
        int[] replaysIndices = getReplayFilesIndices(listOfFiles);
        if(replaysIndices.length > 0) {
            Arrays.sort(replaysIndices);
            return replaysIndices[replaysIndices.length - 1];
        }
        return 0;
    }

    /**
     * Extracts indices from replays files names.
     * @param listOfFiles replay files array.
     * @return replay files indices.
     */
    public static int[] getReplayFilesIndices(File[] listOfFiles) {
        int[] replaysIndices = new int[listOfFiles.length];
        for(int i = 0; i < replaysIndices.length; i++){
            String[] tokens = listOfFiles[i].getName().split("_");
            replaysIndices[i] = Integer.parseInt(tokens[1]);
        }
        return replaysIndices;
    }

    /**
     * Find all replay files in Replays directory.
     * @return founded replay files array.
     */
    public static File[] getReplayFiles() {
        File folder = new File("Replays\\");
        return folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.matches("Replay_[0-9]+_\\.repl"))
                    return true;
                return false;
            }
        });
    }

    /**
     * Read replay file content.
     * @param replayFileName file name without path.
     * @return replay file content.
     */
    public static String readReplayFile(String replayFileName) {
        String replayFileContent;
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader("Replays\\" + replayFileName));
            String readed = null;
            while((readed = reader.readLine()) != null) {
                builder.append(readed);
                builder.append("\r\n");
            }
            replayFileContent = builder.toString();
        }catch (IOException e){
            System.out.println(String.format("Replay file read error\n"));
            return null;
        }
        return replayFileContent;
    }

    /**
     * Read last replay file content.
     * @return last replay file content.
     */
    public static String getLastReplayFileContent(){
        String lastReplayFileName = String.format("Replay_%d_.repl", getLastReplayIndex());
        String replayFileContent = readReplayFile(lastReplayFileName);
        return replayFileContent;
    }

    public static String getBestReplayFileContent(){
        File[] replayFiles = ReplayFilesLoader.getReplayFiles();
        Map<String, Integer> unsortedPoints = ReplaysPointsCollector.getActionsPoints(replayFiles);

        ISortPerformanceCounter javaSort = new JavaSortPerformanceCounter(unsortedPoints);
        Map<String, Integer> javaSorted = javaSort.sort();

        String bestGameFileName = javaSorted.entrySet().iterator().next().getKey();
        return ReplayFilesLoader.readReplayFile(bestGameFileName);
    }

    /**
     * Save new replay.
     * @param content serialized actions list.
     * @see Recorder
     */
    public static void saveNewReplay(String content){
        int lastIndex = ReplayFilesLoader.getLastReplayIndex();
        try {
            Path replayFilePath = Paths.get(String.format("Replays\\Replay_%d_.repl", lastIndex + 1));
            Files.write(replayFilePath, content.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error while trying write records to" + e.getMessage());
        }
    }
}

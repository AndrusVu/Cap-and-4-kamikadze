package Auxiliary;


import Models.Vector2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.*;

/**
 * Implements recording logic. Record users and game actions.
 */
public class Recorder {
    private List<String> records;
    private long prevRecordTime;
    private Timeline replayTimeline;

    public Recorder(){
        records = new ArrayList<>();
        replayTimeline = new Timeline();
    }

    /**
     * Call every time when new record session starts.
     */
    public void startRecord(){
        prevRecordTime = Calendar.getInstance().getTimeInMillis();
    }

    /**
    * Stops replay Timeline, which stop executing recorded actions.
    */
    public void stopReplay(){
        replayTimeline.stop();
    }

    /**
     * Record mouse move action.
     * @param coords mouse move point position.
     */
    public synchronized void recordMouseMove(Vector2 coords){
        makeCoordRecord("MOUSE_MOVED\t%d\t%.2f\t%.2f\r\n", coords);
    }

    /**
     * Record mouse click action.
     * @param coords mouse click position.
     */
    public synchronized void recordMouseClick(Vector2 coords){
        makeCoordRecord("MOUSE_CLICKED\t%d\t%.2f\t%.2f%n", coords);
    }

    /**
     * Record mob creation event.
     * @param coords mobs coords.
     */
    public synchronized void recordMobCreated(Vector2 coords){
        makeCoordRecord("MOB_CREATED\t%d\t%.2f\t%.2f%n", coords);
    }

    /**
     * Record game finish event.
     * @param state game finish state.
     */
    public synchronized void recordGameFinished(boolean state){
        records.add(String.format(Locale.US, "GAME_FINISHED\t0\t%b%n", state));
    }

    /**
     * Record pressed key.
     * @param key pressed key code.
     */
    public synchronized void recordKey(KeyCode key){
        long nowTime = Calendar.getInstance().getTimeInMillis();
        records.add(String.format(Locale.US, "KEY_PRESSED\t%d\t%s%n",
                nowTime - prevRecordTime, key.getName()));
        prevRecordTime = nowTime;
    }

    private void makeCoordRecord(String formatStr, Vector2 coords){
        long nowTime = Calendar.getInstance().getTimeInMillis();
        records.add(String.format(Locale.US, formatStr,
                nowTime - prevRecordTime, coords.getX(), coords.getY()));
        prevRecordTime = nowTime;
    }

    /**
     * Make from recorded actions list one String ready for storing or transferring.
     * @return storing-ready actions list.
     */
    public String serialize(){
        String finalCommands = new String();
        for(String cmd : records){
            finalCommands += cmd;
        }
        return finalCommands;
    }

    /**
     * deserialize method make from previously serialized method inner actions list.
     * @param commands
     */
    public void deserialize(String commands) {
        String[] splitedCommands = commands.split("\\r\\n");
        records.clear();
        for (String cmdStr : splitedCommands) {
            records.add(cmdStr);
        }
    }

    /**
     * Initialize with sequence of recorded actions and start Timeline object.
     * @param executorsMap map of actions names and those executors.
     */
    public void startReplay(Map<String, RecordExecutor> executorsMap){
        long cummulativeTime = 0;
        for(int i = 0; i < records.size(); i++){
            cummulativeTime = addCommandAtReplay(executorsMap, cummulativeTime, i);
        }
        replayTimeline.play();
    }

    private long addCommandAtReplay(Map<String, RecordExecutor> exectutersMap, long cummulativeTime, int i) {
        CommandContainer cmd = parseCommand(records.get(i));
        cummulativeTime += cmd.nextCommandStartTime;
        replayTimeline.getKeyFrames().add(
                new KeyFrame(new Duration(cummulativeTime),
                        event -> executeRecord(cmd, exectutersMap)));
        return cummulativeTime;
    }

    private void executeRecord(CommandContainer cmd, Map<String, RecordExecutor> executersMap) {
        RecordExecutor executer = executersMap.get(cmd.commandName);
        executer.setArgs(cmd.args);
        executer.run();
    }

    private CommandContainer parseCommand(String cmdStr){
        String[] keys = cmdStr.split("\\t");
        CommandContainer cmd = new CommandContainer();
        cmd.commandName = keys[0];
        cmd.args = Arrays.copyOfRange(keys, 2, keys.length);
        cmd.nextCommandStartTime = Long.parseLong(keys[1]);
        return cmd;
    }

}

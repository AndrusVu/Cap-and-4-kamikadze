package Controllers;

import Auxiliary.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

public class StartSceneController extends ControllerBase {
    @FXML private Slider difficultySlider;
    @FXML private CheckBox AICheckbox;

    public StartSceneController(){
        difficultySlider = new Slider();
        AICheckbox = new CheckBox();
    }

    @FXML protected void handleExitClick() {
        System.exit(0);
    }

    @FXML protected void handleStartClick(){
        GameController controller = preloadGameScene();
        controller.start((int) difficultySlider.getValue(), AICheckbox.isSelected());
    }

    @FXML protected void playLastGame(){
        GameController controller = preloadGameScene();
        controller.playRecord(ReplayFilesLoader.getLastReplayFileContent());
    }

    @FXML protected void handleCalcPerformanceButtonClick(){
        File[] replayFiles = ReplayFilesLoader.getReplayFiles();
        Map<String, Integer> unsortedPoints = ReplaysPointsCollector.getActionsPoints(replayFiles);

        ISortPerformanceCounter javaSort = new JavaSortPerformanceCounter(unsortedPoints);
        Map<String, Integer> javaSorted = javaSort.sort();

        ISortPerformanceCounter scalaSort = new ScalaSortPerformanceCounter(unsortedPoints);
        Map<String, Integer> scalaSorted = scalaSort.sort();

        writeResults(javaSorted, "Java sorted ", "JavaSort.res");
        writeResults(scalaSorted, "Scala sorted ", "ScalaSort.res");

        showSortPerformanceInfo(javaSort, scalaSort);
    }

    private void showSortPerformanceInfo(ISortPerformanceCounter javaSort, ISortPerformanceCounter scalaSort) {
        showMagicInfo("Performance calculations result", "Java and Scala performance calculations result",
                String.format("Java execution time: %d\nScala execution time: %d",
                javaSort.getExecutionTime(), scalaSort.getExecutionTime()));
    }

    private void writeResults(Map<String, Integer> toOut, String resName, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Map.Entry<String, Integer> entry : toOut.entrySet()) {
                writer.write(String.format("%s\t%d%n", entry.getKey(), entry.getValue()));
            }
            writer.close();
        }catch (IOException e){
            System.out.println(resName + "map out error\n");
        }
    }

    @FXML protected void handleCalcStatisticButtonClick(){
        File[] replayFiles = ReplayFilesLoader.getReplayFiles();
        ScalaStatisticCollector collector = new ScalaStatisticCollector(replayFiles);
        List<Map<String, Integer>> statistic = collector.collect();
        writeStatisticResult(replayFiles, statistic);
        showStatisticInfo(statistic.get(statistic.size() - 1));
    }

    private void writeStatisticResult(File[] replayFiles, List<Map<String, Integer>> statistic) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ReplaysStatistics.stat"));
            writeStatisticList(replayFiles, statistic, writer);
            writer.close();
        } catch (IOException e){
            System.out.println("Scala statistics " + " write failed\n");
        }
    }

    private void writeStatisticList(File[] replayFiles, List<Map<String, Integer>> statistic, BufferedWriter writer) throws IOException {
        for (int i = 0; i < statistic.size() - 1; i++) {
            writer.write(String.format("Statistic for: %s file%n", replayFiles[i].getName()));
            writeReplayStatistic(statistic, writer, i);
        }
        writer.write("Summary statistic:\nn");
        writeReplayStatistic(statistic, writer, statistic.size() - 1);
    }

    private void writeReplayStatistic(List<Map<String, Integer>> statistic, BufferedWriter writer, int i) throws IOException {
        for (Map.Entry<String, Integer> entry : statistic.get(i).entrySet()) {
            writer.write(String.format("%s\t%d%n", entry.getKey(), entry.getValue()));
        }
    }

    private void showStatisticInfo(Map<String, Integer> statistic) {
        showMagicInfo("Statistic calculations result",
                "Game statistic calculations result",
                buildStatisticInfoString(statistic));
    }

    private String buildStatisticInfoString(Map<String, Integer> statistic) {
        String infoBoxMsg = new String(String.format("%-30s\t%-30s\n", "Event type", "        Call times"));
        for(Map.Entry<String, Integer> entry : statistic.entrySet()){
            infoBoxMsg += String.format("%-30s\t%-30d\n", entry.getKey(), entry.getValue());
        }
        return infoBoxMsg;
    }

    @FXML protected void handleLastReplayMagicMouseClick(){
        String lastReplayContent = ReplayFilesLoader.getLastReplayFileContent();
        String lastReplayReadableForm = ScalaLastReplayMagicPerformer.makeReplayReadable(lastReplayContent);

        try {
            Files.write(Paths.get("LastReplayReadableForm.rrepl"), lastReplayReadableForm.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e){
            System.out.println("Last replay making readable form write failed");
        }

        showMagicInfo("Magic result",
                "Replay magic transformations result",
                "Completed");
    }

    private void showMagicInfo(String title, String headerText, String completed) {
        Alert infoBox = new Alert(Alert.AlertType.INFORMATION);
        infoBox.setTitle(title);
        infoBox.setHeaderText(headerText);
        infoBox.setContentText(completed);
        infoBox.showAndWait();
    }

    @FXML protected void handlePlayBestMouseClick(){
        GameController controller = preloadGameScene();
        controller.playRecord(ReplayFilesLoader.getBestReplayFileContent());
    }

    private GameController preloadGameScene() {
        loader.setScenePath("Views/GameScene.fxml");
        loader.loadScene();
        GameController controller = loader.getSceneController();
        controller.setLoader(loader);
        return controller;
    }
}

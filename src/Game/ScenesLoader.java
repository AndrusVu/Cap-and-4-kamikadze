package Game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Manages scenes loading process.
 */
public class ScenesLoader {
    private MainApp mainApp;
    private Stage primaryStage;
    private FXMLLoader loader;
    private String scenePath;

    public ScenesLoader(MainApp mainApp, Stage primaryStage){
        this.mainApp = mainApp;
        this.primaryStage = primaryStage;
    }

    /**
     * Set scene for work.
     * @param scenePath scene path.
     */
    public void setScenePath(String scenePath){
        this.scenePath = scenePath;
    }

    /**
     * Loads current installed scene.
     */
    public void loadScene() {
        loader = new FXMLLoader(MainApp.class.getClassLoader().getResource(scenePath));
        Parent pageContent = null;
        try {
            pageContent = loader.load();
        }catch (IOException e) {
            e.printStackTrace();
        }
        Scene curentScene = primaryStage.getScene();
        if(curentScene == null){
            primaryStage.setScene(new Scene(pageContent));
        } else{
            primaryStage.getScene().setRoot(pageContent);
        }
        primaryStage.sizeToScene();
    }

    /**
     * Returns current installed scene controller instance.
     * @param <T> every type that extends ControllerBase abstract class.
     * @return scene controller instance.
     */
    public <T> T getSceneController() {
        loader.setLocation(MainApp.class.getClassLoader().getResource(scenePath));
        return loader.getController();
    }
}

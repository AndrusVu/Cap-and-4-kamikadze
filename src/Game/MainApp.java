package Game;

import Controllers.StartSceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        ScenesLoader loader = new ScenesLoader(this, primaryStage);

        loader.setScenePath("Views/StartScene.fxml");
        loader.loadScene();
        StartSceneController controller = loader.getSceneController();
        controller.setLoader(loader);

        primaryStage.setTitle("Airplane");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

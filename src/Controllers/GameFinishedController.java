package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

public class GameFinishedController extends ControllerBase{
    @FXML private Label gameResultLabel;

    public GameFinishedController(){
        gameResultLabel = new Label();
    }

    public void setGameFinishResult(boolean isWin) {
        if (isWin) {
            gameResultLabel.setText("WIN");
            gameResultLabel.setTextFill(Paint.valueOf("LIME"));
        } else {
            gameResultLabel.setText("LOOSE");
            gameResultLabel.setTextFill(Paint.valueOf("RED"));
        }
    }

    public void handleContinueButtonClick(){
        loader.setScenePath("Views/StartScene.fxml");
        loader.loadScene();
        StartSceneController controller = loader.getSceneController();
        controller.setLoader(loader);
    }


}

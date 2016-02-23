package Game;

import Controllers.StartSceneController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
         *Sound
         */
/*        final URL resource = getClass().getResource("https://cdn.eee.fm/file/T1BCS0xCK0I1bjdhNkNvQ1BMS2VoNzd2NHRSbi9jZHc2YzFTMXlTZjdJaE5TdENKbGRHand5KzBjN1hUWFMxeWNEUnVFTjR6VnNjRnpDWGdvUFppcnc0RnRpRGYySWVaN2k5dFVzTmd1NmJ3TVI3U3dvQnNCVG92RWJjNXBQMm8/freelancer_-_critical_conditions_(eee.fm).mp3");
        final Media media = new Media(resource.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();*/

        ScenesLoader loader = new ScenesLoader(this, primaryStage);
        loader.setScenePath("Views/StartScene.fxml");
        loader.loadScene();
        StartSceneController controller = loader.getSceneController();
        controller.setLoader(loader);

        primaryStage.setTitle("Cap & 4 kamikadze");

        /*
         * Add new application icon.
         */
        //Image applicationIcon = new Image(getClass().getResourceAsStream("icon.png"));
        //primaryStage.getIcons().add(applicationIcon);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

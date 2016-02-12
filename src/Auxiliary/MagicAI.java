package Auxiliary;

import Models.Vector2;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
* MagicAI implements Artificial Intelligence logic. Runs in separate thread in which starts ScheduledExecutorService
* by scheduleAtFixedRate method. MagicAI can be instantiates like separate thread, or passed in Timeline object
* as EventHandler<ActionEvent> object.
*/
public class MagicAI extends Thread implements EventHandler<ActionEvent> {
    private Node gameField;
    private Group mobs;

    public MagicAI(Group mobs, Node gameField){
        this.mobs = mobs;
        this.gameField = gameField;
    }

    @Override
    public void run(){
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handle(null);
            }
        }, 100, 200, TimeUnit.MILLISECONDS);

        while(!Thread.interrupted());

        scheduler.shutdown();
    }

    @Override
    public void handle(ActionEvent event) {
        int mobsSize = mobs.getChildren().size();
        if(mobsSize > 0) {
            Node mobToKill = mobs.getChildren().get((int) Math.ceil(Math.random() * (mobsSize-1)));

            Vector2 mobPos = getNodePos(mobToKill);
            fireGameFieldMouseEvent(MouseEvent.MOUSE_MOVED,
                    new Vector2(mobPos.getX(), mobPos.getY()));
            fireGameFieldMouseEvent(MouseEvent.MOUSE_CLICKED,
                    new Vector2(mobPos.getX(), mobPos.getY()));
        }
    }

    private void fireGameFieldMouseEvent(EventType<MouseEvent> mouseEventType, Vector2 mouseEventCoords) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameField.fireEvent(new MouseEvent(mouseEventType,
                        mouseEventCoords.getX(), mouseEventCoords.getY(),
                        0, 0, MouseButton.PRIMARY, 0,
                        true, true, true, true, true,
                        true, true, true, true, true, null));
            }
        });
    }

    private Vector2 getNodePos(Node node){
        Vector2 pos = new Vector2();
        pos.setX((node.getBoundsInLocal().getMaxX() + node.getBoundsInLocal().getMinX())/2);
        pos.setY((node.getBoundsInLocal().getMaxY() + node.getBoundsInLocal().getMinY())/2);
        return pos;
    }
}

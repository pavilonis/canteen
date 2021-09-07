package lt.pavilonis.canteen;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

public class JavaFxApp extends Application {

   @Override
   public void start(Stage stage) {
      try {
         var controller = new ViewController();
         stage.setScene(createScene(controller.getCards()));
         addTestEventHandler(stage, controller);
         stage.show();
      } catch (Exception e) {
         LogFactory.getLog(getClass()).error("Could not start JavaFX application", e);
      }
   }

   private Scene createScene(Collection<? extends Node> cards) {

      var root = new Group();
      root.getChildren().addAll(cards);

      var scene = new Scene(root, 1440, 900, Color.WHITE);
      scene.setOnKeyPressed(event -> {
         if (event.getCode() == KeyCode.ESCAPE) {
            stop();
         }
      });
      return scene;
   }

   private void addTestEventHandler(Stage stage, ViewController controller) {
      stage.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent k) -> {
         if (k.getCharacter().equals("a")) {
            controller.consumeScannerInput("6EB4A9A6");
         }
         if (k.getCharacter().equals("b")) {
            controller.consumeScannerInput("36944CD0");
         }
      });
   }

   @Override
   public void stop() {
      System.exit(0);
   }
}

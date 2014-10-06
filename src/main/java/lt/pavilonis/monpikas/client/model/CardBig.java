package lt.pavilonis.monpikas.client.model;

import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CardBig extends Card {

   @Value("${Card.Message.NoPermission}")
   private String NO_PERMISSION_MSG;

   @Value("${Card.Message.AlreadyHadDinner}")
   private String ALREADY_HAD_DINNER_MSG;

   @Value("${Card.Icon.StatusOkContent}")
   private String ICON_STATUS_OK_CONTENT;

   @Value("${Card.Icon.StatusRejectedContent}")
   private String ICON_STATUS_REJECT_CONTENT;

   private final Text REJECT_TEXT = new Text();
   private final SVGPath ICON_STATUS_OK = new SVGPath();
   private final SVGPath ICON_STATUS_REJECT = new SVGPath();
   private final FlowPane STATUS_MSG_FLOW_PANE = new FlowPane();
   private final ScaleTransition scale = new ScaleTransition(ANIMATION_DURATION, this);

   @Override
   public void initialize() {

      super.initialize();
      setLayoutX(20);
      setLayoutY(20);

      ICON_NO_PHOTO.setScaleX(19);
      ICON_NO_PHOTO.setScaleY(19);
      ICON_NO_PHOTO.setStrokeWidth(0.2);

      ICON_STATUS_OK.setContent(ICON_STATUS_OK_CONTENT);
      ICON_STATUS_OK.setScaleX(6);
      ICON_STATUS_OK.setScaleY(6);
      ICON_STATUS_OK.setFill(Color.GREEN);
      ICON_STATUS_OK.setStroke(Color.DARKGREY);
      ICON_STATUS_OK.setStrokeWidth(0.2);

      ICON_STATUS_REJECT.setContent(ICON_STATUS_REJECT_CONTENT);
      ICON_STATUS_REJECT.setScaleX(6);
      ICON_STATUS_REJECT.setScaleY(6);
      ICON_STATUS_REJECT.setFill(Color.RED);
      ICON_STATUS_REJECT.setStroke(Color.DARKGREY);
      ICON_STATUS_REJECT.setStrokeWidth(0.2);

      REJECT_TEXT.setFont(Font.font("SansSerif", 40));

      ColumnConstraints columnConstraint = new ColumnConstraints(540);
      columnConstraint.setHalignment(HPos.CENTER);
      grid.getColumnConstraints().add(columnConstraint);

      RowConstraints rcTop = new RowConstraints(480);
      rcTop.setValignment(VPos.CENTER);
      RowConstraints rcMiddle = new RowConstraints(180);
      rcMiddle.setValignment(VPos.CENTER);
      RowConstraints rcBottom = new RowConstraints(160);
      rcBottom.setValignment(VPos.CENTER);

      grid.getRowConstraints().addAll(rcTop, rcMiddle, rcBottom);
      grid.add(PHOTO_CONTAINER, 0, 0);

      nameText.setWrappingWidth(540);
      nameText.setFont(Font.font("SansSerif", 70));
      nameText.setTextAlignment(TextAlignment.CENTER);
      grid.add(nameText, 0, 1);

      outerRect.setWidth(580);
      outerRect.setHeight(860);

      innerRect.setX(20);
      innerRect.setY(20);
      innerRect.setWidth(540);
      innerRect.setHeight(820);

      STATUS_MSG_FLOW_PANE.setAlignment(Pos.CENTER);
      STATUS_MSG_FLOW_PANE.setHgap(60);
      grid.add(STATUS_MSG_FLOW_PANE, 0, 2);
      grid.setPadding(new Insets(20));
      getChildren().add(outerRect);
      getChildren().add(innerRect);
      getChildren().add(grid);
   }

   @Override
   public void update() {
      STATUS_MSG_FLOW_PANE.getChildren().clear();
      if (!dto.isError()) {
         if (!checkIfDinnerAllowed()) {
            REJECT_TEXT.setText(
                  (!dto.isDinnerPermitted())
                        ? NO_PERMISSION_MSG
                        : ALREADY_HAD_DINNER_MSG
            );
            STATUS_MSG_FLOW_PANE.getChildren().addAll(ICON_STATUS_REJECT, REJECT_TEXT);
         } else {
            STATUS_MSG_FLOW_PANE.getChildren().add(ICON_STATUS_OK);
         }
         setPhoto();
      } else {
         checkIfDinnerAllowed();
         STATUS_MSG_FLOW_PANE.getChildren().add(ICON_STATUS_REJECT);
      }
      ensureVisible();
   }

   @Override
   public void animate() {
      double originX = getTranslateX();
      double originY = getTranslateY();
      translate.setToX(703);
      translate.setToY(-328);
      scale.setToX(1.4);
      scale.setToY(.245);
      fade.setFromValue(1);
      fade.setToValue(0);
      fade.setOnFinished(event -> {
         setScaleX(1);
         setScaleY(1);
         setTranslateX(originX);
         setTranslateY(originY);
         update();
         fade.setFromValue(0);
         fade.setToValue(1);
         fade.play();
         fade.setOnFinished(event2 -> {
            fade.setOnFinished(null);
            setPhoto();
         });
      });
      scale.play();
      translate.play();
      fade.play();
   }

   @Override
   public List<Transition> getTransitions() {
      return Arrays.asList(scale, fade, translate);
   }
//
//   @Override
//   protected boolean setPhoto(String id) {
//      if (super.setPhoto(id)) {
//         userPhoto
//      } else {
//         return false;
//      }
//
//   }
}
package lt.pavilonis.canteen.model;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public final class CardSmall extends Card {

   private final Card nextCard;

   public CardSmall(Card nextCard) {
      this.nextCard = nextCard;
   }

   @Override
   public void initialize() {

      super.initialize();
      setLayoutX(620);

      ColumnConstraints ccLeft = new ColumnConstraints(142.5);
      ColumnConstraints ccRight = new ColumnConstraints(657.5);
      ccLeft.setHalignment(HPos.CENTER);
      ccRight.setHalignment(HPos.CENTER);
      grid.getColumnConstraints().addAll(ccLeft, ccRight);
      grid.setPadding(new Insets(10));

      RowConstraints rc = new RowConstraints(180);
      rc.setValignment(VPos.CENTER);
      grid.getRowConstraints().add(rc);

      iconNoPhoto.setScaleX(5);
      iconNoPhoto.setScaleY(5);
      iconNoPhoto.setStrokeWidth(0.5);
      grid.add(photoContainer, 0, 0);

      nameText.setWrappingWidth(640);
      nameText.setFont(Font.font("SansSerif", 60));
      grid.add(nameText, 1, 0);

      outerRect.setWidth(800);
      outerRect.setHeight(200);

      innerRect.setX(10);
      innerRect.setY(10);
      innerRect.setWidth(780);
      innerRect.setHeight(180);

      getChildren().add(outerRect);
      getChildren().add(innerRect);
      getChildren().add(grid);
   }

   @Override
   public void update() {
      if (response != null) {
         Platform.runLater(() -> {
            super.update();
            setPhoto();
            ensureVisible();
         });
      }
      nextCard.update();
   }
}

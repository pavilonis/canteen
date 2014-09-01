package lt.pavilonis.monpikas.client;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDateTime;

public class UserInfoSmall extends Group {
   public UserInfoSmall(User user) {

      this.setLayoutX(620);
      this.setLayoutY(20);

      GridPane grid = new GridPane();
      grid.setPadding(new Insets(20));
      grid.setHgap(10);

      SVGPath icon = new SVGPath();
      icon.setContent("M20.771,12.364c0,0,0.849-3.51,0-4.699c-0.85-1.189-1.189-1.981-3.058-2.548s-1.188-0.454-2.547-0.396c-1.359,0.057-2.492,0.792-2.492,1.188c0,0-0.849,0.057-1.188,0.397c-0.34,0.34-0.906,1.924-0.906,2.321s0.283,3.058,0.566,3.624l-0.337,0.113c-0.283,3.283,1.132,3.68,1.132,3.68c0.509,3.058,1.019,1.756,1.019,2.548s-0.51,0.51-0.51,0.51s-0.452,1.245-1.584,1.698c-1.132,0.452-7.416,2.886-7.927,3.396c-0.511,0.511-0.453,2.888-0.453,2.888h26.947c0,0,0.059-2.377-0.452-2.888c-0.512-0.511-6.796-2.944-7.928-3.396c-1.132-0.453-1.584-1.698-1.584-1.698s-0.51,0.282-0.51-0.51s0.51,0.51,1.02-2.548c0,0,1.414-0.397,1.132-3.68H20.771z");
      icon.setScaleX(5);
      icon.setScaleY(5);
      icon.setStrokeWidth(0.5);
      icon.setStroke(Color.DARKGREY);
      icon.setFill(Color.LIGHTGRAY);

      ColumnConstraints ccLeft = new ColumnConstraints(142.5);
      ccLeft.setHalignment(HPos.CENTER);
      grid.getColumnConstraints().addAll(ccLeft, new ColumnConstraints(697.5));

      RowConstraints rc = new RowConstraints(190);
      rc.setValignment(VPos.CENTER);
      grid.getRowConstraints().add(rc);
      grid.add(icon, 0, 0);

      Text name = new Text(user.getName());
      name.setFont(Font.font("SansSerif", 70));
      name.setSmooth(true);
      name.setWrappingWidth(697.5);
      grid.add(name, 1, 0);

      Rectangle outerRect = new Rectangle(840, 210);
      outerRect.setArcHeight(20);
      outerRect.setArcWidth(20);
      outerRect.setStroke(Color.BLACK);
      outerRect.setEffect(new DropShadow(10, 5, 5, Color.DARKGRAY));

      Rectangle innerRect = new Rectangle(10, 10, 820, 190);
      innerRect.setFill(Color.WHITE);
      innerRect.setArcHeight(20);
      innerRect.setArcWidth(20);
      innerRect.setStroke(Color.BLACK);

      outerRect.setFill(
            (user.isDinnerPermission() && user.getLastDinner().getDayOfYear() != LocalDateTime.now().getDayOfYear())
                  ? Color.GREEN
                  : Color.RED
      );

      this.getChildren().add(outerRect);
      this.getChildren().add(innerRect);
      this.getChildren().add(grid);
   }
}

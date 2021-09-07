package lt.pavilonis.canteen.model;

import com.google.common.io.BaseEncoding;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import lt.pavilonis.canteen.Spring;
import lt.pavilonis.canteen.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public abstract class Card extends Group {

   private static final Logger LOGGER = LoggerFactory.getLogger(Card.class);
   private final String iconNoPhotoContentPath = Spring.getStringProperty("card.icon.noPhotoContent");
   private final String noPermissionMessage = Spring.getStringProperty("card.message.noPermission");
   private final String alreadyHadMealMessage = Spring.getStringProperty("card.message.alreadyHadDinner");
   private final boolean displayPhotos = Boolean.parseBoolean(Spring.getStringProperty("card.displayPhotos"));

   protected final FlowPane photoContainer = new FlowPane();
   protected final SVGPath iconNoPhoto = new SVGPath();
   protected final Rectangle outerRect = new Rectangle();
   protected final Rectangle innerRect = new Rectangle();
   protected final Text nameText = new Text();
   protected final Text gradeText = new Text();
   protected final GridPane grid = new GridPane();
   protected final ImageView imageView = new ImageView();

   protected ResponseEntity<User> response;

   public void initialize() {
      setVisible(false);
      photoContainer.setAlignment(Pos.CENTER);

      iconNoPhoto.setContent(iconNoPhotoContentPath);
      iconNoPhoto.setStroke(Color.DARKGREY);
      iconNoPhoto.setFill(Color.LIGHTGRAY);

      imageView.setPreserveRatio(true);
      imageView.fitWidthProperty().bind(photoContainer.widthProperty().asObject());
      imageView.fitHeightProperty().bind(photoContainer.heightProperty());

      outerRect.setArcHeight(20);
      outerRect.setArcWidth(20);
      outerRect.setStroke(Color.BLACK);

      innerRect.setArcHeight(20);
      innerRect.setArcWidth(20);
      innerRect.setFill(Color.WHITE);
      innerRect.setStroke(Color.BLACK);
   }

   public void update() {
      User dto = response.getBody();

      switch (response.getStatusCode()) {

         case ACCEPTED:
            decorate(dto.getName(), GREEN, dto.getEating());
            log("Pupil " + dto.getName() + " is getting his meal");
            break;

         case ALREADY_REPORTED:
            decorate(dto.getName(), RED, alreadyHadMealMessage);
            log("Pupil " + dto.getName() + " already had hist meal");
            break;

         case FORBIDDEN:
            decorate(dto.getName(), RED, noPermissionMessage);
            log("Pupil " + dto.getName() + " has no permission");
            break;

         case NOT_FOUND:
            decorate("Nežinomas mokinys", RED, "");
            log("Pupil not found: ");
            break;

         case SERVICE_UNAVAILABLE:
            log("Server not found");
            decorate("Serveris nerastas", RED, "");
            break;

         case MULTIPLE_CHOICES:
            log("Eating time mismatch");
            decorate("Netinkamas laikas", RED, "");
            break;

         case INTERNAL_SERVER_ERROR:
            decorate("Serverio klaida", RED, "");
            LOGGER.error("Server error");
            break;

         default:
            decorate("Nežinoma klaida", RED, "");
            LOGGER.error("Unknown error");
      }
   }

   protected void log(String text) {/*should be overridden by big card*/}

   protected void decorate(String name, Color color, Object desc) {
      Platform.runLater(() -> {
         nameText.setText(name);
         outerRect.setFill(color);
      });
   }

   protected void setPhoto() {
      Platform.runLater(() -> {
         ObservableList<Node> container = photoContainer.getChildren();
         container.clear();
         Image image = getImage();
         if (image == null) {
            container.add(iconNoPhoto);
         } else {
            imageView.setImage(image);
            container.add(imageView);
            imageView.setY(50);
         }
      });
   }

   public void setResponse(ResponseEntity<User> response) {
      this.response = response;
   }

   protected void ensureVisible() {
      if (!isVisible()) {
         setVisible(true);
      }
   }

   private Image getImage() {

      if (!displayPhotos || response == null || response.getBody() == null) {
         return null;
      }

      String base16image = response.getBody().getBase16photo();

      return StringUtils.hasText(base16image) && BaseEncoding.base16().canDecode(base16image)
            ? extractImageFromString(base16image)
            : null;
   }

   private Image extractImageFromString(String base16image) {
      byte[] imageBytes = BaseEncoding.base16().decode(base16image);

      try (ByteArrayInputStream input = new ByteArrayInputStream(imageBytes)) {

         return new Image(input);

      } catch (IOException e) {
         e.printStackTrace();
         return null;
      }
   }
}

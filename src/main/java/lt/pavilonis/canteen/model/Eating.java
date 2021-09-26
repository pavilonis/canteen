package lt.pavilonis.canteen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lt.pavilonis.canteen.EatingType;

import java.text.DecimalFormat;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Eating {

   //TODO Move out
   private static final Map<EatingType, String> TRANSLATIONS = Map.of(
         EatingType.BREAKFAST, "Pusryčiai",
         EatingType.LUNCH, "Priešpiečiai",
         EatingType.DINNER, "Pietus",
         EatingType.LATE_SUPPER, "Vakarienė",
         EatingType.SUPPER, "Naktipiečiai"
   );

   private final EatingType type;
   private final double price;

   public Eating(@JsonProperty("type") EatingType type,
                 @JsonProperty("price") double price) {

      this.type = type;
      this.price = price;
   }

   @Override
   public String toString() {
      return TRANSLATIONS.get(type) + " " + new DecimalFormat("0.00").format(price);
   }
}

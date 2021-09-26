package lt.pavilonis.canteen.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lt.pavilonis.canteen.model.Eating;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class User {

   private final String name;
   private final Eating eating;
   private final String grade;
   private final String base16photo;

   public User(@JsonProperty("name") String name,
               @JsonProperty("eating") Eating eating,
               @JsonProperty("grade") String grade,
               @JsonProperty("base16photo") String base16photo) {

      this.name = name;
      this.eating = eating;
      this.grade = grade;
      this.base16photo = base16photo;
   }

   public String getName() {
      return name;
   }

   public Eating getEating() {
      return eating;
   }

   public String getGrade() {
      return grade;
   }

   public String getBase16photo() {
      return base16photo;
   }
}

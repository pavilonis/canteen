package lt.pavilonis.monpikas.client.dto;

import java.io.Serializable;

public class ClientPupilDto implements Serializable {

   private static final long serialVersionUID = 1L;
   private String cardId;
   private String name;
   private boolean dinnerPermitted;
   private boolean hadDinnerToday;
   private boolean systemError;

   public ClientPupilDto() {
   }

   public ClientPupilDto(String cardId, String name, boolean dinnerPermitted, boolean hadDinnerToday) {
      this.cardId = cardId;
      this.name = name;
      this.dinnerPermitted = dinnerPermitted;
      this.hadDinnerToday = hadDinnerToday;
   }

   //for system error events
   public ClientPupilDto(String errorMsg) {
      this.systemError = true;
      this.cardId = "0";
      this.name = errorMsg;
      this.dinnerPermitted = false;
      this.hadDinnerToday = false;
   }

   public String getCardId() {
      return cardId;
   }

   public void setCardId(String cardId) {
      this.cardId = cardId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean isDinnerPermitted() {
      return dinnerPermitted;
   }

   public void setDinnerPermitted(boolean dinnerPermitted) {
      this.dinnerPermitted = dinnerPermitted;
   }

   public boolean isHadDinnerToday() {
      return hadDinnerToday;
   }

   public void setHadDinnerToday(boolean hadDinnerToday) {
      this.hadDinnerToday = hadDinnerToday;
   }

   public boolean isSystemError() {
      return systemError;
   }

   public void setSystemError(boolean systemError) {
      this.systemError = systemError;
   }
}

package lt.pavilonis.canteen;

import com.google.common.collect.EvictingQueue;
import com.google.common.util.concurrent.AtomicDouble;
import lt.pavilonis.canteen.dto.User;
import lt.pavilonis.canteen.model.Card;
import lt.pavilonis.canteen.model.CardBig;
import lt.pavilonis.canteen.model.CardSmall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.reverse;

final class ViewController {

   private static final Logger LOGGER = LoggerFactory.getLogger(ViewController.class);
   private final UserRequestService userRequestService = Spring.getBean(UserRequestService.class);
   private final EvictingQueue<ResponseEntity<User>> responses = EvictingQueue.create(5);
   private final List<Card> cards;

   ViewController() {
      new SerialPortListener(Spring.getStringProperty("scanner.port.name"), this::consumeScannerInput);
      this.cards = createListOfCards();
   }

   private List<Card> createListOfCards() {
      var first = new CardBig();
      var second = new CardSmall(first);
      var third = new CardSmall(second);
      var forth = new CardSmall(third);
      List<Card> list = List.of(first, second, third, forth, new CardSmall(forth));

      var y = new AtomicDouble(20);
      list.forEach(card -> {
         card.initialize();
         if (list.indexOf(card) != 0) {
            card.setLayoutY(y.get());
            y.getAndAdd(220);
         }
      });

      return list;
   }

   void consumeScannerInput(String cardCode) {

      cardCode = cardCode + "000000";
      LOGGER.info("Requesting user with cardCode: " + cardCode);

      try {
         responses.add(userRequestService.requestUser(cardCode));

      } catch (ResourceAccessException e) {
         LOGGER.error("no connection to server", e);
         responses.add(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));

      } catch (HttpStatusCodeException e) {
         LOGGER.error("Unknown error", e);
      }

      int i = 0;
      //update cards content
      for (ResponseEntity<User> response : reverse(new ArrayList<>(responses))) {
         cards.get(i++).setResponse(response);
      }
      //start visual update sequence
      cards.get(cards.size() - 1).update();
   }

   public List<Card> getCards() {
      return cards;
   }
}

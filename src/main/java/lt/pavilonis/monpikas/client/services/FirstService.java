package lt.pavilonis.monpikas.client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FirstService {

   @Autowired
   private RestTemplate rt;

   public String SaySomething() {
      return "I sit on a table";
   }
}

package lt.pavilonis.canteen;

import lt.pavilonis.canteen.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class UserRequestService {

   private final String url;
   private final RestTemplate restTemplate;

   public UserRequestService(@Value("${api.uri}") String url,
                             @Value("${api.auth.username}") String username,
                             @Value("${api.auth.password}") String password) {
      this.url = url;
      this.restTemplate = createRestTemplate(username, password);
   }

   public ResponseEntity<User> requestUser(String barcode) {

      URI uri = UriComponentsBuilder.fromUriString(url)
            .pathSegment(barcode)
            .build()
            .toUri();

      return restTemplate.getForEntity(uri, User.class);
   }

   private RestTemplate createRestTemplate(String username, String password) {
      var rest = new RestTemplate();
      rest.setInterceptors(List.of(authenticatingInterceptor(username, password)));
      rest.setMessageConverters(List.of(new MappingJackson2HttpMessageConverter()));
      rest.setErrorHandler(new CustomResponseErrorHandler());
      return rest;
   }

   private ClientHttpRequestInterceptor authenticatingInterceptor(String username, String password) {
      return (request, body, execution) -> {
         HttpHeaders headers = request.getHeaders();
         headers.setBasicAuth(username, password);
         headers.setAccept(List.of(MediaType.APPLICATION_JSON));
         return execution.execute(request, body);
      };
   }
}

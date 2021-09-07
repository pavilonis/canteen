package lt.pavilonis.canteen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
@PropertySource(value = {"inner.properties", "file:${propertiesLocation:app.properties}"}, encoding = "UTF-8")
public class Spring {

   public static ConfigurableApplicationContext CONTEXT;

   public static void main(String[] args) {
      CONTEXT = SpringApplication.run(Spring.class, args);
      JavaFxApp.launch(JavaFxApp.class);
   }

   @Bean
   public static PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
      PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
      conf.setFileEncoding("UTF-8");
      return conf;
   }

   public static String getStringProperty(String name) {
      return CONTEXT.getEnvironment()
            .getProperty(name);
   }

   public static <T> T getStringProperty(String name, Class<T> clazz) {
      return CONTEXT.getEnvironment()
            .getProperty(name, clazz);
   }

   public static <T> T getBean(Class<T> clazz) {
      return CONTEXT.getBean(clazz);
   }
}

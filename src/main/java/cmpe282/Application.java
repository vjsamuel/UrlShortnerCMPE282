package cmpe282;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"cmpe282"})
@EntityScan({"cmpe282"})
@EnableDynamoDBRepositories({"cmpe282"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
  
    }
}
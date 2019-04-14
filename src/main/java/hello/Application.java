package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"controller","service","redis","config","validator","dao", "model"})
@EntityScan( basePackages = {"model"})
public class Application {

    public static void main(String[] args) {
        // http://localhost:8080/
        SpringApplication.run(Application.class, args);
    }
}
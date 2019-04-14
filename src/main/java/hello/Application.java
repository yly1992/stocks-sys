package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"controller","domain","service","redis","config","validator"})
public class Application {

    public static void main(String[] args) {
        // http://localhost:8080/
        SpringApplication.run(Application.class, args);
    }
}
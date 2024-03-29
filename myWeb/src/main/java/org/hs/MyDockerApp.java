package org.hs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MyDockerApp{

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World, change 1.1";
    }

    public static void main(String[] args) {
        SpringApplication.run(MyDockerApp.class, args);
    }

}
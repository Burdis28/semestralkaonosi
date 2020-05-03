package upce.sem.semestralkabe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"upce.sem.semestralkabe"})
public class SemestralkabeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SemestralkabeApplication.class, args);
  }

}

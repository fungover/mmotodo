package org.fungover.mmotodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"org.fungover.mmotodo.entities.team"})
public class MmotodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmotodoApplication.class, args);
    }


}

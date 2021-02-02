package com.ejercicio2.apis;
import static  spark.Spark.*;
import spark.*;
import com.ejercicio2.apis.controllers.SparkController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo2Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);

        port(8081);
        new InitSpark().init();

    }

}

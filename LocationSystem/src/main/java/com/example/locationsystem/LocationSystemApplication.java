package com.example.locationsystem;

import com.example.locationsystem.models.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class LocationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationSystemApplication.class, args);
    }


}

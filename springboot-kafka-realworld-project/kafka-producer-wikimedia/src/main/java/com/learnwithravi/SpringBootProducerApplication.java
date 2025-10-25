package com.learnwithravi;

import com.learnwithravi.kafka.WikimediaChangesProducer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication
public class SpringBootProducerApplication implements CommandLineRunner {
    public SpringBootProducerApplication(WikimediaChangesProducer wikimediaChangesProducer) {
        this.wikimediaChangesProducer = wikimediaChangesProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProducerApplication.class, args);
    }

    private final WikimediaChangesProducer wikimediaChangesProducer;

    @Override
    public void run(String... args) throws Exception {
        wikimediaChangesProducer.sendMessage();
    }
}

package com.learnwithravi.basedomains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BaseDomainsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseDomainsApplication.class, args);
    }

}

package com.cloudflare.billonsigndate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan(basePackages = {"com.cloudflare"})
@EnableScheduling
@EnableAsync
public class BillOnSignDateApplication {
  public static void main(String[] args) {
    SpringApplication.run(BillOnSignDateApplication.class, args);
  }

}

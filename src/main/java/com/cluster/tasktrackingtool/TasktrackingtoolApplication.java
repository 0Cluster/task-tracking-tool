package com.cluster.tasktrackingtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.cluster.tasktrackingtool.models") // Adjust package as needed
public class TasktrackingtoolApplication {

  public static void main(String[] args) {
    SpringApplication.run(TasktrackingtoolApplication.class, args);
  }

}

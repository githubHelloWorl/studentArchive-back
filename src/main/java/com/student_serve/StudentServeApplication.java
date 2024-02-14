package com.student_serve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class StudentServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServeApplication.class, args);
    }

}

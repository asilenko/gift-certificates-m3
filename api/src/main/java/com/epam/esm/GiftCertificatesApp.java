package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application' entry point.
 */
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class GiftCertificatesApp {

    public static void main(String[] args) {
        SpringApplication.run(GiftCertificatesApp.class);
    }
}

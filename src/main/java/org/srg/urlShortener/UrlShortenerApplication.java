package org.srg.urlShortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.srg.urlShortener.services.UrlShortenerService;

@SpringBootApplication
public class UrlShortenerApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerApplication.class);

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerApplication(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // You can add any initialization logic here
        logger.info("URL Shortener Service started...");
    }

}

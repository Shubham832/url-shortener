package org.srg.urlShortener.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.srg.urlShortener.models.ShortUrl;
import org.srg.urlShortener.services.UrlShortenerService;

import java.io.IOException;

@RestController
@RequestMapping("")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/api/shorten")
    public ResponseEntity<ShortUrl> shortenUrl(@RequestParam("url") String originalUrl) {

        ShortUrl shortUrl = urlShortenerService.generateShortUrl(originalUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);

    }

    @GetMapping("/{shortUrl}")
    public void redirectToOriginalUrl(@PathVariable("shortUrl") String shortUrl,
                                      HttpServletResponse response) throws IOException {

        String originalUrl = urlShortenerService.getOriginalUrl(shortUrl);
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }

    }

    @PutMapping("/{shortUrl}")
    public ResponseEntity<ShortUrl> updateShortUrl(@PathVariable("shortUrl") String shortUrl,
                                                   @RequestParam("url") String newOriginalUrl) {

        ShortUrl updatedShortUrl =
                urlShortenerService.updateShortUrl(shortUrl, newOriginalUrl);
        return ResponseEntity.ok(updatedShortUrl);

    }
}
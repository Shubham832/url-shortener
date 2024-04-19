package org.srg.urlShortener.services.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.srg.urlShortener.daos.UrlShortenerDAO;
import org.srg.urlShortener.models.ShortUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.srg.urlShortener.services.UrlShortenerService;

import java.time.Instant;
import java.util.Date;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerServiceImpl.class);

    private final UrlShortenerDAO urlShortenerDAO;

    @Autowired
    public UrlShortenerServiceImpl(UrlShortenerDAO urlShortenerDAO) {
        this.urlShortenerDAO = urlShortenerDAO;
    }

    @Override
    public ShortUrl generateShortUrl(String originalUrl) {
        String shortUrl = generateUniqueShortUrl();
        ShortUrl shortUrlEntity = new ShortUrl();
        shortUrlEntity.setShortUrl(shortUrl);
        shortUrlEntity.setOriginalUrl(originalUrl);
        shortUrlEntity.setCreatedAt(Date.from(Instant.now()));
        shortUrlEntity.setUpdatedAt(Date.from(Instant.now()));
        String savedShortUrl = urlShortenerDAO.save(shortUrlEntity);
        return shortUrlEntity;
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        ShortUrl urlObj = urlShortenerDAO.findByShortUrl(shortUrl);
        if (urlObj != null) {
            return urlObj.getOriginalUrl();
        } else {
            logger.warn("No original URL found for short URL: {}", shortUrl);
        }
        return null;
    }

    @Override
    public ShortUrl updateShortUrl(String shortUrl, String newOriginalUrl) {
        ShortUrl shortUrlEntity = new ShortUrl();
        shortUrlEntity.setShortUrl(shortUrl);
        shortUrlEntity.setOriginalUrl(newOriginalUrl);
        shortUrlEntity.setUpdatedAt(Date.from(Instant.now()));
        urlShortenerDAO.update(shortUrlEntity);
        return shortUrlEntity;
    }

    private String generateUniqueShortUrl() {
        String shortUrl;
        do {
            shortUrl = RandomStringUtils.randomAlphanumeric(6);
        } while (urlShortenerDAO.findByShortUrl(shortUrl) != null);
        return shortUrl;
    }
}
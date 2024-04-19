package org.srg.urlShortener.services;

import org.srg.urlShortener.models.ShortUrl;

public interface UrlShortenerService {

    ShortUrl generateShortUrl(String originalUrl);

    String getOriginalUrl(String shortUrl);

    ShortUrl updateShortUrl(String shortUrl, String newOriginalUrl);
}

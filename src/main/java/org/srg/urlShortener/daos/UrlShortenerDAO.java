package org.srg.urlShortener.daos;

import org.srg.urlShortener.models.ShortUrl;

public interface UrlShortenerDAO {

    String save(ShortUrl shortUrl);

    ShortUrl findByShortUrl(String shortUrl);

    void update(ShortUrl shortUrl);
}

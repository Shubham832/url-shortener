package org.srg.urlShortener.daos.impl;

import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.srg.urlShortener.daos.UrlShortenerDAO;
import org.srg.urlShortener.models.ShortUrl;


@Repository
public class UrlShortenerDAOImpl implements UrlShortenerDAO {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerDAOImpl.class);

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UrlShortenerDAOImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String save(ShortUrl shortUrl) {
        ShortUrl savedShortUrl = mongoTemplate.save(shortUrl);
        return savedShortUrl.getShortUrl();
    }

    @Override
    public ShortUrl findByShortUrl(String shortUrl) {
        Query query = new Query(Criteria.where("shortUrl").is(shortUrl));
        return mongoTemplate.findOne(query, ShortUrl.class);
    }

    @Override
    public void update(ShortUrl shortUrl) {
        Query query = new Query(Criteria.where("shortUrl").is(shortUrl.getShortUrl()));
        Update update = new Update()
                .set("originalUrl", shortUrl.getOriginalUrl())
                .set("updatedAt", shortUrl.getUpdatedAt());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, ShortUrl.class);
        if (updateResult.getModifiedCount() == 0) {
            logger.warn("No ShortUrl document updated for short URL: {}", shortUrl.getShortUrl());
        }
    }
}
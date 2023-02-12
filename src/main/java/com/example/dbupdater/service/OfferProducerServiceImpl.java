package com.example.dbupdater.service;

import com.example.dbupdater.dao.OfferDao;
import com.example.dbupdater.dao.OfferDaoImpl;
import com.example.dbupdater.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class OfferProducerServiceImpl implements OfferProducerService {

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OfferDao offerDao;

    @Autowired
    public OfferProducerServiceImpl(KafkaTemplate<String, String> kafkaTemplate, OfferDao offerDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.offerDao = offerDao;
    }

    public void sendChunksToKafka(int rowsSize, int offset) throws IOException {
        int sizeOfOffers = offerDao.getSizeOfOffers();

        while (offset < sizeOfOffers) {
            List<Offer> chunkFromDb = offerDao.getChunkFromDb(rowsSize, offset);
            kafkaTemplate.send(topic, chunkFromDb.toString());
            offset += rowsSize;
        }
    }
}

package com.example.dbupdater.service;

import java.io.IOException;

public interface OfferProducerService {

    void sendChunksToKafka(int rowsSize, int offset) throws IOException;
}

package com.example.dbupdater.dao;

import com.example.dbupdater.model.Offer;

import java.io.IOException;
import java.util.List;

public interface OfferDao {
    List<Offer> getChunkFromDb(int rowsSize, int offset) throws IOException;
    int getSizeOfOffers();
}

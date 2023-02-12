package com.example.dbupdater.controller;

import com.example.dbupdater.service.OfferProducerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/updater")
public class DbUpdaterController {

    private final OfferProducerService offerProducerService;

    @Autowired
    public DbUpdaterController(OfferProducerService offerProducerService) {
        this.offerProducerService = offerProducerService;
    }

    @GetMapping(value = "/offerstocache", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateOffersInCacheService(@RequestParam int rowsSize, @RequestParam int offset) throws IOException {
        offerProducerService.sendChunksToKafka(rowsSize, offset);
        return ResponseEntity.ok("Ok");
    }
}
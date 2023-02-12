package com.example.dbupdater.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Offer implements Serializable {
    private long offerId;
    private int exposable;
    private String clientFio;
}
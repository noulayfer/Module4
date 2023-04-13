package com.fedorenko.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Factory {
    private List<Detail> details;
    private int amountOfGas;
    private static Factory factory;
    private Factory() {
        this.details = new ArrayList<>();
    }

    public static Factory getInstance() {
        if (factory == null) {
            factory = new Factory();
        }
        return factory;
    }
}

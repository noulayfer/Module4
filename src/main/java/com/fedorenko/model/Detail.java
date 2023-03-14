package com.fedorenko.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private final LocalDateTime dateTime;
    private int seconds;
    private int amountOfGas;
    private int countOfBrokenMicroSchemas;

    public Detail() {
        dateTime = LocalDateTime.now();
    }
}

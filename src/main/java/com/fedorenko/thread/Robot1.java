package com.fedorenko.thread;

import com.fedorenko.model.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
public class Robot1 implements Runnable {
    private final Factory factory;
    private static final Random RANDOM = new Random();
    private static final Semaphore SEMAPHORE = new Semaphore(1);
    private static final Logger LOGGER = LoggerFactory.getLogger(Robot1.class);

    public Robot1() {
        factory = Factory.getInstance();
    }

    @Override
    public void run() {
        try {
            SEMAPHORE.acquire();
            LOGGER.info("{} starts work with class {}.", Thread.currentThread(), Robot1.class.getSimpleName());
            produceGas();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOGGER.info("{} ends work with class {}.", Thread.currentThread(), Robot1.class.getSimpleName());
            SEMAPHORE.release();
        }
    }

    private void produceGas() {
        while (!Thread.currentThread().isInterrupted()) {
                factory.setAmountOfGas(factory.getAmountOfGas() + RANDOM.nextInt(501) + 500);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

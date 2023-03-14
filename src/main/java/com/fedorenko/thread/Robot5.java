package com.fedorenko.thread;

import com.fedorenko.model.Detail;
import com.fedorenko.model.Factory;
import com.fedorenko.service.DetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Robot5 implements Callable<Detail> {
    private static final Random RANDOM = new Random();
    private static final Factory FACTORY = Factory.getInstance();
    private final CountDownLatch waitForRobot4;
    private static final DetailService DETAIL_SERVICE = DetailService.getInstance();
    private static final Semaphore SEMAPHORE = new Semaphore(1);
    private static final Logger LOGGER = LoggerFactory.getLogger(Robot5.class);

    public Robot5(CountDownLatch latch) {
        waitForRobot4 = latch;
    }

    @Override
    public Detail call() {
        try {
            waitForRobot4.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return assembleDetail();
    }

    private Detail assembleDetail() {
        try {
            LOGGER.info("{} starts work with class {}.", Thread.currentThread(), Robot5.class.getSimpleName());
            SEMAPHORE.acquire();
            AtomicInteger totalAmount = new AtomicInteger(0);
        int points = 0;
        while (points < 100) {
            int amountOfGasToCreateDetail = RANDOM.nextInt(351) + 350;
            totalAmount.addAndGet(amountOfGasToCreateDetail);
                if (FACTORY.getAmountOfGas() < amountOfGasToCreateDetail) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    LOGGER.info("We are waiting for gas.");
                    continue;
                }
                points += 10;
                FACTORY.setAmountOfGas(FACTORY.getAmountOfGas() - amountOfGasToCreateDetail);
            try {
                TimeUnit.SECONDS.sleep(1);
                LOGGER.info("Reset.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        final Detail detail = FACTORY.getDetails().get(0);
        FACTORY.getDetails().remove(0);
        detail.setSeconds((int) (LocalDateTime.now().toEpochSecond(ZoneOffset.MAX) - detail.getDateTime().toEpochSecond(ZoneOffset.MAX)));
        detail.setAmountOfGas(FACTORY.getAmountOfGas() + totalAmount.get());
        FACTORY.setAmountOfGas(0);
            return DETAIL_SERVICE.save(detail);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOGGER.info("The detail is ready.");
            SEMAPHORE.release();
        }
    }
}

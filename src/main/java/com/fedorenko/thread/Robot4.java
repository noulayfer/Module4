package com.fedorenko.thread;

import com.fedorenko.model.Detail;
import com.fedorenko.model.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Robot4 implements Runnable {
    private static final Random RANDOM = new Random();
    private static final Factory FACTORY = Factory.getInstance();
    private final CountDownLatch robotLatch1;
    private final CountDownLatch robotLatch2;
    private final CountDownLatch latch;
    private static final Semaphore SEMAPHORE = new Semaphore(1);
    private static final Logger LOGGER = LoggerFactory.getLogger(Robot4.class);

    public Robot4(CountDownLatch robotLatch1, CountDownLatch robotLatch2, CountDownLatch latch) {
        this.robotLatch1 = robotLatch1;
        this.robotLatch2 = robotLatch2;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            robotLatch1.await();
            robotLatch2.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            SEMAPHORE.acquire();
            buildMicroSchema();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            SEMAPHORE.release();
        }
        latch.countDown();
    }

    public void buildMicroSchema() {
        LOGGER.info("{} starts work with class {}.", Thread.currentThread(), Robot4.class.getSimpleName());
        int points = 0;
        while (points < 100) {
            points += RANDOM.nextInt(11) + 25;

            if (RANDOM.nextDouble() < 0.3) {
                points = 0;
                final List<Detail> details = FACTORY.getDetails();
                final Detail detail = details.get(details.size() - 1);
                details.remove(detail);
                details.add(0, detail);
                FACTORY.setDetails(details);
                Optional.ofNullable(detail).ifPresent(det ->
                        det.setCountOfBrokenMicroSchemas(det.getCountOfBrokenMicroSchemas() + 1));
                LOGGER.info("Robot 4 encountered a chip failure and had to start over.");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Robot 4 has completed its work with {} points.", points);
    }
}

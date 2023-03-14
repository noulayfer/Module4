package com.fedorenko.util;

import com.fedorenko.model.Detail;
import com.fedorenko.model.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BuildConstruction {
    private final static Random RANDOM = new Random();
    private static final int ASSEMBLY_POINTS_THRESHOLD = 100;
    private static AtomicInteger ASSEMBLY_POINTS = new AtomicInteger(0);
    private static final Factory FACTORY = Factory.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildConstruction.class);
    private static final CyclicBarrier BARRIER = new CyclicBarrier(2, () -> {
        Detail detail = new Detail();
        FACTORY.getDetails().add(detail);
        LOGGER.info("Details created by {}.", Thread.currentThread());
        ASSEMBLY_POINTS = new AtomicInteger(0);
    });
    private static final Semaphore SEMAPHORE = new Semaphore(2);


    public static void assemble() {
        try {
            LOGGER.info("{} starts work with class {}.", Thread.currentThread(), BuildConstruction.class.getSimpleName());
            SEMAPHORE.acquire();
                while (ASSEMBLY_POINTS.get() < ASSEMBLY_POINTS_THRESHOLD) {
                    int progress = RANDOM.nextInt(11) + 10;
                    ASSEMBLY_POINTS.addAndGet(progress);
                    LOGGER.info("{} is assembling the part, progress: {}, total points: {}.",
                            Thread.currentThread(), progress, ASSEMBLY_POINTS.get());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            BARRIER.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
        } finally {
            LOGGER.info("{} has finished assembling the part!", Thread.currentThread());
            SEMAPHORE.release();
        }
    }
}

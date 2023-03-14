package com.fedorenko.thread;

import com.fedorenko.util.BuildConstruction;

import java.util.concurrent.CountDownLatch;

public class Robot3 implements Runnable {
    private final CountDownLatch latch;

    public Robot3(final CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        BuildConstruction.assemble();
        latch.countDown();
    }
}

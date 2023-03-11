package com.fedorenko;

import com.fedorenko.thread.*;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch robot2 = new CountDownLatch(1);
        CountDownLatch robot3 = new CountDownLatch(1);
        CountDownLatch robot4 = new CountDownLatch(1);
        Thread thread1 = new Thread(new Robot1());
        Thread thread2 = new Thread(new Robot2(robot2));
        Thread thread3 = new Thread(new Robot3(robot3));
        Thread thread4 = new Thread(new Robot4(robot2, robot3, robot4));
        Thread thread5 = new Thread(new Robot5(robot4));
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread5.join();
        thread1.interrupt();
    }
}

package com.fedorenko.util;

import com.fedorenko.model.Detail;
import com.fedorenko.thread.*;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.concurrent.*;

public class ExecuteThreads {

    private ExecuteThreads() {
    }

    @SneakyThrows
    public static Detail executeThreads() {
        CountDownLatch robot2 = new CountDownLatch(1);
        CountDownLatch robot3 = new CountDownLatch(1);
        CountDownLatch robot4 = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            Future<?> future1 = executorService.submit(new Robot1());
            Future<?> future2 = executorService.submit(new Robot2(robot2));
            Future<?> future3 = executorService.submit(new Robot3(robot3));
            Future<?> future4 = executorService.submit(new Robot4(robot2, robot3, robot4));
            Future<Detail> future5 = executorService.submit(new Robot5(robot4));
            Detail detail = future5.get();
            future1.cancel(true);
            future2.cancel(true);
            future3.cancel(true);
            future4.cancel(true);
            return Optional.ofNullable(detail).
                    orElseThrow(IllegalStateException::new);
        } finally {
            executorService.shutdown();
        }
    }
}

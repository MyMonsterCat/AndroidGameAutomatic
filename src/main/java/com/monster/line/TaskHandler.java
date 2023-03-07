package com.monster.line;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Random;

/**
 * 任务处理器
 *
 * @author Monster
 */
@Component
@Slf4j
public class TaskHandler {
    @SneakyThrows
    @Async("modelTaskExecutor")
    public void train() {
        log.info(Thread.currentThread().getName() + new Random().nextInt() + "哈山河");
    }

    @SneakyThrows
    @Async("modelTaskExecutor")
    public void delayTask() {

    }


    @Async("modelTaskExecutor")
    public ListenableFuture<String> c(String str) {
        log.info(Thread.currentThread().getName() + new Random().nextInt() + "有结果");
        return new AsyncResult<>(str);

    }

}

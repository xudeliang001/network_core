package com.uca.network.common.utils;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 *
 *
 */
public class CompletionStageExecutor {

    static ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(350);
    /**
     * 异步调用方法，并且将主线程的traceId传递到子线程中
     */
    public static <T> CompletableFuture<T> supplyAsyncWithTraceId(Supplier<T> supplier, Map map) {
        return CompletableFuture.supplyAsync(() -> {
            MDC.setContextMap(map);
            return supplier.get();
        }, threadPoolExecutor);
    }


}

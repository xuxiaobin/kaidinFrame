package com.kaidin.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
public class NaivelyConcurrentTotalFileSize {
    private long getTotalSizeOfFilesInDir(final ExecutorService service,
            final File file) throws InterruptedException, ExecutionException,
            TimeoutException {
        if (file.isFile())
            return file.length();
        long total = 0;
        final File[] children = file.listFiles();
        if (children != null) {
            final List<Future<Long>> partialTotalFutures = new ArrayList<Future<Long>>();
            for (final File child : children) {
                partialTotalFutures.add(service.submit(new Callable<Long>() {
                    public Long call() throws InterruptedException,
                            ExecutionException, TimeoutException {
                        return getTotalSizeOfFilesInDir(service, child);
                    }
                }));
            }
            for (final Future<Long> partialTotalFuture : partialTotalFutures)
                total += partialTotalFuture.get(100, TimeUnit.SECONDS);
        }
        return total;
    }
    public long getTotalSizeOfFile(final String fileName)
            throws InterruptedException, ExecutionException, TimeoutException {
        final ExecutorService service = Executors.newFixedThreadPool(100);
        try {
            return getTotalSizeOfFilesInDir(service, new File(fileName));
        } finally {
            service.shutdown();
        }
    }
}
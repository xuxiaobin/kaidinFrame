package com.kaidin.test;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Test {
	public static final String fileName = "D:/download";

	
	public static void main(final String[] args) throws Exception {
		long costTimes = System.currentTimeMillis();
		long total = new ConcurrentTotalFileSize()
				.getTotalSizeOfFilesInDir(new File(fileName));
		costTimes = System.currentTimeMillis() - costTimes;
		System.out.println("ConcurrentTotalFileSize Size:" + total);
		System.out.println("ConcurrentTotalFileSize taken:" + costTimes);
		
		
		costTimes = System.currentTimeMillis();
        total = new ConcurrentTotalFileSizeWLatch()
                .getTotalSizeOfFile(fileName);
        costTimes = System.currentTimeMillis() - costTimes;
        System.out.println("ConcurrentTotalFileSizeWLatch Size: " + total);
        System.out.println("ConcurrentTotalFileSizeWLatch taken: " + costTimes);
        
        
        costTimes = System.currentTimeMillis();
        total = new ConcurrentTotalFileSizeWQueue()
                .getTotalSizeOfFile(fileName);
        costTimes = System.currentTimeMillis() - costTimes;
        System.out.println("ConcurrentTotalFileSizeWQueue Size: " + total);
        System.out.println("ConcurrentTotalFileSizeWQueue taken: " + costTimes);
        
        
        costTimes = System.currentTimeMillis();
        total = new ForkJoinPool().invoke(new FileSize.FileSizeFinder(new File("fileName")));
        costTimes = System.currentTimeMillis() - costTimes;
        System.out.println("forkJoinPool Size: " + total);
        System.out.println("forkJoinPool taken: " + costTimes);
        
        
        costTimes = System.currentTimeMillis();
        total = new NaivelyConcurrentTotalFileSize()
                .getTotalSizeOfFile(fileName);
        costTimes = System.currentTimeMillis() - costTimes;
        System.out.println("NaivelyConcurrentTotalFileSize Size: " + total);
        System.out.println("NaivelyConcurrentTotalFileSize taken: " + costTimes);
        
        
        costTimes = System.currentTimeMillis();
        total = new TotalFileSizeSequential()
                .getTotalSizeOfFilesInDir(new File(fileName));
        costTimes = System.currentTimeMillis() - costTimes;
        System.out.println("TotalFileSizeSequential Size: " + total);
        System.out.println("TotalFileSizeSequential taken: " + costTimes);
	}
}

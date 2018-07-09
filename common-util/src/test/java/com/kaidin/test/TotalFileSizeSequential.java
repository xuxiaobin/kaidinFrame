package com.kaidin.test;

import java.io.File;
public class TotalFileSizeSequential {
    // 递归方式 计算文件的大小
    public long getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }
}
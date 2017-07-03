package sortAlgorithm;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * merge sort with multithreading
 * Created by Charley on 2017/7/2.
 */
public class mergeSortMulThread {

    static class mergeSortDemo implements Runnable {

        private CountDownLatch countDownLatch;
        private int[] list;
        private int begin;
        private int end;

        public mergeSortDemo(CountDownLatch countDownLatch, int[] list, int begin, int end) {

            this.countDownLatch = countDownLatch;
            this.list = list;
            this.begin = begin;
            this.end = end;
        }

        @Override
        public void run() {

            SortAllDemo.mergeSort(list, begin, end);
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int threadNum = 4;
        ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        int size = 100000000;
        Random random = new Random();
        int[] list = new int[size];
        for (int i = 0; i < size; ++i) {
            list[i] = random.nextInt(256);
        }
        int per = size / threadNum;

        long time = System.currentTimeMillis();
        for (int i = 0; i < threadNum; ++i) {

            int begin = per * i;
            int end = per * (i + 1);
            threadPool.execute(new mergeSortDemo(countDownLatch, list, begin, end - 1));
        }
        System.out.println(threadNum);
        countDownLatch.await();

//        for (int i = 0; i < Math.sqrt(threadNum); ++i) {
//            SortAllDemo.merge(list,);
//        }
        SortAllDemo.merge(list, 0, per -1, per * 2 -1);
        SortAllDemo.merge(list, per * 2, per * 3 - 1, per * 4 - 1);

        SortAllDemo.merge(list, 0, size / 2, size - 1);
        time = System.currentTimeMillis() - time;
        System.out.println("Time: " + time);
        threadPool.shutdownNow();

        threadPool = Executors.newSingleThreadExecutor();
        countDownLatch = new CountDownLatch(1);
        time = System.currentTimeMillis();
        threadPool.execute(new mergeSortDemo(countDownLatch, list, 0, size - 1));
        countDownLatch.await();
        time = System.currentTimeMillis() - time;
        System.out.println("Time Single: " + time);
        threadPool.shutdownNow();
    }
}

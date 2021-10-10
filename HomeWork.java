package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 还没搞懂，这是抄来的，对不起，我跟不上了。
 */

public class HomeWork {

    public static void main(String[] args) {

        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，

        // 异步执行 下面方法


        System.out.printf("当前主线程[%s]\n", Thread.currentThread().getName());
        for (int i = 0; i < 10; i++) {
            runWithWait();
        }
        int result = sum(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }


    /**
     * 使用wait的方式来实现
     */
    private static void runWithWait() {
        long start=System.currentTimeMillis();
        final AtomicInteger num = new AtomicInteger(0);
        Thread thread = new Thread() {
            @Override
            public void run() {
                synchronized (num) {
                    num.set(sum());
                    num.notifyAll();
                }
            }
        };
        thread.start();
        synchronized (num) {
            if (num.get() == 0) {
                try {
                    num.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

       // System.out.println("异步计算结果为："+num.get());

       // System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}

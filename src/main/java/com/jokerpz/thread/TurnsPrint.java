package com.jokerpz.thread;

import java.util.concurrent.TimeUnit;

public class TurnsPrint {
    private static String[] letters = "abcdefghijklmnopqrstuvwxyz".split("");

    private static String[] nums = "12345567890".split("");

    private void printLetters() throws InterruptedException {
        for (String letter : letters) {
            System.out.println(Thread.currentThread().getName() + ":" + letter);
            TimeUnit.SECONDS.sleep(1);
            this.notifyAll();
            this.wait();
        }
    }

    private void printNums() throws InterruptedException {
        for (String num : nums) {
            System.out.println(Thread.currentThread().getName() + ":" + num);
            TimeUnit.SECONDS.sleep(1);
            this.notifyAll();
            this.wait();
        }
    }

    public static void main(String[] args) {
        TurnsPrint t1 = new TurnsPrint();
        new Thread(() -> {
            try {
                t1.printLetters();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                t1.printNums();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

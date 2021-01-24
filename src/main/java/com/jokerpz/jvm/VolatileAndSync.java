package com.jokerpz.jvm;

public class VolatileAndSync {
    int i;
    volatile int j;

    synchronized void syncDemo() {

    }

    public static void main(String[] args) {
        int i = 8;
        // i = i++;
        i = ++i;
        System.out.println(i);
    }
}

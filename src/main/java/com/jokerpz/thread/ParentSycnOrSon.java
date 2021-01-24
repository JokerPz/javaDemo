package com.jokerpz.thread;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ParentSycnOrSon {
    public static void main(String[] args) throws InterruptedException {
        SonSycnDeom s1 = new SonSycnDeom();

        Thread t1 = new Thread(() -> {
            try {
                System.out.println("son will go to sleep");
                s1.toSleep("son");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            // parentSycnDemo p1 = new parentSycnDemo();
            try {
                System.out.println("parent will go to sleep");
                s1.toWake("son");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }
}

class ParentSycnDemo {
    public  void toSleep(String name) throws InterruptedException {
        synchronized (this) {
            System.out.println(name + " will sleep");
            TimeUnit.SECONDS.sleep(2);
            LinkedList<Integer> linkedList = new LinkedList<>();
            List<Integer> arrayList = new ArrayList<>();
            arrayList.iterator();
        }
    }
}

class SonSycnDeom extends ParentSycnDemo {
    public void toWake(String name) throws InterruptedException {
        synchronized (this) {
            System.out.println(name + " will wake up");
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        a.add(12);
        a.add(13);
        a.add(14);
        Iterator<Integer> b = a.iterator();
        while (b.hasNext()) {
            if (Objects.equals(13, b.next())) {
                a.add(113);

                Map map = new HashMap();
                // map.put();
                map.entrySet();
            }
        }
    }
}

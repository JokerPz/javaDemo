package com.jokerpz.thread;

import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TurnsPrint {
    private static String[] letters = "abcdefghijklmnopqrstuvwxyz".split("");

    private static String[] nums = "12345567890".split("");
    private static int numIndex = 0;
    public Object lock = new Object();
    public void printLetters() throws InterruptedException {
        synchronized(lock) {
            for (String letter : letters) {
                System.out.println(Thread.currentThread().getName() + ":" + letter);
//                TimeUnit.SECONDS.sleep(1);
                lock.notifyAll();
                if (numIndex <= 10) {
                    lock.wait();
                }
            }
        }
    }

    private void printNums() throws InterruptedException {
        synchronized (lock) {
            for (String num : nums) {
                numIndex++;
                System.out.println(Thread.currentThread().getName() + ":" + num + "[" + numIndex + "]");
//                TimeUnit.SECONDS.sleep(1);
                lock.notifyAll();
                if (numIndex <= 10) {
                    lock.wait();
                }
            }
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

class T03_NotifyHoldingLock { //wait notify

    //添加volatile，使t2能够得到通知
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        T03_NotifyHoldingLock c = new T03_NotifyHoldingLock();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized(lock) {
                System.out.println("t2启动");
                if(c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
            }

        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1启动");
            synchronized(lock) {
                for(int i=0; i<10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);

                    if(c.size() == 5) {
                        lock.notify();
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();
    }
}

class ReentranlockDemo {
    volatile List<Object> list = new ArrayList<>();

    public void add(Object object) {
        list.add(object);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        ReentranlockDemo reDemo = new ReentranlockDemo();
        Lock relock = new ReentrantLock();
        new Thread(() -> {
            relock.lock();
            try {

            } finally {

            }
        })  ;


    }
}

class T04_ReentrantLock4 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Thread t1 = new Thread(()->{
            try {
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        Thread t2 = new Thread(()->{
            try {
//                lock.lock();
                lock.lockInterruptibly(); //可以对interrupt()方法做出响应
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            } finally {
                lock.unlock();
            }
        });
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        t1.start();

        t2.interrupt(); //打断线程2的等待

    }
}




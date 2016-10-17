package ru.bender.learnjava.threadtests;


public class ThreadTest {
    public static void main(String[] args) {
        ThreadTest app = new ThreadTest();
        app.go();
    }

    private void go() {
        MyRunnable run1 = new MyRunnable();
        Thread run1Thred = new Thread(run1);
        run1Thred.setName("1");
        MyRunnable run2 = new MyRunnable();
        Thread run2Thred = new Thread(run2);
        run2Thred.setName("2");

        run1Thred.start();
        run2Thred.start();
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 25; i++) {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName);
            }
        }
    }

}

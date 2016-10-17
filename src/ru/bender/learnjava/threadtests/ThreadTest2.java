package ru.bender.learnjava.threadtests;

public class ThreadTest2 {
    int i = 0;
    public static void main(String[] args) {
        ThreadTest2 app = new ThreadTest2();
        app.go();
    }

    private void go() {
        Thread t1 = new Thread(new Incrementer());
        Thread t2 = new Thread(new Incrementer());
        t1.start();
        t2.start();
    }

    private synchronized void incremet() {
        int t = i + 1;
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i = t;
    }

    class Incrementer implements Runnable{
        @Override
        public void run() {
            for (int j = 0; j < 50; j++) {
                incremet();
            }
            System.out.println(i);
        }
    }
}


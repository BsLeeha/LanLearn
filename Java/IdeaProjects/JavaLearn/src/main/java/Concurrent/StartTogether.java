package Concurrent;

/*
 * 10 个 racer 线程等待同一个开始信号
 */

public class StartTogether {
    static class FireFlag {
        private volatile boolean fired = false;

        public synchronized void waitForFire() throws InterruptedException {
            while (!fired) {
                wait();
            }
        }

        public synchronized void fire() {
            fired = true;
            notifyAll();
        }
    }

    static class Racer extends Thread {
        FireFlag fireFlag;

        public Racer(FireFlag fireFlag) {
            this.fireFlag = fireFlag;
        }

        @Override
        public void run() {
            try {
                fireFlag.waitForFire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Start run: " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] racers = new Racer[10];
        FireFlag fireFlag = new FireFlag();

        for (int i = 0; i < 10; i++) {
            racers[i] = new Racer(fireFlag);
            racers[i].start();
        }

        Thread.sleep(5000);
        fireFlag.fire();
    }
}

package rbit.machine;

class Releaser implements Runnable {
    Machine machine;
    volatile boolean dead = false;
    Releaser(Machine machine) {
        this.machine = machine;
        Thread t = new Thread(this);
        t.start();
    }

    void kill() {
        dead = true;
    }

    @Override
    public void run() {
        while (!dead) {
            synchronized (machine) {
                machine.notify();
                try {
                    machine.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
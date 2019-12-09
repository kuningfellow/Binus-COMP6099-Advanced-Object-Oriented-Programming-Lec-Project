package rbit.machine;

class Player implements Runnable {
    Machine machine;
    int curBeat;
    int curSubBeat;
    long wait;
    Releaser releaser;
    volatile boolean dead = false;

    Player(Machine machine) {
        this.machine = machine;
        curBeat = curSubBeat = 0;
        calculateWait();
        // releaser = new Releaser(machine);
        Thread t = new Thread(this);
        t.start();
    }

    void calculateWait() {
        double tmp = (double) 1000.0 * 60.0 / (machine.arrangement.getTempo() * machine.arrangement.getSubTempo());
        wait = (long) tmp;
        // System.out.println(wait);
    }

    void kill() {
        dead = true;
        releaser.kill();
    }

    @Override
    public void run() {
        while (!dead) {
            // synchronized (machine) {
                machine.trigger(curBeat, curSubBeat);
                curSubBeat++;
                if (curSubBeat >= machine.arrangement.getSubTempo()) {
                    curSubBeat = 0;
                    curBeat++;
                }
                if (curBeat >= machine.arrangement.getLength()) {
                    curBeat = 0;
                }
                // machine.notify();
                // try {
                //     machine.wait();
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }
            // }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
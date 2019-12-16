package rbit.machine;

// Plays the arrangement according to tempo
class Player implements Runnable {
    Machine machine;
    int curBeat;
    int curSubBeat;
    long wait;
    volatile boolean dead = false;

    Player(Machine machine) {
        this.machine = machine;
        curBeat = curSubBeat = 0;
        calculateWait();
        Thread t = new Thread(this);
        t.start();
    }

    void calculateWait() {
        double tmp = (double) 1000.0*60.0 / ( machine.arrangement.getTempo() * (1<<machine.arrangement.getSubTempo()) );
        wait = (long) tmp;
    }

    void kill() {
        dead = true;
    }

    @Override
    public void run() {
        while (!dead) {
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (machine) {
                if ( curSubBeat >= (1<<machine.arrangement.getSubTempo()) ) {
                    curSubBeat = 0;
                    curBeat++;
                }
                if (curBeat >= machine.arrangement.getLength()) {
                    curBeat = 0;
                }
                machine.trigger(curBeat, curSubBeat);
                curSubBeat++;
            }
        }
    }
}
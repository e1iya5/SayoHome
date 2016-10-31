package sayohome;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Throws event once a minute.
 * @author elias
 */
public class TimeEventPollingService implements Runnable {
    private final BlockingQueue channel;
    private int lastMinEvent = -1;
    public TimeEventPollingService(BlockingQueue channel){
        this.channel = channel; 
    }
    @Override
    public void run() {
        while(true){
            try {
                Date d = new Date();
                if(lastMinEvent != d.getMinutes()){
                    channel.put(new TimeEvent(d));
                    lastMinEvent = d.getMinutes();
                }
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TimeEventPollingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

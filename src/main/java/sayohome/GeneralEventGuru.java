package sayohome;

import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import rules.TimePattern;
import rules.TimeRule;
import rules.TimeRuleChain;

/**
 * Ich kümmere mich um alle möglichen Ereignisse. :)
 *
 * @author elias
 */
public class GeneralEventGuru implements Runnable {
    
    private final BlockingQueue eventChannel;
    
    private TimeRuleChain timeRules;
    
    private void refreshTimeRules(RefreshTimeRulesEvent ev) {
        this.timeRules = new TimeRuleChain();
        for(TimeRule r: ev.getBody()){
            this.timeRules.addRule(r);
        }
    }
    
    public GeneralEventGuru(LinkedBlockingQueue<Event> channel) throws SQLException {
        this.eventChannel = channel;
        this.timeRules = new TimeRuleChain();
    }
    
    
    @Override
    public void run() {
        LinkedBlockingQueue<DeviceEvent> deviceChannel = new LinkedBlockingQueue<DeviceEvent>();
        DeviceManager deviceManager = new DeviceManager(deviceChannel);
        new Thread(deviceManager).start();
        
        while (true) {
            try {
                Event evObj = (Event) eventChannel.take();
                if (evObj instanceof DeviceEvent) {
                    deviceChannel.put((DeviceEvent) evObj);
                }
                if(evObj instanceof RefreshTimeRulesEvent){
                    this.refreshTimeRules((RefreshTimeRulesEvent) evObj);
                }
                if (evObj instanceof TimeEvent) {
                    TimeEvent ev = (TimeEvent) evObj;
                    this.timeRules.execute(ev.getBody());
                }
            } catch (InterruptedException ex) {
                
            }
        }
    }
}

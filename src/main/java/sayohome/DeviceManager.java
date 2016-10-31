/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sayohome;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elias
 */
public class DeviceManager implements Runnable {

    private LinkedBlockingQueue<DeviceEvent> deviceChannel;
    private HashMap<String, Device> deviceList;
    public DeviceManager(LinkedBlockingQueue<DeviceEvent> deviceChannel) {
        this.deviceChannel = deviceChannel;
        this.deviceList = new HashMap<String, Device>();
    }

    @Override
    public void run() {
        while(true){
            try {
                DeviceEvent ev = deviceChannel.take();
                // TODO: Handle Device Event
                System.out.println("device event");
                System.out.println(ev);
            } catch (InterruptedException ex) {
               
            }
        }
    }

}

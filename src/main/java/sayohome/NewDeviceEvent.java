/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sayohome;

/**
 *
 * @author elias
 */
public class NewDeviceEvent implements DeviceEvent {
    private Device device;
    public NewDeviceEvent(Device device){
        this.device = device;
    }
    
    @Override
    public Device getBody() {
        return this.device;
    }
    
}

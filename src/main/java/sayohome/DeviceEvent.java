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
public interface DeviceEvent extends Event {
    @Override
    public Device getBody();
}

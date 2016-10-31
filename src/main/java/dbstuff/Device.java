/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff;

/**
 *
 * @author elias
 */
public class Device {
    private int id;
    private String ip;
    private String name;
    public Device(int id, String ip, String name){
        this.id = id;
        this.ip = ip;
        this.name = name;
    }
}

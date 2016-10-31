/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sayohome;

import shcp.ShcpResponse;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author elias
 */
public class Device {

    private String id;
    private Socket socket;

    public Device(String id, Socket socket) {
        this.id = id;
        this.socket = socket;
    }

    public void writeResponse(ShcpResponse res) {
        try {
            socket.getOutputStream().write(res.toString().getBytes());
        } catch (IOException ex) {

        }
    }

    public String getId() {
        return this.id;
    }
}

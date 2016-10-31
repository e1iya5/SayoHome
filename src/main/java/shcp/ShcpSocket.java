/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shcp;

import dbstuff.Session;
import shcp.ShcpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import sayohome.Event;
import shcp.requesthandler.RequestHandler;

/**
 * Socket for the "SayoHome Controlling Protocol"
 *
 * @author elias
 */
public class ShcpSocket implements Runnable {

    private ServerSocket tcpServer;
    private final LinkedBlockingQueue<Event> eventChannel;

    public ShcpSocket(LinkedBlockingQueue<Event> eventChannel) {
        this.eventChannel = eventChannel;
    }

    @Override
    public void run() {
        try {
            tcpServer = new ServerSocket(2620);
        } catch (IOException ex) {
            Logger.getLogger(ShcpSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                Socket s = tcpServer.accept();
                ShcpHandler handler = new ShcpHandler(s, this.eventChannel);

                // Login-Handler
                handler.addRequestHandler("login", new RequestHandler() {
                    @Override
                    public ShcpResponse handle(ShcpCommand datagram, Session session) {
                        if(session == null) return new ShcpResponse("lololol");
                        return new ShcpResponse(session.getCode());
                    }
                });

                new Thread(handler).start();
            } catch (IOException ex) {
                Logger.getLogger(ShcpSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

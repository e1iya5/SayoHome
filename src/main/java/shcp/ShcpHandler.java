/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shcp;

import dbstuff.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import sayohome.Event;
import sayohome.SayoHome;
import shcp.requesthandler.RequestHandler;

/**
 *
 * @author elias
 */
public class ShcpHandler implements Runnable {

    private Socket socket = null;
    private BufferedReader inputStream = null;
    private OutputStream outputStream = null;
    private final LinkedBlockingQueue<Event> eventChannel;
    private HashMap<String, RequestHandler> handlerList;
    // private Session currentSession = null;

    public ShcpHandler(Socket socket, LinkedBlockingQueue<Event> eventChannel) {
        this.socket = socket;
        this.eventChannel = eventChannel;
        this.handlerList = new HashMap<String, RequestHandler>();
        try {
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outputStream = socket.getOutputStream();
        } catch (IOException ex) {

        }
    }

    private void writeResponse(ShcpResponse response) throws IOException {
        this.outputStream.write(response.toString().getBytes());
    }

    private void runMethodHandler(String method, ShcpCommand datagram) throws IOException, SQLException, ParseException {
        RequestHandler rh = this.handlerList.get(method);
        if (rh == null) {
            this.writeResponse(new ShcpResponse("invalid_method"));
            return;
        }
        Session s = null;
        if (datagram.getToken() != null) {
            try {
                s = SayoHome.getDbGuru().getSessionByToken(datagram.getToken());
            } catch (IllegalArgumentException e) {
            }
        }
        ShcpResponse res = rh.handle(datagram, s);
        this.writeResponse(res == null ? new ShcpResponse("null") : rh.handle(datagram, null));
    }

    @Override
    public void run() {
        try {
            writeResponse(new ShcpResponse("connected_to_sayohome"));
        } catch (IOException ex) {

        }
        while (true) {
            try {
                String inputString = inputStream.readLine();
                if (inputString == null) {
                    continue;
                }
                System.out.println(inputString);
                ShcpCommand datagram = new ShcpCommand(inputString);
                System.out.println("method: " + datagram.getMethod());
                System.out.println("token: " + datagram.getToken());
                System.out.println("body: " + datagram.getBody());
                if (datagram.getMethod() == null) {
                    writeResponse(new ShcpResponse("invalid_command"));
                    continue;
                }
                this.runMethodHandler(datagram.getMethod(), datagram);
            } catch (SocketException e) { // if connection is already closed
                System.out.println("connection closed by client.");
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ShcpHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                Thread.currentThread().stop();
            } catch (IOException ex) {

            } catch (SQLException ex) {
                Logger.getLogger(ShcpHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ShcpHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void login(ShcpCommand datagram) throws IOException {
        /*if (datagram.getParam("username") == null || datagram.getParam("pwd") == null) {
            return;
        }
        Session s = null;
        try {
            s = SayoHome.getDbGuru().login(datagram.getParam("username"), datagram.getParam("pwd"));
        } catch (SQLException ex) {
            Logger.getLogger(ShcpHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            this.writeResponse(new ShcpResponse("wrong_username_or_pwd"));
            return;
        } catch (ParseException ex) {
            Logger.getLogger(ShcpHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.writeResponse(new ShcpResponse("authentication_successfully").addParam("token", s.getCode()));*/
    }

    public void addRequestHandler(String methodName, RequestHandler requestHandler) {
        this.handlerList.put(methodName, requestHandler);
    }
}

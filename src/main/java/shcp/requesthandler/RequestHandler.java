/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shcp.requesthandler;

import dbstuff.Session;
import shcp.ShcpCommand;
import shcp.ShcpResponse;

/**
 *
 * @author elias
 */
public class RequestHandler {
    public ShcpResponse handle(ShcpCommand datagram, Session session){
        return new ShcpResponse("null");
    };
}

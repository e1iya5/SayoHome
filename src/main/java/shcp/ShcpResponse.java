/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shcp;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author elias
 */
public class ShcpResponse {

    private String method = null;
    private HashMap<String, String> params = null;

    public ShcpResponse(String method) {
        this.method = method;
        this.params = new HashMap<String, String>();
    }

    @Override
    public String toString() {
        String a = this.method + "!";
        for (Entry<String, String> e : this.params.entrySet()) {
            a += e.getKey() + "=" + e.getValue() + "&";
        }
        a += "\r\n";
        return a;
    }

    public ShcpResponse addParam(String key, String value) {
        this.params.put(key, value);
        return this;
    }
}

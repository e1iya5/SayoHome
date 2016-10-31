/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsstuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author elias
 */
public class HttpGetRequestObject {

    private String url = null;
    private String result = null;

    public void HttpGetRequestObject(String url) {
        this.url = url;
    }
    
    public void send() throws MalformedURLException, IOException {
        StringBuilder r = new StringBuilder();
        URL u = new URL(this.url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            r.append(line);
        }
        rd.close();
        this.result = r.toString();
    }
    
    public String getResultString() {
        return this.result;
    }
}

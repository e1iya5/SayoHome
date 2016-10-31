/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsstuff;

import java.util.HashMap;

/**
 *
 * @author elias
 */
// Das ist das SayoHome-Objekt in der JS-Umgebung
public class SayoHomeObject {

    private static HashMap<String, ServiceObject> services = null;

    public static void initServices() {
        if (services == null) {
            services = new HashMap<String, ServiceObject>();
        }
    }

    public static void clearServices() {
        services = null;
        initServices();
    }

    public static ServiceObject getService(String name) {
        return services.get(name);
    }

    public static void installService(String name, ServiceObject service) {
        services.put(name, service);
    }

    public static void println(String msg) {
        System.out.println(msg);
    }
}

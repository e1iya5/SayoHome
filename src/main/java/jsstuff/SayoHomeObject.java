/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsstuff;

import org.mozilla.javascript.ScriptableObject;

/**
 *
 * @author elias
 */
// Das ist das SayoHome-Objekt in der JS-Umgebung
public class SayoHomeObject extends ScriptableObject {

    public SayoHomeObject() {
        
    }
    

    public String getClassName() {
        return "sayohome";
    }
    
    public void jsConstructor() {
    
    }
    
    public void jsFunction_println(String msg) {
        System.out.println(msg);
    }
}

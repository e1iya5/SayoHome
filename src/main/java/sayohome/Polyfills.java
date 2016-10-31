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
public class Polyfills {
    public static String stringArrayToSingleString(String[] arr){
        String s = "";
        for(int i=0; i<arr.length; ++i){
            s += arr[i];
        }
        return s;
    }
    public static String inputStreamArrayToSingleString(Object[] arr){
        String s = "";
        for(int i=0; i<arr.length; ++i){
            s += arr[i].toString()+"\n";
        }
        return s;
    }
}

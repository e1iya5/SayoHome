/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsstuff;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import sayohome.SayoHome;

/**
 *
 * @author elias
 */
public class LogEntryObject {

    private Time time;
    private String msg;
    private int entryMode;
    
    public static int INFO = 1;
    public static int WARNING = 2;
    public static int ERROR = 3;
    
    public LogEntryObject(Time time, String msg, int mode){
        this.time = time;
        this.msg = msg;
        this.entryMode = mode;
    }
    
    public void jsConstructor(String msg) {
        this.entryMode = 1;
        this.msg = msg;
        this.time = new Time(new Date().getTime());
    }
    
    public int getEntryMode(){
        return this.entryMode;
    }
    
    public String getMessage(){
        return this.msg;
    }
    
    public Time getTime(){
        return this.time;
    }

    public LogEntryObject setMode(int mode) {
        this.entryMode = mode;
        return this;
    }
    
    public void save() throws SQLException{
        SayoHome.getDbGuru().createLogEntry(this);
    }
}

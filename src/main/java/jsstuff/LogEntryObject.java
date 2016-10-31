/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsstuff;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import org.mozilla.javascript.ScriptableObject;
import sayohome.SayoHome;

/**
 *
 * @author elias
 */
public class LogEntryObject extends ScriptableObject{

    private Time time;
    private String msg;
    private int entryMode;

    public LogEntryObject(){
    
    }
    
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

    public String getClassName() {
        return "LogEntry";
    }

    public LogEntryObject jsFunction_setMode(int mode) {
        this.entryMode = mode;
        return this;
    }
    
    public void jsFunction_save() throws SQLException{
        SayoHome.getDbGuru().createLogEntry(this);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import java.util.Date;

/**
 *
 * @author elias
 */

public class TimePattern {
    private final int year; 
    private final int month;
    private final int dayOfMonth;
    private final int dayOfWeek;
    private final int hour;
    private final int minute;
    
    public TimePattern(int year, int month, int dayOfMonth, int dayOfWeek, int hour, int minute){
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.minute = minute;
    }
    
    public boolean isElement(Date date){
        boolean a = true;
        a = a && ((this.year < 0) || this.year == date.getYear()+1900);
        a = a && ((this.month < 0) || this.month == date.getMonth());
        a = a && ((this.dayOfMonth < 0) || this.dayOfMonth == date.getDate());
        a = a && ((this.dayOfWeek < 0) || this.dayOfWeek == date.getDay());
        a = a && ((this.hour < 0) || this.hour == date.getHours());
        a = a && ((this.minute < 0) || this.minute == date.getMinutes());
        return a;
    }
    
    public int getYear(){
        return this.year;
    }
    
    public int getMonth(){
        return this.month;
    }
    
    public int getDayOfMonth(){
        return this.dayOfMonth;
    }
    
    public int getDayOfWeek(){
        return this.dayOfWeek;
    }
    
    public int getHour(){
        return this.hour;
    }
    
    public int getMinute(){
        return this.minute;
    }
}

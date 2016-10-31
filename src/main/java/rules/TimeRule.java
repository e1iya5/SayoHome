/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import java.util.Date;
import java.util.ArrayList;
import jsstuff.ScriptingEnviroment;

/**
 *
 * @author elias
 */
public class TimeRule implements Runnable {

    private ArrayList<TimePattern> timePattern;
    private String ruleBody;
    private boolean active;
    private int id;
    private String name;

    public boolean isTime(Date date) {
        for (TimePattern p : timePattern) {
            if (p.isElement(date)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<TimePattern> getPatterns() {
        return this.timePattern;
    }

    public String getCode() {
        return this.ruleBody;
    }

    public TimeRule(String name, boolean active, ArrayList<TimePattern> timePattern, String ruleBody) {
        this.name = name;
        this.active = active;
        this.timePattern = timePattern;
        this.ruleBody = ruleBody;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public void run() {
        ScriptingEnviroment env = new ScriptingEnviroment();
        env.runScript(this.ruleBody);
    }
}

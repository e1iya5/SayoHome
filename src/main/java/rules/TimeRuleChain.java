/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rules;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author elias
 */
public class TimeRuleChain {

    Vector<TimeRule> rules;

    public TimeRuleChain() {
        this.rules = new Vector<TimeRule>();
    }

    public void addRule(TimeRule rule){
        this.rules.add(rule);
    }
    
    public void execute(Date date){
        rules.stream().filter((rule) -> (rule.isTime(date))).forEach((rule) -> {
            (new Thread(rule)).start();
        });
    }
}

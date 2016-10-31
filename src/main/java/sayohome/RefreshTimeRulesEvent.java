/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sayohome;

import java.util.ArrayList;
import rules.TimeRule;

/**
 *
 * @author elias
 */
public class RefreshTimeRulesEvent implements Event {

    private ArrayList<TimeRule> rules;

    public RefreshTimeRulesEvent(ArrayList<TimeRule> rules) {
        this.rules = rules;
    }

    @Override
    public ArrayList<TimeRule> getBody() {
        return this.rules;
    }

}

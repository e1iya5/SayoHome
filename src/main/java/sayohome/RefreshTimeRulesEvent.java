/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sayohome;

import java.util.Vector;
import rules.TimeRule;

/**
 *
 * @author elias
 */
public class RefreshTimeRulesEvent implements Event {

    private Vector<TimeRule> rules;

    public RefreshTimeRulesEvent(Vector<TimeRule> rules) {
        this.rules = rules;
    }

    @Override
    public Vector<TimeRule> getBody() {
        return this.rules;
    }

}

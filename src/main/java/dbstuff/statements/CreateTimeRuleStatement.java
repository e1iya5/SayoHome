/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff.statements;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rules.TimeRule;

/**
 *
 * @author elias
 */
public class CreateTimeRuleStatement extends Statement {

    TimeRule r;

    public CreateTimeRuleStatement(TimeRule r) {
        this.r = r;
    }

    @Override
    public PreparedStatement getPreparedStatement(Connection c) {
        Gson gson = new Gson();
        PreparedStatement s = null;
        try {
            s = c.prepareStatement("INSERT INTO TimeRule (name, trigger, code, active, permissions) VALUES (?, ?, ?, 1, '{}');");
            s.setString(1, this.r.getName());
            s.setString(2, gson.toJson(this.r.getPatterns()));
            s.setString(3, this.r.getCode());
        } catch (SQLException ex) {
            Logger.getLogger(CreateTimeRuleStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}

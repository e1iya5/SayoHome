/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff.statements.timerules;

import dbstuff.statements.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elias
 */
public class GetAllTimeRulesStatement extends Statement {
    @Override
    public PreparedStatement getPreparedStatement(Connection c) {
        PreparedStatement s = null;
        try {
            s = c.prepareStatement("SELECT * FROM TimeRule;");
        } catch (SQLException ex) {
            Logger.getLogger(CreateTimeRuleStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}

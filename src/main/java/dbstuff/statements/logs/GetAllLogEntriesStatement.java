/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff.statements.logs;

import dbstuff.statements.Statement;
import dbstuff.statements.timerules.CreateTimeRuleStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elias
 */
public class GetAllLogEntriesStatement extends Statement {
    @Override
    public PreparedStatement getPreparedStatement(Connection c) {
        PreparedStatement s = null;
        try {
            s = c.prepareStatement("SELECT * FROM LogEntry ORDER BY time DESC;");
        } catch (SQLException ex) {
            Logger.getLogger(GetAllLogEntriesStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}

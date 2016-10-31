/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff.statements;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsstuff.LogEntryObject;

/**
 *
 * @author elias
 */
public class CreateLogEntryStatement extends Statement {
    LogEntryObject entry;
    
    public CreateLogEntryStatement(LogEntryObject entry){
        this.entry = entry;
    }
    
    @Override
    public PreparedStatement getPreparedStatement(Connection c) {
        Gson gson = new Gson();
        PreparedStatement s = null;
        try {
            s = c.prepareStatement("INSERT INTO LogEntry (time, message, mode) VALUES (?, ?, ?);");
            s.setTime(1, this.entry.getTime());
            s.setString(2, this.entry.getMessage());
            s.setInt(3, this.entry.getEntryMode());
        } catch (SQLException ex) {
            Logger.getLogger(CreateTimeRuleStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}

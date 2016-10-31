/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff.statements;

import com.google.gson.Gson;
import dbstuff.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elias
 */
public class CreateSessionStatement extends Statement {
    private Session s;
    public CreateSessionStatement(Session s){
        this.s = s;
    }
    
    @Override
    public PreparedStatement getPreparedStatement(Connection c) {
        Gson gson = new Gson();
        PreparedStatement s = null;
        try {
            s = c.prepareStatement("INSERT INTO Session (code, createdDate, deathDate, storage, userId) VALUES (?, ?, ?, '{}', ?);");
            s.setString(1, this.s.getCode());
            s.setString(2, this.s.getFormattedCreationDate());
            s.setString(3, this.s.getFormattedDeathDate());
            s.setString(4, this.s.getUserId());
        } catch (SQLException ex) {
            Logger.getLogger(CreateTimeRuleStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}

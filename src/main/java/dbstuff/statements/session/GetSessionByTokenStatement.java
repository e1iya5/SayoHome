/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff.statements.session;

import dbstuff.statements.timerules.CreateTimeRuleStatement;
import com.google.gson.Gson;
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
public class GetSessionByTokenStatement extends Statement {

    private String token;

    public GetSessionByTokenStatement(String token) {
        this.token = token;
    }

    @Override
    public PreparedStatement getPreparedStatement(Connection c) {
        Gson gson = new Gson();
        PreparedStatement s = null;
        try {
            s = c.prepareStatement("Select * from Session where code=?;");
            s.setString(1, this.token);
        } catch (SQLException ex) {
            Logger.getLogger(CreateTimeRuleStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}

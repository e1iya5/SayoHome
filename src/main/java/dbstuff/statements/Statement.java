/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff.statements;

import dbstuff.statements.user.GetUserByNameStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elias
 */
public class Statement {
    public PreparedStatement getPreparedStatement(Connection c){
        return null;
    }
    public void run(Connection c) throws SQLException {
        this.getPreparedStatement(c).execute();
    };
    public ResultSet getResult(Connection c) throws SQLException {
        return this.getPreparedStatement(c).executeQuery();
    };
}

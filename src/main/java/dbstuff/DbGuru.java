/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbstuff;

import com.google.gson.Gson;
import dbstuff.statements.enviromentscripts.GetAllEnviromentScriptsStatement;
import dbstuff.statements.enviromentscripts.UpdateEnviromentScriptStatement;
import dbstuff.statements.logs.CreateLogEntryStatement;
import dbstuff.statements.session.CreateSessionStatement;
import dbstuff.statements.timerules.CreateTimeRuleStatement;
import dbstuff.statements.logs.GetAllLogEntriesStatement;
import dbstuff.statements.timerules.GetAllTimeRulesStatement;
import dbstuff.statements.session.GetOpenSessionsStatement;
import dbstuff.statements.session.GetSessionByTokenStatement;
import dbstuff.statements.user.GetUserByIdStatement;
import dbstuff.statements.user.GetUserByNameStatement;
import dbstuff.statements.timerules.UpdateTimeRuleStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import jsstuff.EnviromentScript;
import jsstuff.LogEntryObject;
import rules.TimePattern;
import rules.TimeRule;

/**
 *
 * @author elias
 */
public class DbGuru {

    private Connection c;

    /**
     *
     * @param dbPath
     * @throws SQLException
     */
    public DbGuru(String dbPath) throws SQLException {
        c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        this.initDbModel();
    }

    private boolean existsTable(String tableName) throws SQLException {
        ResultSet rs = this.c.getMetaData().getTables(null, null, tableName, null);
        return rs.next();
    }

    private void initDbModel() throws SQLException {
        /* System.out.println("+++ Table check +++");
        System.out.println("User: " + this.existsTable("User"));
        System.out.println("Session: " + this.existsTable("Session"));
        System.out.println("Device: " + this.existsTable("Device"));
        System.out.println("");*/
    }

    private ResultSet getStatementResult(dbstuff.statements.Statement s) throws SQLException {
        return s.getResult(this.c);
    }

    private void runStatement(dbstuff.statements.Statement s) throws SQLException {
        s.run(this.c);
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public User getUserById(String id) throws SQLException {
        ResultSet rs = this.getStatementResult(new GetUserByIdStatement(id));
        if (!rs.next()) {
            return null;
        }
        return new User(rs.getString("id"), rs.getString("username"));
    }

    /**
     *
     * @param username
     * @param pwd
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws ParseException
     */
    public Session login(String username, String pwd) throws SQLException, IllegalArgumentException, ParseException {
        ResultSet rs = this.getStatementResult(new GetUserByNameStatement(username));
        if (!rs.next()) {
            throw new IllegalArgumentException("wrong username");
        }

        User u = new User(rs.getString("id"), rs.getString("username"));
        u.setHashedPwd(rs.getString("pwd"));
        if (!u.correctPwd(pwd)) {
            throw new IllegalArgumentException("incorrect pwd");
        }

        Session s = null;

        rs = this.getStatementResult(new GetOpenSessionsStatement(u));
        if (rs.next()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            s = new Session(u, rs.getString("code"), this, sdf.parse(rs.getString("createdDate")),
                    (rs.getString("deathDate").equals("null") ? null : sdf.parse(rs.getString("deathDate"))), new HashMap<>());
            if (!s.isValid()) {
                s = new Session(u, this, null);
                this.runStatement(new CreateSessionStatement(s));
            }
        } else {
            s = new Session(u, this, null);
            this.runStatement(new CreateSessionStatement(s));
        }
        return s;
    }

    /**
     *
     * @param token
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public Session getSessionByToken(String token) throws SQLException, ParseException {
        ResultSet rs = this.getStatementResult(new GetSessionByTokenStatement(token));
        if (!rs.next()) {
            throw new IllegalArgumentException("invalid token");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Session s = new Session(this.getUserById(rs.getString("userId")), rs.getString("code"), this, sdf.parse(rs.getString("createdDate")),
                (rs.getString("deathDate").equals("null") ? null : sdf.parse(rs.getString("deathDate"))), new HashMap<>());
        return s;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<TimeRule> getAllTimeRules() throws SQLException {
        ResultSet rs = this.getStatementResult(new GetAllTimeRulesStatement());
        ArrayList<TimeRule> result = new ArrayList<TimeRule>();
        while (rs.next()) {
            Gson gson = new Gson();
            TimePattern[] pattern = gson.fromJson(rs.getString("trigger"), TimePattern[].class);
            ArrayList patternNew = new ArrayList<TimePattern>();
            for (Object p : pattern) {
                patternNew.add(p);
            }
            TimeRule rule = new TimeRule(rs.getString("name"), rs.getBoolean("active"), patternNew, rs.getString("code"));
            rule.setId(rs.getInt("id"));
            result.add(rule);
        }
        return result;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<TimeRule> getActiveTimeRules() throws SQLException {
        ArrayList<TimeRule> all = this.getAllTimeRules();
        ArrayList<TimeRule> result = new ArrayList<TimeRule>();
        for (TimeRule rule : all) {
            if (rule.isActive()) {
                result.add(rule);
            }
        }
        return result;
    }

    /**
     *
     * @param rule
     * @throws SQLException
     */
    public void createTimeRule(TimeRule rule) throws SQLException {
        this.runStatement(new CreateTimeRuleStatement(rule));
    }

    /**
     *
     * @param rule
     * @throws SQLException
     */
    public void updateTimeRule(TimeRule rule) throws SQLException {
        this.runStatement(new UpdateTimeRuleStatement(rule));
    }

    /**
     * @param entry
     * @throws java.sql.SQLException
     */
    public void createLogEntry(LogEntryObject entry) throws SQLException {
        this.runStatement(new CreateLogEntryStatement(entry));
    }

    /**
     *
     * @return @throws SQLException
     */
    public ArrayList<LogEntryObject> getAllLogEntries() throws SQLException {
        ResultSet rs = this.getStatementResult(new GetAllLogEntriesStatement());
        ArrayList<LogEntryObject> entries = new ArrayList<LogEntryObject>();
        while (rs.next()) {
            LogEntryObject entry = new LogEntryObject(rs.getTime("time"), rs.getString("message"), rs.getInt("mode"));
            entries.add(entry);
        }
        return entries;
    }

    /**
     * get all enviroment scripts
     *
     * @return
     * @throws java.sql.SQLException *
     */
    public ArrayList<EnviromentScript> getAllEnviromentScripts() throws SQLException {
        ResultSet rs = this.getStatementResult(new GetAllEnviromentScriptsStatement());
        ArrayList<EnviromentScript> scripts = new ArrayList<>();
        while (rs.next()) {
            EnviromentScript script = new EnviromentScript(rs.getInt("id"), rs.getString("title"), rs.getString("code"), rs.getBoolean("active"));
            scripts.add(script);
        }
        return scripts;
    }

    /**
     *
     * @return @throws SQLException
     */
    public ArrayList<EnviromentScript> getActiveEnviromentScripts() throws SQLException {
        ArrayList<EnviromentScript> results = new ArrayList<EnviromentScript>();
        this.getAllEnviromentScripts()
                .stream()
                .filter(script -> script.isActive())
                .forEach(script -> results.add(script));
        return results;
    }
    
    /**
     *
     * @param s
     * @throws SQLException
     */
    public void updateEnviromentScript(EnviromentScript s) throws SQLException{
        this.runStatement(new UpdateEnviromentScriptStatement(s));
    }
}

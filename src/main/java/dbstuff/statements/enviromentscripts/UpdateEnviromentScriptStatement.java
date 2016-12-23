/*
 * The MIT License
 *
 * Copyright 2016 e1iya5.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dbstuff.statements.enviromentscripts;

import com.google.gson.Gson;
import dbstuff.statements.Statement;
import dbstuff.statements.timerules.CreateTimeRuleStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsstuff.EnviromentScript;

/**
 *
 * @author e1iya5
 */
public class UpdateEnviromentScriptStatement extends Statement {
     EnviromentScript s;

    public UpdateEnviromentScriptStatement(EnviromentScript s) {
        this.s = s;
    }

    @Override
    public PreparedStatement getPreparedStatement(Connection c) {
        Gson gson = new Gson();
        PreparedStatement s = null;
        try {
            s = c.prepareStatement("UPDATE TimeRule SET title=?, code=?, active=? WHERE id=?;");
            s.setString(1, this.s.getTitle());
            s.setString(2, this.s.getCode());
            s.setBoolean(3, this.s.isActive());
            s.setInt(4, this.s.getId());
        } catch (SQLException ex) {
            Logger.getLogger(CreateTimeRuleStatement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
}

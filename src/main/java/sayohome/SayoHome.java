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
package sayohome;

import dbstuff.DbGuru;
import httpstuff.MainServer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import jsstuff.EnviromentScript;
import jsstuff.SayoHomeObject;
import jsstuff.ScriptingEnviroment;

/**
 *
 * @author elias
 */
public class SayoHome {

    /**
     * @param args the command line arguments
     */
    private static LinkedBlockingQueue eventChannel;
    private static DbGuru dbG;

    public static void refreshTimeRules() throws SQLException {
        eventChannel.add(new RefreshTimeRulesEvent(dbG.getActiveTimeRules()));
    }

    public static void main(String[] args) throws Exception {

        // Create Event Channel
        eventChannel = new LinkedBlockingQueue<Event>();

        // Start "DB Guru"
        dbG = new DbGuru(Config.getDbPath());

        // Load Enviroment Scripts
        loadEnviromentScripts();

        // Start "General Event Guru"
        GeneralEventGuru geg = new GeneralEventGuru(eventChannel);
        new Thread(geg).start();

        // Activate Time Rules in GEG
        refreshTimeRules();

        new MainServer();

        // Start "Time Event Polling Service"
        TimeEventPollingService teps = new TimeEventPollingService(eventChannel);
        new Thread(teps).start();
    }

    public static DbGuru getDbGuru() {
        return dbG;
    }

    private static void loadEnviromentScripts() throws SQLException {
        SayoHomeObject.clearServices();
        ScriptingEnviroment env = new ScriptingEnviroment();
        ArrayList<EnviromentScript> scripts = getDbGuru().getActiveEnviromentScripts();
        scripts.stream().forEach((script) -> {
            env.runScript(script.getCode());
        });
    }
}

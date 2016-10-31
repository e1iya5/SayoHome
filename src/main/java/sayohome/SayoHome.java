package sayohome;

import shcp.ShcpSocket;
import dbstuff.DbGuru;
import httpstuff.MainServer;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import jsstuff.ScriptingEnviroment;
import rules.TimePattern;
import rules.TimeRule;

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

        // Start "General Event Guru"
        GeneralEventGuru geg = new GeneralEventGuru(eventChannel);
        new Thread(geg).start();

        // Activate Time Rules in GEG
        refreshTimeRules();

        new MainServer();

        // Start "Time Event Polling Service"
        TimeEventPollingService teps = new TimeEventPollingService(eventChannel);
        new Thread(teps).start();

        // Start "SayoHome Controlling Protocol" Server
        ShcpSocket shcp = new ShcpSocket(eventChannel);
        new Thread(shcp).start();
    }

    public static DbGuru getDbGuru() {
        return dbG;
    }
}

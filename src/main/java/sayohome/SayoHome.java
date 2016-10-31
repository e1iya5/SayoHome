package sayohome;

import dbstuff.DbGuru;
import httpstuff.MainServer;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
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

    private static void loadEnviromentScripts() {
        SayoHomeObject.clearServices();
    }
}

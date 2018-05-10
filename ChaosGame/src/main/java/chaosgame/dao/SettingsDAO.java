
package chaosgame.dao;

import chaosgame.domain.Node;
import chaosgame.domain.Settings;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Handles all saving, loading and deleting of fractal-generating settings
 * in a local database, using the Ormlite DAO-implementation.
 */
public class SettingsDAO {

    private Dao<Settings, String> daoS;
    private Dao<Node, String> daoN;

    /**
     * Creates a local database if not present, establishes a connection and
     * initializes the required tables.
     * @param dbName Name of the database file
     */
    public SettingsDAO(String dbName) {
        try {
            ConnectionSource cs = new JdbcConnectionSource("jdbc:sqlite:saves/" + dbName + ".db");
            this.daoS = DaoManager.createDao(cs, Settings.class);
            this.daoN = DaoManager.createDao(cs, Node.class);
            TableUtils.createTableIfNotExists(cs, Settings.class);
            TableUtils.createTableIfNotExists(cs, Node.class);
        } catch (SQLException e) {
            System.out.println(e.toString() + ": ERROR CONNECTING TO DATABASE");
        }
    }
    
    /**
     * Saves the given Settings and all associated anchor points
     * to the database.
     * 
     * @param settings The Settings to be saved
     */
    public void saveToDatabase(Settings settings) {
        try {
            DeleteBuilder<Node, String> db = daoN.deleteBuilder();  //removes old anchor points
            db.setWhere(db.where().eq("settings_id", settings));
            daoN.delete(db.prepare());
            
            daoS.createIfNotExists(settings);
            daoS.update(settings);
            for (Node anchor : settings.getAnchors()) {
                anchor.setSettings(settings);
                daoN.createIfNotExists(anchor);
            }
        } catch (SQLException e) {
            System.out.println(e.toString() + ": ERROR WHEN SAVING SETTINGS");
        }
    }

    /**
     * Queries for Settings from the database with a given key.
     * 
     * @param id The string by which the Settings are identified (primary key)
     * @return The queried settings, or default Settings if an error occurs
     * @see chaosgame.domain.Settings#Settings(double)  
     */
    public Settings getFromDatabase(String id) {
        try {
            Settings s = daoS.queryForId(id);
            if (s == null) {
                return new Settings(0.5);
            }
            s.setAnchors(new ArrayList<>());
            List<Node> anchors = daoN.queryBuilder().where()
                    .eq("settings_id", s).query();
            for (Node anchor : anchors) {
                s.addAnchor(anchor);
            }
            s.setPrev(s.getFirstAnchor());
            s.setRandom(new Random());
            return s;
        } catch (SQLException e) {
            System.out.println(e.toString() + ": ERROR WHEN LOADING SETTINGS");
            return new Settings(0.5);           //default
        }
    }

    /**
     * Removes settings with the given key from the database.
     * @param id The string by which the Settings are identified (primary key)
     */
    public void removeFromDatabase(String id) {
        try {
            Settings s = daoS.queryForId(id);
            if (s == null) {
                return;
            }
            DeleteBuilder<Node, String> db = daoN.deleteBuilder();
            db.setWhere(db.where().eq("settings_id", s));
            daoN.delete(db.prepare());

            daoS.delete(s);
        } catch (SQLException e) {
            System.out.println(e.toString() + ": ERROR WHEN DELETING SETTINGS");
        }
    }
    
    /**
     * Gets a list of Settings names (keys) from the database.
     * @return A sorted list of Settings
     */
    public List<String> getSettingsKeys() {
        try {
            List<Settings> list = daoS.queryForAll();
            if (list.isEmpty()) {
                return new ArrayList<>();
            }
            return list.stream()
                    .map(s -> s.getKey())
                    .sorted()
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (SQLException e) {
            System.out.println(e.toString() + ": ERROR WHEN FETCHING SETTING KEYS");
            return new ArrayList<>();
        }
    }
}

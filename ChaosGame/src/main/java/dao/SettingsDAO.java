/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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

public class SettingsDAO {

    private Dao<Settings, String> daoS;
    private Dao<Node, String> daoN;

    public SettingsDAO() {
        try {
            ConnectionSource cs = new JdbcConnectionSource("jdbc:sqlite:SavedSettings.db");
            this.daoS = DaoManager.createDao(cs, Settings.class);
            this.daoN = DaoManager.createDao(cs, Node.class);
            TableUtils.createTableIfNotExists(cs, Settings.class);
            TableUtils.createTableIfNotExists(cs, Node.class);
        } catch (SQLException e) {

        }
    }

    public void saveToDatabase(Settings settings) {
        try {
            DeleteBuilder<Node, String> db = daoN.deleteBuilder();
            db.setWhere(db.where().eq("settings_id", settings));
            daoN.delete(db.prepare());

            daoS.createIfNotExists(settings);
            daoS.update(settings);
            for (Node anchor : settings.getAnchors()) {
                anchor.setSettings(settings);
                daoN.createIfNotExists(anchor);
            }
        } catch (SQLException e) {
            System.out.println("ERROR WHEN SAVING SETTINGS");
        }
    }

    public Settings getFromDatabase(String id) {
        try {
            Settings s = daoS.queryForId(id);
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
            System.out.println("ERROR WHEN LOADING SETTINGS");
            return new Settings(0.5);           //default
        }
    }

    public void removeFromDatabase(String id) {
        try {
            Settings s = daoS.queryForId(id);
            DeleteBuilder<Node, String> db = daoN.deleteBuilder();
            db.setWhere(db.where().eq("settings_id", s));
            daoN.delete(db.prepare());

            daoS.delete(s);
        } catch (SQLException e) {
            System.out.println("ERROR WHEN DELETING SETTINGS");
        }
    }

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
            System.out.println("ERROR WHEN GETTING SETTING KEYS");
            return new ArrayList<>();
        }
    }
}

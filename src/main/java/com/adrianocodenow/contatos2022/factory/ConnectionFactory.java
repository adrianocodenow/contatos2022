package com.adrianocodenow.contatos2022.factory;

import com.adrianocodenow.contatos2022.dao.CriaTabelas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author apereira
 */
public class ConnectionFactory {

    public static Connection abre(String bancoDeDados) {
        Connection db = null;
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection(
                    "jdbc:sqlite:" + bancoDeDados + ".sqlite",
                    config.toProperties()
            );
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return db;
    }

    public static void fecha(Connection db) {
        try {
            if (db != null) {
                db.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}

package com.adrianocodenow.contatos2022.factory;

import com.adrianocodenow.contatos2022.dao.CriaTabelas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author apereira
 */
public class ConnectionFactory {

    public static Connection abre(String bancoDeDados) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection db = null;
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection(
                    "jdbc:sqlite:" + bancoDeDados + ".sqlite",
                    config.toProperties()
            );
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
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

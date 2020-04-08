package com.yusufsezer.repository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteRepository extends SQLRepository {

    public SQLiteRepository(String source) {
        try {
            connection = DriverManager.getConnection(source);
            statement = connection.createStatement();
            statement.execute("SELECT * FROM contacts");
        } catch (SQLException ex) {
            createTable();
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE contacts ("
                + "  id integer primary key,"
                + "  firstName VARCHAR,"
                + "  lastName VARCHAR,"
                + "  email VARCHAR,"
                + "  phoneNumber VARCHAR,"
                + "  address VARCHAR,"
                + "  webAddress VARCHAR,"
                + "  notes VARCHAR)";
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLiteRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }

}

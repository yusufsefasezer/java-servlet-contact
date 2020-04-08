package com.yusufsezer.repository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLRepository extends SQLRepository {

    public MySQLRepository(String source) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(source);
            statement = connection.createStatement();
            statement.execute("SELECT * FROM contacts");
        } catch (SQLException ex) {
            createTable();
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE contacts ("
                + "  id INT PRIMARY KEY AUTO_INCREMENT,"
                + "  firstName VARCHAR(40),"
                + "  lastName VARCHAR(40),"
                + "  email VARCHAR(40),"
                + "  phoneNumber VARCHAR(20),"
                + "  address VARCHAR(80),"
                + "  webAddress VARCHAR(80),"
                + "  notes VARCHAR(80))";
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger
                    .getLogger(MySQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }

}

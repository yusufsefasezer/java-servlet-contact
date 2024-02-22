package com.yusufsezer.repository;

import com.yusufsezer.model.Contact;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLRepository extends SQLRepository {

    public MySQLRepository(Connection connection) {
        super(connection);
    }

    @Override
    protected void createTable() {
        String sql = "CREATE TABLE contacts ("
                + Contact.ID + " INTEGER PRIMARY KEY AUTO_INCREMENT,"
                + Contact.FIRST_NAME + " VARCHAR(100),"
                + Contact.LAST_NAME + " VARCHAR(100),"
                + Contact.EMAIL + " VARCHAR(80),"
                + Contact.PHONE_NUMBER + " VARCHAR(30),"
                + Contact.ADDRESS + " TEXT,"
                + Contact.WEB_ADDRESS + " VARCHAR(255),"
                + Contact.NOTES + "  TEXT)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            logException(exception);
            throw new RuntimeException("Failed to create table.");
        }
    }

}

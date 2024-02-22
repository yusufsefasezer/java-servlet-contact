package com.yusufsezer.repository;

import java.sql.Connection;

public class SQLiteRepository extends SQLRepository {

    public SQLiteRepository(Connection connection) {
        super(connection);
    }

}

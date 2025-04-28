package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {

//      Class<Driver> driverClass = Driver.class;
//      String sql = """
//              SELECT *
//              FROM book
//              """;
//
//        try (var  connection = ConnectionManager.open();
//            var statement = connection.createStatement()) {
//            System.out.println(connection.getTransactionIsolation());
//
//            var executeResult =  statement.executeQuery(sql);
//            System.out.println(executeResult);
//        }
        checkMetaData();


    }

    private static void checkMetaData() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                System.out.println(catalogs.getString(1));
            }
            var schemas = metaData.getSchemas();
            while (schemas.next()) {
                System.out.println(schemas.getString("TABLE_SCHEM"));
            }




        }
    }
}
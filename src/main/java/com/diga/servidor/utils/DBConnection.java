/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diga.servidor.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Guilherme
 */
public class DBConnection {

    public static Connection getConnection() throws SQLException {
        DataSource dataSource = lookupDataSource();

        Connection connection = dataSource.getConnection();

        return connection;
    }

    private static DataSource lookupDataSource() {
        try {
            Context initialContext = new InitialContext();
            try {
                return (DataSource) initialContext.lookup(System.getenv("DB_JNDI"));
            } catch (NameNotFoundException e) {
                Context envContext = (Context) initialContext.lookup("java:comp/env");
                // inside java:comp/env
                return (DataSource) envContext.lookup(System.getenv("DB_JNDI"));
            }
        } catch (NamingException e) {
            throw new RuntimeException("Datasource não pode ser encontrado", e);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.*;

/**
 *
 * @author jorge
 */
public class Dbconfig {

    private static Connection c = null;

    public static Connection getConnection() {
        if (c == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                c = DriverManager.getConnection("jdbc:mysql://localhost/javabank", "root", "");
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
            return c;

        } else {
            return c;
        }
    }

}

package com.example.WEB_App.samples;
import com.example.WEB_App.entity.CustomUser;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class DatabaseHandler extends Configs {
    Connection connection;
    public Connection getDbConnection()
            throws ClassNotFoundException,SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" +dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(connectionString,dbUser,dbPass);
        return connection;
    }

    public void singUpUser(CustomUser user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USER_ID + "," +
                Const.USER_LOGIN + "," + Const.USER_PASSWORD + "," +
                Const.USER_INFORMATION + ")" +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setLong(1, user.getId());
            prSt.setString(2, user.getLogin());
            prSt.setString(3, user.getPassword());
            prSt.setString(4, user.getInformation());

            prSt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet loginUser(String login, String pass) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USER_LOGIN + "=? AND " + Const.USER_PASSWORD + "=?" ;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,login);
            prSt.setString(2,pass);
           resSet =  prSt.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }
}

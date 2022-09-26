package com.nfcat.papermc.sql;

import com.alibaba.fastjson.JSONObject;
import com.nfcat.papermc.Main;
import com.nfcat.papermc.exception.FailedVerificationException;
import com.nfcat.papermc.sql.entry.User;
import com.nfcat.papermc.utils.NfUtils;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class SQLManager {

    @Test
    public void t() {
        try (InputStream di = this.getClass().getClassLoader().getResourceAsStream("dbcp.properties")) {
            Properties dbcpProp = new Properties();
            dbcpProp.load(di);
            JdbcDBCP.init(dbcpProp);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(addGold("puppetk", 10));
    }

    public static boolean addGold(String username, int addGold) {
        username = username.toLowerCase().trim();
        try {
            Connection connection = JdbcDBCP.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(
                            "UPDATE nf_mc_user SET gold=gold+? WHERE mc_name=?");
            preparedStatement.setInt(1, addGold);
            preparedStatement.setString(2, username);
            int n = preparedStatement.executeUpdate();
            if (n > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new FailedVerificationException("未知错误");
        }
        return false;
    }

    public static User selectInfo(String username) {
        username = username.toLowerCase().trim();
        try {
            Connection connection = JdbcDBCP.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM nf_mc_user WHERE mc_name=?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            Map<String, Object> map = converResultSetToMap(rs);
            if (map == null) return null;
            return JSONObject.toJavaObject(new JSONObject(map), User.class);
        } catch (SQLException e) {
            throw new FailedVerificationException("未知错误");
        }
    }

    public static boolean login(String username, String password) {
        username = username.toLowerCase().trim();
        password = password.trim();
        try {
            Connection connection = JdbcDBCP.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * from nf_mc_user where mc_name=? && password=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, NfUtils.pwdEncrypt(password, Main.configProp.getProperty("salt", "")));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new FailedVerificationException("密码错误");
        }
        return false;
    }

    public static boolean register(String username, String password) {
        username = username.toLowerCase().trim();
        password = password.trim();
        try {
            Connection connection = JdbcDBCP.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO nf_mc_user (mc_name,password) VALUES (?,?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, NfUtils.pwdEncrypt(password, Main.configProp.getProperty("salt", "")));
            int n = preparedStatement.executeUpdate();
            if (n > 0) {
                return true;
            }
        } catch (SQLException se) {
            throw new FailedVerificationException("注册失败，账号重复");
        }
        return false;
    }

    public static boolean changepass(String username, String oldPassword, String password) {
        username = username.toLowerCase().trim();
        oldPassword = oldPassword.trim();
        password = password.trim();
        try {
            Connection connection = JdbcDBCP.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE nf_mc_user SET password=? WHERE mc_name=? && password=?");
            preparedStatement.setString(2, username);
            preparedStatement.setString(1, NfUtils.pwdEncrypt(password, Main.configProp.getProperty("salt", "")));
            preparedStatement.setString(3, NfUtils.pwdEncrypt(oldPassword, Main.configProp.getProperty("salt", "")));
            int n = preparedStatement.executeUpdate();
            if (n > 0) {
                return true;
            }
        } catch (SQLException se) {
            throw new FailedVerificationException("修改密码失败，未知错误");
        }
        return false;
    }

    private static List<Map<String, Object>> converResultSetToList(ResultSet resultSet) throws SQLException {
        if (null == resultSet) {
            return null;
        }
        List<Map<String, Object>> data = new ArrayList<>();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        while (resultSet.next()) {
            Map<String, Object> rowData = new HashMap<String, Object>();
            for (int i = 0, columnCount = rsmd.getColumnCount(); i < columnCount; i++) {
                rowData.put(rsmd.getColumnName(i + 1), resultSet.getObject(i + 1));
            }
            data.add(rowData);
        }
        return data;
    }

    private static Map<String, Object> converResultSetToMap(ResultSet resultSet) throws SQLException {
        if (null == resultSet) {
            return null;
        }
        ResultSetMetaData rsmd = resultSet.getMetaData();
        if (resultSet.next()) {
            Map<String, Object> rowData = new HashMap<>();
            for (int i = 0, columnCount = rsmd.getColumnCount(); i < columnCount; i++) {
                rowData.put(rsmd.getColumnName(i + 1), resultSet.getObject(i + 1));
            }
            return rowData;
        }
        return null;
    }
}

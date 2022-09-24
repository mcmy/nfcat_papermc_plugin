package com.nfcat.spigotmc.sql;

import com.nfcat.spigotmc.Main;
import com.nfcat.spigotmc.exception.FailedVerificationException;
import com.nfcat.spigotmc.utils.NfUtils;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

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
        System.out.println(login("PuppetK", "Aoyiqi253"));
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
}

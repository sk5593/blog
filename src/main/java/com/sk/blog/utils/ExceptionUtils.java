package com.sk.blog.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Date;

@Component
public class ExceptionUtils {
    @Value( "${spring.datasource.url}" )
    private String url;
    @Value( "${spring.datasource.username}" )
    private String username;
    @Value( "${spring.datasource.password}" )
    private String password;
    @Value( "${spring.datasource.driverClassName}" )
    private String driver;

    public void errorToDB(Exception e) throws ClassNotFoundException, SQLException {
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        Connection con = null;
        Class.forName(driver);
        con = DriverManager.getConnection(url, username, password);

        PreparedStatement ps = null;
        String sql = "INSERT INTO t_logs(data,created) VALUES (?,?)";
        ps = con.prepareStatement(sql);
        String data = e.toString() + ",errorMassage:" + stackTraceElement + "," + "errorLine:" + stackTraceElement.getLineNumber();
        long time = new Date().getTime();
        System.out.println("data:" + data);
        ps.setString(1, data);
        ps.setLong(2, time);
        ps.execute();
        if (con != null) {
            con.close();
        }
        if (ps != null) {
            ps.close();
        }


    }


}

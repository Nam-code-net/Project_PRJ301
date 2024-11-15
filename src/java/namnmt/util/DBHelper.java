/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namnmt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author VICTUS
 */
public class DBHelper {
    public static Connection getConnection() 
    throws ClassNotFoundException, SQLException{
        // tên này viết sai
        // chưa add driver vào trong project
        
        //SQLException xuất hiện lỗi trong 3 trường hợp sau:
        //+ chưa enable
        //+ bất kì giá trị nào sai jdbc sai : sai.....
        //+ 
        //1.  Load Driver 
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //2.  Create connection string 
        
            //syntax: protocol (JDBC) :DBMS_name://ip:port;databaseName = ......
        String con = "jdbc:sqlserver://"
                + "localhost:1433;"
                + "databaseName = master";
        
        //3. Open connection
            Connection connection = DriverManager.getConnection(con, "sa", "12345");
            
            return connection;
    }
}

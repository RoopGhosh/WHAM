package edu.neu.cs5500.Jerks.business;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class Connection {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
	    //connect to database
       // Class.forName("oracle.jdbc.driver.OracleDriver");
       // String serverName = "00.000.0.000";
        String portNumber = "1521";
        String sid = "My Sid";
        String url = "jdbc:mysql://localhost:3306/jerks";
        String username = "admin6IViBwq";
        String password = "RKzjAiKiCd9N";
        java.sql.Connection conn = DriverManager.getConnection(url, username, password);
        Statement statement = (Statement) conn.createStatement();
        ResultSet res =  statement.executeQuery("SELECT * FROM `address` WHERE 1");
        while(res.next())
        {
        	System.out.println(res.getString(1));
        	System.out.println(res.getString(3));
        	System.out.println(res.getString(4));
        	System.out.println(res.getString(5));
        }
    }
	}
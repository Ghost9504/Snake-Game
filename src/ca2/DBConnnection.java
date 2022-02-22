/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca2;
import java.sql.DriverManager;
import java.sql.*;

public class DBConnnection {
     public static java.sql.Connection connect() throws ClassNotFoundException{
java.sql.Connection conn = null;
try{
Class.forName("com.mysql.cj.jdbc.Driver");
conn=(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/game","root","");
System.out.print("Created");
}
catch(SQLException e){
    System.out.print(" Not Created");
    System.out.print(e);
}
        return conn;
    } 
}

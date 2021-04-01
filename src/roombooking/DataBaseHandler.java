/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roombooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author James
 */
public class DataBaseHandler {
    
    public static void excecuteUpdateQuery(String query){
        
        try {
            
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/BookingDatabase", "James", "Password");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.executeUpdate(query);
            
            
            
        } catch (Exception e) {
            System.out.println("Error:" +e);
        }
        
    }
    
    public static ResultSet excecuteQuery(String query){
        
        try {
            
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/BookingDatabase", "James", "Password");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = stmt.executeQuery(query);         
            
            return rs;
            
        } catch (Exception e) {
            System.out.println("Error:" +e);
            return null;
        }
        
    }
    
}

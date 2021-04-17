/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roombooking;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

/**
 *
 * @author James
 */
public class Cleaning {
    
     public static void AddCleaningSlot(Time time, Date date, int BookingID) {
        try {

            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/BookingDatabase", "James", "Password");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "SELECT MAX(CleanID) as idNum from app.CLEANING";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int idValue = rs.getInt("idNum") + 1;
            //System.out.println("idValue is: " + idValue);

            sql = "INSERT INTO app.CLEANING VALUES (" + idValue + ", '" + time + "', '" + date + "', " + BookingID+")";
            stmt.executeUpdate(sql);
            
            
            rs.close();
            con.close();
            stmt.close();
            

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}

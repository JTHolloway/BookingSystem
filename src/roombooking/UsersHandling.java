/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roombooking;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author James
 */
public class UsersHandling {

    private static ArrayList<User> Users = new ArrayList<User>();
    private static User CurrentUser;
    private static int CurrentAccessLevel;

    public static void AddUser(String FName, String SName, String Email, String Salary, String Company, int Department, String Password) {
        try {

            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/BookingDatabase", "James", "Password");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "SELECT MAX(UserID) as idNum from app.Users";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int idValue = rs.getInt("idNum") + 1;
            //System.out.println("idValue is: " + idValue);

            sql = "INSERT INTO app.Users VALUES (" + idValue + ", '" + FName + "', '" + SName + "', '" + Email + "', " 
                    + "0.0" + ", '" + Company + "', " + (Department+1) + ", '" + Encrypt(Password) +"')";
            stmt.executeUpdate(sql);
            
            
            rs.close();
            con.close();
            stmt.close();
            

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int returnId(){
        int idValue = 1;
        try {

            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/BookingDatabase", "James", "Password");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "SELECT MAX(UserID) as idNum from app.Users";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            idValue = rs.getInt("idNum");
            System.out.println("idValue is ppo: " + idValue);           
            
            rs.close();
            con.close();
            stmt.close();
            

        } catch (Exception e) {
            System.out.println(e);
        }
        
        return idValue;
    }
    
    public static void CreateUsers() {
        try {

            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/BookingDatabase", "James", "Password");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "Select * From app.USERS";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int UserID = rs.getInt("UserId");
                String FirstName = rs.getString("FirstName");
                String Surname = rs.getString("Surname");
                int Department = rs.getInt("Department");
                String Email = rs.getString("EmailAddress");
                double Salary = rs.getDouble("Salary");
                String Company = rs.getString("Company");
                String Password = rs.getString("Password");

                User u = new User(UserID, FirstName, Surname, Department, Email, Salary, Company, Password);
                Users.add(u);
                
                //System.out.println(UserID + " " + FirstName + " " + Surname + " " + Department
                //+ " " + Email + " " + Salary + " " + Company+ " " + Password);
            }

            //HouseKeeping
            rs.close();
            con.close();
            stmt.close();

        } catch (Exception e) {
            System.err.println("USERSHANDLING CLASS ERROR: " + e);
        }
    }

    public static void SaveUserAccessLevel() {

        if(CurrentUser == null){
            CurrentAccessLevel = 0;
        }else{
            try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/BookingDatabase", "James", "Password");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "Select * From app.DEPARTMENT WHERE DEPTID = " + CurrentUser.getDepartment();
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();

            //System.out.println(rs.getInt("ACCESSLEVEL"));
            CurrentAccessLevel = rs.getInt("ACCESSLEVEL");

        } catch (Exception e) {
            System.out.println("Error in UserHAndling " + e);
            CurrentAccessLevel = 0;
        }
        }

    }

    public static int getCurrentAccessLevel() {
        return CurrentAccessLevel;
    }

    public static void UpdateUsers() {
        Users.clear();
        CreateUsers();
    }

    public static User ReturnUser(int UserID) {
        //System.out.println("UserID: " + UserID);
        for (int i = 0; i < Users.size(); i++) {
            if (Users.get(i).getUserId() == UserID) {
                return Users.get(i);
            }
        }
        return null;
    }

    public static ArrayList<User> getUsers() {
        return Users;
    }

    public static boolean ConfirmUser(int UserID, String Email, String Password) {
        boolean found = false;
        for (int i = 0; i < Users.size(); i++) {
            System.out.println(Users.get(i).getUserId() + " ........ " + UserID);
            System.out.println(Users.get(i).getEmailAddress() + " ........ " + Email);
            System.out.println(Users.get(i).getPassword() + " ........ " + Encrypt(Password));
            if ((Users.get(i).getUserId() == UserID) && (Users.get(i).getEmailAddress().equalsIgnoreCase(Email)) && (Users.get(i).getPassword().equals(Encrypt(Password)))) {
                found = true;
                break;
            } else {
                found = false;
            }
            System.out.println(found);
        }
        System.out.println(found);
        return found;
    }

    public static User getCurrentUser() {
        return CurrentUser;
    }

    public static void setCurrentUser(User CurrentUser) {
        UsersHandling.CurrentUser = CurrentUser;
        //System.out.println(CurrentUser);
    }

    private static String Encrypt(String Password) {

        try {
            MessageDigest Encrypt = MessageDigest.getInstance("SHA");

            Encrypt.update(Password.getBytes());
            byte[] Encryption = Encrypt.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : Encryption) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

}

package roombooking;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class BookingHandling {

    private Connection con;
    private Statement stmt;
    private String sql;
    private ResultSet rs;
    private static ArrayList<Booking> Bookings = new ArrayList<>();

    public static boolean AddBookingRecord(Time Start, Time End, Date date, User user, int Room) {
        try {

            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/BookingDatabase", "James", "Password");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "SELECT MAX(BookingID) as idNum from app.Bookings";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int idValue = rs.getInt("idNum") + 1;
            //System.out.println("idValue is: " + idValue);
            
            if (VerifiyTime(Room, Start, End, date)) {
                sql = "INSERT INTO app.Bookings VALUES (" + idValue + ", '" + Start + "', '" + End + "', '" + date + "', " + user.getUserId() + ", " + Room + ")";
                stmt.executeUpdate(sql);
                
                Cleaning.AddCleaningSlot(End, date, idValue);
                
                rs.close();
                con.close();
                stmt.close();
                UpdateBookingRecords();
                return true;
            } else {
                rs.close();
                con.close();
                stmt.close();
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    public static void CreateBookingRecords() {
        try{
            
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/BookingDatabase", "James", "Password");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "Select * From app.Bookings";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int BookingID = rs.getInt("BookingID");
                Time Start_Time = rs.getTime("Booking_StartTimeSlot");
                Time End_Time = rs.getTime("Booking_EndTimeSlot");
                Date Booking_Date = rs.getDate("Booking_Date");
                int UserID = rs.getInt("UserID");
                int RoomNumber = rs.getInt("RoomNumber");

                Booking b = new Booking(BookingID, Start_Time, End_Time, Booking_Date, UserID, RoomNumber);
                Bookings.add(b);
                //System.out.println(BookingID + " " + Start_Time.toString() + " " + End_Time.toString() + " " + Booking_Date.toString()
                        //+ " " + UserID + " " + RoomNumber);

            }

            //HouseKeeping
            rs.close();
            con.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void UpdateBookingRecords(){
        Bookings.clear();
        CreateBookingRecords();
    }

    public ArrayList<Booking> getBookings() {
        return Bookings;
    }

    public static boolean VerifiyTime(int Room, Time Start, Time End, Date Date) {

        ArrayList<Booking> FilteredBookings = new ArrayList<>();

        for (int i = 0; i < Bookings.size(); i++) {
            if (Bookings.get(i).getBooking_Date().toString().equals(Date.toString())) {
                if (Bookings.get(i).getRoomNumber() == Room) {
                    FilteredBookings.add(Bookings.get(i));
                }
            }
        }

        if (FilteredBookings.isEmpty()) {
            System.out.println("1");
            return true;
        } else {

            ArrayList<Booking> NextFilteredBookings = new ArrayList<>();

            int OverLapCount = 0;

            for (int i = 0; i < FilteredBookings.size(); i++) {

                //IF TIME DOES NOT OVERLAP WITH OTHER TIMES ON SAME DATE AND ROOM THEN:
                if (((Start.before(FilteredBookings.get(i).getBooking_StartTimeSlot())) && (End.before(FilteredBookings.get(i).getBooking_StartTimeSlot())))) {

                    NextFilteredBookings.add(FilteredBookings.get(i));

                } else if (((Start.after(FilteredBookings.get(i).getBooking_EndTimeSlot())) && (End.after(FilteredBookings.get(i).getBooking_EndTimeSlot())))) {

                    NextFilteredBookings.add(FilteredBookings.get(i));
                } else {
                    OverLapCount++;
                }
            }

            //if its empty then they all overlapped
            if (OverLapCount > 0) {
                System.out.println("2");
                return false;
            } else {

                FilteredBookings.clear();

                Time Sub = Start;
                LocalTime Before = Sub.toLocalTime();
                Before = Before.minusMinutes(30);

                Time Add = End;
                LocalTime After = Add.toLocalTime();
                After = After.plusMinutes(30);

                for (int i = 0; i < NextFilteredBookings.size(); i++) {

                    //IF START OF MEETING - 30 MINS IS BEFORE END OF OTHER MEETING AND THE START IS AFTER THE END OF THE OTHER MEETING THEN
                    if ((((Before.isBefore(NextFilteredBookings.get(i).getBooking_EndTimeSlot().toLocalTime())) && (Start.toLocalTime().isAfter(NextFilteredBookings.get(i).getBooking_EndTimeSlot().toLocalTime())))
                            || ((After.isAfter(NextFilteredBookings.get(i).getBooking_StartTimeSlot().toLocalTime()))) && (End.toLocalTime().isBefore(NextFilteredBookings.get(i).getBooking_StartTimeSlot().toLocalTime())))) {
                        FilteredBookings.add(NextFilteredBookings.get(i));
                    } //If no time to clean rooms then gets added to list
                }

                if (FilteredBookings.isEmpty()) {
                    System.out.println("3");
                    return true;
                } else {
                    System.out.println("4");
                    return false;
                }
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roombooking;

import java.sql.Date;
import java.sql.Time;
import roombooking.UsersHandling;

/**
 *
 * @author James
 */
public class Booking {
    
    private int BookingID;
    private Time Booking_StartTimeSlot;
    private Time Booking_EndTimeSlot;
    private Date Booking_Date;
    private User User; 
    private int RoomNumber;

    public Booking(int BookingID, Time Booking_StartTimeSlot, Time Booking_EndTimeSlot, Date Booking_Date, int UserID, int RoomNumber) {
        this.BookingID = BookingID;
        this.Booking_StartTimeSlot = Booking_StartTimeSlot;
        this.Booking_EndTimeSlot = Booking_EndTimeSlot;
        this.Booking_Date = Booking_Date;
        this.RoomNumber = RoomNumber;
        
        this.User = UsersHandling.ReturnUser(UserID);
    }

    

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int BookingID) {
        this.BookingID = BookingID;
    }

    public Time getBooking_StartTimeSlot() {
        return Booking_StartTimeSlot;
    }

    public void setBooking_StartTimeSlot(Time Booking_StartTimeSlot) {
        this.Booking_StartTimeSlot = Booking_StartTimeSlot;
    }

    public Time getBooking_EndTimeSlot() {
        return Booking_EndTimeSlot;
    }

    public void setBooking_EndTimeSlot(Time Booking_EndTimeSlot) {
        this.Booking_EndTimeSlot = Booking_EndTimeSlot;
    }

    public Date getBooking_Date() {
        return Booking_Date;
    }

    public void setBooking_Date(Date Booking_Date) {
        this.Booking_Date = Booking_Date;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public int getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(int RoomNumber) {
        this.RoomNumber = RoomNumber;
    }
    
    
        
}

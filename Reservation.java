package hotel;

import java.sql.Date;

public class Reservation {
    private int reservationId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private String reservationStatus;

    public Reservation(int reservationId, int roomId, Date checkInDate, Date checkOutDate, String reservationStatus) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.reservationStatus = reservationStatus;
    }

    // Getters
    public int getReservationId() { return reservationId; }
    public int getRoomId() { return roomId; }
    public Date getCheckInDate() { return checkInDate; }
    public Date getCheckOutDate() { return checkOutDate; }
    public String getReservationStatus() { return reservationStatus; }

    // Setters
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
    public void setReservationStatus(String reservationStatus) { this.reservationStatus = reservationStatus; }
}

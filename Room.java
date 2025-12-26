package hotel;

public class Room {
    private int roomId;
    private String roomType;
    private double price;
    private boolean availability;

    public Room(int roomId, String roomType, double price, boolean availability) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.price = price;
        this.availability = availability;
    }

    // Getters
    public int getRoomId() { return roomId; }
    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return availability; }

    // Setters
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public void setPrice(double price) { this.price = price; }
    public void setAvailability(boolean availability) { this.availability = availability; }
}


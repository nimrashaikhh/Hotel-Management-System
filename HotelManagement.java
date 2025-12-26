package hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HotelManagement {

    // ---- Base Methods (empty stubs) ----
    public void addCustomer() {}
    public void viewCustomer() {}
    public void editCustomer() {}

    public void addRoom() {}
    public void viewRoom() {}
    public void editRoom() {}

    public void newBooking() {}
    public void viewBooking() {}
    public void editBooking() {}

    public void addPayment() {}
    public void viewPayment() {}
    public void editPayment() {}

    public void makeReservation() {}
    public void viewReservation() {}
    public void editReservation() {}

    // ---- Customer Management ----
    public static class CustomerManagement extends HotelManagement {
        @Override public void addCustomer() { new AddCustomerGUI(); }
        @Override public void viewCustomer() { new ViewCustomerGUI(); }
        @Override public void editCustomer() { new EditCustomerGUI(); }
    }

    // ---- Room Management ----
    public static class RoomManagement extends HotelManagement {
        @Override public void addRoom() { new AddRoomGUI(); }
        @Override public void viewRoom() { new ViewRoomGUI(); }
        @Override public void editRoom() { new EditRoomGUI(); }
    }

    // ---- Booking Management ----
    public static class BookingManagement extends HotelManagement {
        @Override public void newBooking() { new NewBookingGUI(); }
        @Override public void viewBooking() { new ViewBookingGUI(); }
        @Override public void editBooking() { new EditBookingGUI(); }
    }

    // ---- Payment Management ----
    public static class PaymentManagement extends HotelManagement {
        @Override public void addPayment() { new AddPaymentGUI(); }
        @Override public void viewPayment() { new ViewPaymentGUI(); }
        @Override public void editPayment() { new EditPaymentGUI(); }
    }

    // ---- Reservation Management ----
    public static class ReservationManagement extends HotelManagement {
        @Override public void makeReservation() { new MakeReservationGUI(); }
        @Override public void viewReservation() { new ViewReservationGUI(); }
        @Override public void editReservation() { new EditReservationGUI(); }
    }

    // ---------------- GUI Forms ----------------

    // ---- Customers ----
    public static class AddCustomerGUI extends FormTemplate {
        public AddCustomerGUI() {
            super("Add Customer", new String[]{"Full Name", "Email", "Phone", "ID Number"});
        }
    }

    public static class ViewCustomerGUI extends TableTemplate {
        public ViewCustomerGUI() {
            super("View Customers", new String[]{"ID", "Name", "Email", "Phone"});
        }
    }

    public static class EditCustomerGUI extends FormTemplate {
        public EditCustomerGUI() {
            super("Edit Customer", new String[]{"Customer ID", "Full Name", "Email", "Phone"});
        }
    }

    // ---- Rooms ----
    public static class AddRoomGUI extends FormTemplate {
        public AddRoomGUI() {
            super("Add Room", new String[]{"Room Number", "Type", "Price", "Status"});
        }
    }

    public static class ViewRoomGUI extends TableTemplate {
        public ViewRoomGUI() {
            super("View Rooms", new String[]{"Room No", "Type", "Price", "Status"});
        }
    }

    public static class EditRoomGUI extends FormTemplate {
        public EditRoomGUI() {
            super("Edit Room", new String[]{"Room Number", "Type", "Price", "Status"});
        }
    }

    // ---- Bookings ----
    public static class NewBookingGUI extends FormTemplate {
        public NewBookingGUI() {
            super("New Booking", new String[]{"Customer ID", "Room Number", "Check-In", "Check-Out"});
        }
    }

    public static class ViewBookingGUI extends TableTemplate {
        public ViewBookingGUI() {
            super("View Bookings", new String[]{"Booking ID", "Customer ID", "Room No", "Check-In", "Check-Out"});
        }
    }

    public static class EditBookingGUI extends FormTemplate {
        public EditBookingGUI() {
            super("Edit Booking", new String[]{"Booking ID", "Customer ID", "Room Number", "Check-In", "Check-Out"});
        }
    }

    // ---- Payments ----
    public static class AddPaymentGUI extends FormTemplate {
        public AddPaymentGUI() {
            super("Add Payment", new String[]{"Customer ID", "Amount", "Payment Date", "Method"});
        }
    }

    public static class ViewPaymentGUI extends TableTemplate {
        public ViewPaymentGUI() {
            super("View Payments", new String[]{"Payment ID", "Customer ID", "Amount", "Date", "Method"});
        }
    }

    public static class EditPaymentGUI extends FormTemplate {
        public EditPaymentGUI() {
            super("Edit Payment", new String[]{"Payment ID", "Customer ID", "Amount", "Date", "Method"});
        }
    }

    // ---- Reservations ----
    public static class MakeReservationGUI extends FormTemplate {
        public MakeReservationGUI() {
            super("Make Reservation", new String[]{"Customer ID", "Room No", "Reservation Date"});
        }
    }

    public static class ViewReservationGUI extends TableTemplate {
        public ViewReservationGUI() {
            super("View Reservations", new String[]{"Reservation ID", "Customer ID", "Room No", "Date"});
        }
    }

    public static class EditReservationGUI extends FormTemplate {
        public EditReservationGUI() {
            super("Edit Reservation", new String[]{"Reservation ID", "Customer ID", "Room No", "Date"});
        }
    }

    // ---------------- Reusable Templates ----------------

    public static class FormTemplate extends JFrame {
        public FormTemplate(String title, String[] labels) {
            setTitle(title);
            setSize(400, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            for (String label : labels) {
                panel.add(new JLabel(label + ":"));
                panel.add(new JTextField(20));
            }

            JButton submit = new JButton("Submit");
            panel.add(submit);
            submit.addActionListener(e -> dispose()); // Placeholder

            add(panel);
            setVisible(true);
        }
    }

    public static class TableTemplate extends JFrame {
        public TableTemplate(String title, String[] columns) {
            setTitle(title);
            setSize(600, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JTable table = new JTable(new DefaultTableModel(columns, 0));
            JScrollPane scrollPane = new JScrollPane(table);

            add(scrollPane);
            setVisible(true);
        }
    }
}


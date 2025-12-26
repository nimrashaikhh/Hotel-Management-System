package hotel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class ReservationManagement extends HotelManagement {

    // Show Add Reservation form
    public void showAddReservationForm() {
        JFrame frame = new JFrame("Add Reservation");
        frame.setSize(400, 350);
        frame.setLayout(new GridLayout(6, 2));

        JTextField roomIdField = new JTextField();
        JTextField checkInField = new JTextField("yyyy-mm-dd");
        JTextField checkOutField = new JTextField("yyyy-mm-dd");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Pending", "Confirmed", "Cancelled"});

        frame.add(new JLabel("Room ID:"));
        frame.add(roomIdField);
        frame.add(new JLabel("Check-in Date (yyyy-mm-dd):"));
        frame.add(checkInField);
        frame.add(new JLabel("Check-out Date (yyyy-mm-dd):"));
        frame.add(checkOutField);
        frame.add(new JLabel("Status:"));
        frame.add(statusCombo);

        JButton addBtn = new JButton("Add Reservation");
        frame.add(addBtn);

        addBtn.addActionListener(e -> {
            String roomIdStr = roomIdField.getText();
            String checkInStr = checkInField.getText();
            String checkOutStr = checkOutField.getText();
            String status = (String) statusCombo.getSelectedItem();

            if (roomIdStr.isEmpty() || checkInStr.isEmpty() || checkOutStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.");
                return;
            }

            try {
                int roomId = Integer.parseInt(roomIdStr);

                // Validate dates
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                java.util.Date checkInDate = sdf.parse(checkInStr);
                java.util.Date checkOutDate = sdf.parse(checkOutStr);

                if (!checkOutDate.after(checkInDate)) {
                    JOptionPane.showMessageDialog(frame, "Check-out date must be after check-in date.");
                    return;
                }

                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(
                             "INSERT INTO reservations (room_id, check_in_date, check_out_date, reservation_status) VALUES (?, ?, ?, ?)")) {

                    stmt.setInt(1, roomId);
                    stmt.setDate(2, new java.sql.Date(checkInDate.getTime()));
                    stmt.setDate(3, new java.sql.Date(checkOutDate.getTime()));
                    stmt.setString(4, status);
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "✅ Reservation added successfully!");
                    frame.dispose();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "❌ Failed to add reservation. Check if Room ID exists.");
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "Room ID must be a number.");
            } catch (ParseException pe) {
                JOptionPane.showMessageDialog(frame, "Invalid date format. Use yyyy-mm-dd.");
            }
        });

        frame.setVisible(true);
    }

    // Show all reservations in a text area
    public void showViewReservations() {
        JFrame frame = new JFrame("View Reservations");
        frame.setSize(600, 400);
        JTextArea area = new JTextArea();
        area.setEditable(false);

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM reservations")) {

            StringBuilder builder = new StringBuilder("Reservations List:\n\n");
            while (rs.next()) {
                builder.append("Reservation ID: ").append(rs.getInt("reservation_id")).append("\n");
                builder.append("Room ID: ").append(rs.getInt("room_id")).append("\n");
                builder.append("Check-in: ").append(rs.getDate("check_in_date")).append("\n");
                builder.append("Check-out: ").append(rs.getDate("check_out_date")).append("\n");
                builder.append("Status: ").append(rs.getString("reservation_status")).append("\n\n");
            }

            area.setText(builder.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            area.setText("Error loading reservations.");
        }

        frame.add(new JScrollPane(area));
        frame.setVisible(true);
    }

    // Show Edit Reservation form
    public void showEditReservationForm() {
        JFrame frame = new JFrame("Edit Reservation");
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(7, 2));

        JTextField reservationIdField = new JTextField();
        JTextField roomIdField = new JTextField();
        JTextField checkInField = new JTextField("yyyy-mm-dd");
        JTextField checkOutField = new JTextField("yyyy-mm-dd");
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Pending", "Confirmed", "Cancelled"});

        frame.add(new JLabel("Reservation ID to Edit:"));
        frame.add(reservationIdField);
        frame.add(new JLabel("New Room ID:"));
        frame.add(roomIdField);
        frame.add(new JLabel("New Check-in Date (yyyy-mm-dd):"));
        frame.add(checkInField);
        frame.add(new JLabel("New Check-out Date (yyyy-mm-dd):"));
        frame.add(checkOutField);
        frame.add(new JLabel("New Status:"));
        frame.add(statusCombo);

        JButton updateBtn = new JButton("Update Reservation");
        frame.add(updateBtn);

        updateBtn.addActionListener(e -> {
            try {
                int reservationId = Integer.parseInt(reservationIdField.getText());
                int roomId = Integer.parseInt(roomIdField.getText());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                java.util.Date checkInDate = sdf.parse(checkInField.getText());
                java.util.Date checkOutDate = sdf.parse(checkOutField.getText());

                if (!checkOutDate.after(checkInDate)) {
                    JOptionPane.showMessageDialog(frame, "Check-out date must be after check-in date.");
                    return;
                }

                String status = (String) statusCombo.getSelectedItem();

                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(
                             "UPDATE reservations SET room_id = ?, check_in_date = ?, check_out_date = ?, reservation_status = ? WHERE reservation_id = ?")) {

                    stmt.setInt(1, roomId);
                    stmt.setDate(2, new java.sql.Date(checkInDate.getTime()));
                    stmt.setDate(3, new java.sql.Date(checkOutDate.getTime()));
                    stmt.setString(4, status);
                    stmt.setInt(5, reservationId);

                    int updated = stmt.executeUpdate();

                    if (updated > 0) {
                        JOptionPane.showMessageDialog(frame, "✅ Reservation updated!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Reservation ID not found.");
                    }

                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "Reservation ID and Room ID must be numbers.");
            } catch (ParseException pe) {
                JOptionPane.showMessageDialog(frame, "Invalid date format. Use yyyy-mm-dd.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "❌ Error updating reservation.");
            }
        });

        frame.setVisible(true);
    }
}

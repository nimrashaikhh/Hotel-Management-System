package hotel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RoomManagement{

    public void showAddRoomForm() {
        JFrame frame = new JFrame("Add Room");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2));

        JTextField typeField = new JTextField();
        JTextField priceField = new JTextField();
        JCheckBox availableBox = new JCheckBox("Available", true);

        frame.add(new JLabel("Room Type:"));
        frame.add(typeField);
        frame.add(new JLabel("Price:"));
        frame.add(priceField);
        frame.add(new JLabel("Availability:"));
        frame.add(availableBox);

        JButton addBtn = new JButton("Add Room");
        frame.add(addBtn);

        addBtn.addActionListener(e -> {
            String type = typeField.getText();
            String priceStr = priceField.getText();
            boolean available = availableBox.isSelected();

            if (type.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.");
                return;
            }

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO rooms (room_type, price, availability) VALUES (?, ?, ?)")) {

                stmt.setString(1, type);
                stmt.setDouble(2, Double.parseDouble(priceStr));
                stmt.setBoolean(3, available);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(frame, "✅ Room added successfully!");
                frame.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "❌ Failed to add room.");
            }
        });

        frame.setVisible(true);
    }

    public void showViewRooms() {
        JFrame frame = new JFrame("View Rooms");
        frame.setSize(600, 400);
        JTextArea area = new JTextArea();
        area.setEditable(false);

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rooms")) {

            StringBuilder builder = new StringBuilder("Room List:\n\n");
            while (rs.next()) {
                builder.append("ID: ").append(rs.getInt("room_id")).append("\n");
                builder.append("Type: ").append(rs.getString("room_type")).append("\n");
                builder.append("Price: $").append(rs.getDouble("price")).append("\n");
                builder.append("Available: ").append(rs.getBoolean("availability") ? "Yes" : "No").append("\n\n");
            }

            area.setText(builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
            area.setText("Error loading rooms.");
        }

        frame.add(new JScrollPane(area));
        frame.setVisible(true);
    }

    public void showEditRoomForm() {
        JFrame frame = new JFrame("Edit Room");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2));

        JTextField idField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField priceField = new JTextField();
        JCheckBox availableBox = new JCheckBox("Available");

        frame.add(new JLabel("Room ID to Edit:"));
        frame.add(idField);
        frame.add(new JLabel("New Room Type:"));
        frame.add(typeField);
        frame.add(new JLabel("New Price:"));
        frame.add(priceField);
        frame.add(new JLabel("Availability:"));
        frame.add(availableBox);

        JButton updateBtn = new JButton("Update Room");
        frame.add(updateBtn);

        updateBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String type = typeField.getText();
                double price = Double.parseDouble(priceField.getText());
                boolean available = availableBox.isSelected();

                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(
                             "UPDATE rooms SET room_type = ?, price = ?, availability = ? WHERE room_id = ?")) {

                    stmt.setString(1, type);
                    stmt.setDouble(2, price);
                    stmt.setBoolean(3, available);
                    stmt.setInt(4, id);

                    int updated = stmt.executeUpdate();

                    if (updated > 0) {
                        JOptionPane.showMessageDialog(frame, "✅ Room updated!");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Room ID not found.");
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "❌ Error updating room.");
            }
        });

        frame.setVisible(true);
    }
}

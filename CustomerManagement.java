package hotel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerManagement {

    private final List<Customer> customerList = new ArrayList<>();

    // Load all customers from DB
    public void loadCustomersFromDB() {
        customerList.clear();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
                customerList.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Error loading customers from database.");
        }
    }

    // Add a new customer
    public void addCustomer() {
        JFrame frame = new JFrame("Add Customer");
        frame.setSize(400, 500);
        frame.setLayout(new GridLayout(0, 1));

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();

        frame.add(new JLabel("First Name:"));
        frame.add(firstNameField);
        frame.add(new JLabel("Last Name:"));
        frame.add(lastNameField);
        frame.add(new JLabel("Email:"));
        frame.add(emailField);
        frame.add(new JLabel("Phone:"));
        frame.add(phoneField);
        frame.add(new JLabel("Address:"));
        frame.add(addressField);

        JButton submit = new JButton("Save");
        frame.add(submit);

        submit.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields.");
                return;
            }

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO customers (first_name, last_name, email, phone, address) VALUES (?, ?, ?, ?, ?)")) {

                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, phone);
                ps.setString(5, address);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(frame, "✅ Customer added!");
                frame.dispose();
                loadCustomersFromDB(); // refresh cache

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "❌ Error saving customer to database.");
            }
        });

        frame.setVisible(true);
    }

    // View all customers
    public void viewCustomer() {
        loadCustomersFromDB();
        JFrame frame = new JFrame("View Customers");
        frame.setSize(600, 500);
        JTextArea area = new JTextArea();
        area.setEditable(false);

        StringBuilder builder = new StringBuilder("Customers:\n\n");
        for (Customer c : customerList) {
            builder.append("ID: ").append(c.getCustomerId()).append("\n");
            builder.append("Name: ").append(c.getFirstName()).append(" ").append(c.getLastName()).append("\n");
            builder.append("Email: ").append(c.getEmail()).append("\n");
            builder.append("Phone: ").append(c.getPhone()).append("\n");
            builder.append("Address: ").append(c.getAddress()).append("\n\n");
        }

        area.setText(builder.toString());
        frame.add(new JScrollPane(area));
        frame.setVisible(true);
    }

    // Edit a customer
    public void editCustomer() {
        String idStr = JOptionPane.showInputDialog("Enter Customer ID to edit:");

        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int customerId = Integer.parseInt(idStr);
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers WHERE customer_id = ?")) {

                ps.setInt(1, customerId);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "❌ Customer not found.");
                    return;
                }

                String currentFirstName = rs.getString("first_name");
                String currentLastName = rs.getString("last_name");
                String currentEmail = rs.getString("email");
                String currentPhone = rs.getString("phone");
                String currentAddress = rs.getString("address");

                JTextField firstNameField = new JTextField(currentFirstName);
                JTextField lastNameField = new JTextField(currentLastName);
                JTextField emailField = new JTextField(currentEmail);
                JTextField phoneField = new JTextField(currentPhone);
                JTextField addressField = new JTextField(currentAddress);

                Object[] fields = {
                        "First Name:", firstNameField,
                        "Last Name:", lastNameField,
                        "Email:", emailField,
                        "Phone:", phoneField,
                        "Address:", addressField
                };

                int result = JOptionPane.showConfirmDialog(null, fields, "Edit Customer", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String newFirstName = firstNameField.getText();
                    String newLastName = lastNameField.getText();
                    String newEmail = emailField.getText();
                    String newPhone = phoneField.getText();
                    String newAddress = addressField.getText();

                    try (PreparedStatement updatePs = conn.prepareStatement(
                            "UPDATE customers SET first_name = ?, last_name = ?, email = ?, phone = ?, address = ? WHERE customer_id = ?")) {
                        updatePs.setString(1, newFirstName);
                        updatePs.setString(2, newLastName);
                        updatePs.setString(3, newEmail);
                        updatePs.setString(4, newPhone);
                        updatePs.setString(5, newAddress);
                        updatePs.setInt(6, customerId);

                        updatePs.executeUpdate();
                        JOptionPane.showMessageDialog(null, "✅ Customer updated.");
                        loadCustomersFromDB();
                    }
                }

            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Invalid Customer ID.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Error accessing database.");
        }
    }
}

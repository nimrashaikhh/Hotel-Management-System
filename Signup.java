package hotel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {

    JTextField nameField, userField;
    JPasswordField passField, confirmField;
    JButton signupBtn, cancelBtn;

    public Signup() {
        setTitle("Hotel Sign Up");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Sidebar
        JLabel sidebar = new JLabel("Dream Hotel", SwingConstants.CENTER);
        sidebar.setOpaque(true);
        sidebar.setBackground(new Color(10, 10, 100));
        sidebar.setForeground(Color.WHITE);
        sidebar.setFont(new Font("Arial", Font.BOLD, 24));
        sidebar.setBounds(0, 0, 250, 600);
        add(sidebar);

        // Fields + Labels
        int x = 300, y = 100, w = 500, h = 30, gap = 70;

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setBounds(x, y, w, h); add(nameLabel);
        nameField = new JTextField(); nameField.setBounds(x, y + 30, w, h); add(nameField);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(x, y += gap, w, h); add(userLabel);
        userField = new JTextField(); userField.setBounds(x, y + 30, w, h); add(userField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(x, y += gap, w, h); add(passLabel);
        passField = new JPasswordField(); passField.setBounds(x, y + 30, w, h); add(passField);

        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setBounds(x, y += gap, w, h); add(confirmLabel);
        confirmField = new JPasswordField(); confirmField.setBounds(x, y + 30, w, h); add(confirmField);

        // Buttons
        signupBtn = new JButton("Sign Up");
        cancelBtn = new JButton("Cancel");
        signupBtn.setBounds(x + 50, y + 70, 100, 30);
        cancelBtn.setBounds(x + 200, y + 70, 100, 30);
        add(signupBtn); add(cancelBtn);

        signupBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBtn) {
            dispose();
            new Login();
        } else {
            String name = nameField.getText().trim();
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String confirm = new String(confirmField.getPassword()).trim();

            if (name.isEmpty() || user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            try (Connection c = DBConnection.getConnection()) {
                // Check if username already exists
                PreparedStatement check = c.prepareStatement("SELECT username FROM login WHERE username = ?");
                check.setString(1, user);
                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Username already exists. Choose a different one.");
                    return;
                }

                rs.close();
                check.close();

                // Insert new user
                PreparedStatement ps = c.prepareStatement("INSERT INTO login (username, password) VALUES (?, ?)");
                ps.setString(1, user);
                ps.setString(2, pass);
                ps.executeUpdate();
                ps.close();

                JOptionPane.showMessageDialog(this, "Signup Successful!");
                dispose();
                new Login();

            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + sqlEx.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Signup Failed!");
            }
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}



package hotel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    private JTextField username;
    private JPasswordField password;
    private JButton login, cancel;

    Login() {
        setTitle("Hotel Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));

        // ==== HEADER ====
        JLabel header = new JLabel("Welcome to the Dream Hotel", JLabel.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(30, 144, 255));
        header.setForeground(new Color(173, 216, 230));
        header.setFont(new Font("Segoe UI", Font.BOLD, 48));
        header.setPreferredSize(new Dimension(getWidth(), 80));
        add(header, BorderLayout.NORTH);

        // ==== CENTER PANEL ====
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 100));
        centerPanel.setBackground(new Color(240, 248, 255));

        // ==== FORM PANEL ====
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        formPanel.setPreferredSize(new Dimension(350, 250));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(userLabel);

        username = new JTextField();
        formPanel.add(username);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(passLabel);

        password = new JPasswordField();
        formPanel.add(password);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        login = new JButton("Login");
        login.setBackground(new Color(0, 123, 255));
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Segoe UI", Font.BOLD, 14));
        login.addActionListener(this);
        buttonPanel.add(login);

        cancel = new JButton("Cancel");
        cancel.setBackground(new Color(220, 53, 69));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancel.addActionListener(this);
        buttonPanel.add(cancel);

        formPanel.add(buttonPanel);
        centerPanel.add(formPanel);

        // ==== IMAGE ====
        java.net.URL imageURL = getClass().getResource("/hotel/second2.png");
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            centerPanel.add(logoLabel);
        }

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String use = username.getText();
            String pass = new String(password.getPassword());

            try {
                Connection conn = DBConnection.getConnection();

                // ✅ Use your actual table: 'login'
                String query = "SELECT * FROM login WHERE username = ? AND password = ?";

                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, use);
                pst.setString(2, pass);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    setVisible(false);
                    new Dashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                }

                rs.close();
                pst.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}

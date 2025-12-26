
package hotel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Dashboard extends JFrame {

    JPanel mainPanel;
    CustomerManagement customerMgmt = new CustomerManagement();
    RoomManagement roomMgmt = new RoomManagement();

    HotelManagement bookingMgmt = new HotelManagement.BookingManagement();
    HotelManagement paymentMgmt = new HotelManagement.PaymentManagement();
    ReservationManagement reservationMgmt = new ReservationManagement();  

    public Dashboard() {
        setTitle("Dream Hotel - Dashboard");
        setSize(1370, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        try {
            ImageIcon imgIcon = new ImageIcon(getClass().getResource("/hotel/resortt.jpg"));
            Image scaled = imgIcon.getImage().getScaledInstance(1500, 900, Image.SCALE_SMOOTH);
            JLabel background = new JLabel(new ImageIcon(scaled));
            background.setBounds(0, 0, 1500, 900);
            setContentPane(background);
            background.setLayout(null);
        } catch (Exception e) {
            System.out.println("❌ Could not load background image: " + e.getMessage());
        }


        // Nav Bar
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navBar.setBackground(new Color(0, 51, 153));
        navBar.setBounds(0, 0, 1500, 60);
        getContentPane().add(navBar);

        // Main Panel
        mainPanel = new JPanel(null);
        mainPanel.setOpaque(false);
        mainPanel.setBounds(0, 60, 1500, 840);
        getContentPane().add(mainPanel);

        // Title
        JLabel title = new JLabel("WELCOME TO THE DREAM HOTEL");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));

        title.setForeground(Color.WHITE);
        title.setBounds(400, 30, 800, 40);
        mainPanel.add(title);

        // Buttons
        addMainButton(navBar, "Reception", null);
        addMainButton(navBar, "Manage Customers", new String[]{"Add", "View", "Edit"});
        addMainButton(navBar, "Room Management", new String[]{"Add", "View", "Edit"});
        addMainButton(navBar, "Booking Management", new String[]{"New", "View"});
        addMainButton(navBar, "Payment Management", new String[]{"Add", "View"});
        addMainButton(navBar, "Reservation Management", new String[]{"Make", "View"});
        addMainButton(navBar, "Logout", null);

        setVisible(true);
    }


    private void addMainButton(JPanel navBar, String name, String[] subItems) {
        JButton button = new JButton(name);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBackground(new Color(0, 70, 160));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(170, 40));
        navBar.add(button);

        JPanel submenu = null;

        if (subItems != null) {
            submenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            submenu.setBounds(20, 80, 600, 40);
            submenu.setOpaque(false);
            submenu.setVisible(false);
            mainPanel.add(submenu);

            for (String item : subItems) {
                JButton subBtn = new JButton(item);
                subBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
                subBtn.setFocusPainted(false);
                subBtn.setBackground(Color.WHITE);
                submenu.add(subBtn);

                subBtn.addActionListener(e -> handleAction(name, item));
            }
        }

        JPanel finalSubmenu = submenu;
        button.addActionListener(e -> {
            for (Component comp : mainPanel.getComponents()) {
                if (comp instanceof JPanel) comp.setVisible(false);
            }
            if (finalSubmenu != null) finalSubmenu.setVisible(true);
        });
    }

    private void handleAction(String mainCategory, String subItem) {
        switch (mainCategory) {
            case "Manage Customers" -> {
                switch (subItem) {
                    case "Add" -> customerMgmt.addCustomer();
                    case "View" -> customerMgmt.viewCustomer();
                    case "Edit" -> customerMgmt.editCustomer();
                }
            }
            case "Room Management" -> {
                switch (subItem) {
                    case "Add" -> roomMgmt.showAddRoomForm();
                    case "View" -> roomMgmt.showViewRooms();
                    case "Edit" -> roomMgmt.showEditRoomForm();
                }
            }

            case "Booking Management" -> {
                switch (subItem) {
                    case "New" -> bookingMgmt.newBooking();
                    case "View" -> bookingMgmt.viewBooking();
                }
            }
            case "Payment Management" -> {
                switch (subItem) {
                    case "Add" -> paymentMgmt.addPayment();
                    case "View" -> paymentMgmt.viewPayment();
                }
            }
            case "Reservation Management" -> {
                switch (subItem) {
                    case "Make" -> reservationMgmt.showAddReservationForm();
                    case "View" -> reservationMgmt.showViewReservations();
                    case "Edit" -> reservationMgmt.showEditReservationForm();  
                }
            }
            case "Logout" -> {
                dispose();
                new Login();
            }
        }
    }




    public static void main(String[] args) {
        new Dashboard();
    }
}



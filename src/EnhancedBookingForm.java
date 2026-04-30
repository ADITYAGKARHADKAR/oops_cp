import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EnhancedBookingForm extends JFrame {

    Connection connection;

    private JTextField fromCityField, fromCountryField, toCityField, toCountryField;
    private JSpinner dateSpinner;

    private JTextField airlineCodeField, airlineNameField, departureField, arrivalField, priceField, discountField;
    private JPanel flightDetailsPanel;

    private JTextField passengerNameField, passportNumberField, ageField;
    private JPanel passengerPanel;
    private JPanel buttonPanel;
    private JButton bookTicketBtn, cancelTicketBtn;

    // ===== Custom Colors =====
    private final Color DARK_BLUE = new Color(0, 0, 139);
    private final Color VIOLET = new Color(138, 43, 226);
    private final Color GREEN = new Color(0, 128, 0);

    public EnhancedBookingForm(Connection con) {
        this.connection = con;

        setTitle("Ticket Booking Form");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ===== Background Image =====
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/aeroplane.png"));
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // ===== Title =====
        JLabel titleLabel = new JLabel("Integrated Airline Booking System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(DARK_BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        background.add(titleLabel, BorderLayout.NORTH);

        // ===== Main Panel =====
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== Inputs =====
        fromCityField = new JTextField(12);
        fromCountryField = new JTextField(12);
        toCityField = new JTextField(12);
        toCountryField = new JTextField(12);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        gbc.gridx = 0; gbc.gridy = 0; panel.add(createStyledLabel("From City:", VIOLET), gbc);
        gbc.gridx = 1; panel.add(fromCityField, gbc);
        gbc.gridx = 2; panel.add(createStyledLabel("From Country:", VIOLET), gbc);
        gbc.gridx = 3; panel.add(fromCountryField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(createStyledLabel("To City:", VIOLET), gbc);
        gbc.gridx = 1; panel.add(toCityField, gbc);
        gbc.gridx = 2; panel.add(createStyledLabel("To Country:", VIOLET), gbc);
        gbc.gridx = 3; panel.add(toCountryField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(createStyledLabel("Travel Date:", VIOLET), gbc);
        gbc.gridx = 1; panel.add(dateSpinner, gbc);

        // ===== Search Buttons =====
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);

        JButton searchRoute = new JButton("Check Availability");
        searchRoute.setForeground(VIOLET);

        JButton searchTicket = new JButton("Search Ticket");
        searchTicket.setForeground(VIOLET);

        searchPanel.add(searchRoute);
        searchPanel.add(searchTicket);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        panel.add(searchPanel, gbc);

        // ===== Flight Details =====
        flightDetailsPanel = new JPanel(new GridLayout(2, 6, 10, 5));
        javax.swing.border.TitledBorder flightBorder =
                BorderFactory.createTitledBorder("Selected Flight Details");

        flightBorder.setTitleColor(Color.BLACK); // Dark black
        flightBorder.setTitleFont(new Font("Arial", Font.BOLD, 16)); // Bold + bigger

        flightDetailsPanel.setBorder(flightBorder);
        flightDetailsPanel.setOpaque(false);

        airlineCodeField = createReadOnlyField();
        airlineNameField = createReadOnlyField();
        departureField = createReadOnlyField();
        arrivalField = createReadOnlyField();
        priceField = createReadOnlyField();
        discountField = createReadOnlyField();

        flightDetailsPanel.add(createStyledLabel("Airline Code:", VIOLET));
        flightDetailsPanel.add(airlineCodeField);
        flightDetailsPanel.add(createStyledLabel("Airline Name:", VIOLET));
        flightDetailsPanel.add(airlineNameField);
        flightDetailsPanel.add(createStyledLabel("Departure:", VIOLET));
        flightDetailsPanel.add(departureField);
        flightDetailsPanel.add(createStyledLabel("Arrival:", VIOLET));
        flightDetailsPanel.add(arrivalField);
        flightDetailsPanel.add(createStyledLabel("Price:", VIOLET));
        flightDetailsPanel.add(priceField);
        flightDetailsPanel.add(createStyledLabel("Discount:", VIOLET));
        flightDetailsPanel.add(discountField);

        flightDetailsPanel.setVisible(false);

        gbc.gridy = 4;
        panel.add(flightDetailsPanel, gbc);

        // ===== Passenger Panel (FIXED ALIGNMENT) =====
        passengerPanel = new JPanel(new GridBagLayout());
        javax.swing.border.TitledBorder border =
                BorderFactory.createTitledBorder("Passenger Info");

        border.setTitleColor(Color.BLACK); // Dark black
        border.setTitleFont(new Font("Arial", Font.BOLD, 16)); // Bold + bigger

        passengerPanel.setBorder(border);
        passengerPanel.setOpaque(false);

        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.insets = new Insets(5, 10, 5, 10);
        pgbc.fill = GridBagConstraints.HORIZONTAL;

        passengerNameField = new JTextField(15);
        passportNumberField = new JTextField(15);
        ageField = new JTextField(15);

        // Row 1
        pgbc.gridx = 0; pgbc.gridy = 0;
        passengerPanel.add(createStyledLabel("Name:", VIOLET), pgbc);

        pgbc.gridx = 1;
        passengerPanel.add(passengerNameField, pgbc);

        pgbc.gridx = 2;
        passengerPanel.add(createStyledLabel("Passport:", VIOLET), pgbc);

        pgbc.gridx = 3;
        passengerPanel.add(passportNumberField, pgbc);

        // Row 2 (Age aligned properly)
        pgbc.gridx = 0; pgbc.gridy = 1;
        passengerPanel.add(createStyledLabel("Age:", VIOLET), pgbc);

        pgbc.gridx = 1;
        passengerPanel.add(ageField, pgbc);

        passengerPanel.setVisible(false);

        gbc.gridy = 5;
        panel.add(passengerPanel, gbc);

        // ===== Buttons =====
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);


        bookTicketBtn = new JButton("Book Ticket");
        bookTicketBtn.setForeground(VIOLET);

        cancelTicketBtn = new JButton("Cancel");
        cancelTicketBtn.setForeground(VIOLET);

        buttonPanel.add(bookTicketBtn);
        buttonPanel.add(cancelTicketBtn);
        buttonPanel.setVisible(false);

        gbc.gridy = 6;
        panel.add(buttonPanel, gbc);

        background.add(panel, BorderLayout.CENTER);

        // ===== Listeners =====
        searchRoute.addActionListener(e -> handleSearch());

        searchTicket.addActionListener(e ->
                new TicketManagementForm(this, connection).setVisible(true)
        );

        cancelTicketBtn.addActionListener(e -> clearForm());

        bookTicketBtn.addActionListener(e -> {
            try {
                String name = passengerNameField.getText();
                String passport = passportNumberField.getText();
                int age = Integer.parseInt(ageField.getText());

                Date travelDate = (Date) dateSpinner.getValue();

                String airlineCode = airlineCodeField.getText();
                String airlineName = airlineNameField.getText();
                java.sql.Time departure = java.sql.Time.valueOf(departureField.getText());
                java.sql.Time arrival = java.sql.Time.valueOf(arrivalField.getText());
                double price = Double.parseDouble(priceField.getText());
                double discount = Double.parseDouble(discountField.getText());

                Ticket ticket = new Ticket(
                        name, passport, age, travelDate,
                        airlineCode, airlineName, departure, arrival,
                        price, discount, connection
                );

                ticket.fare = price - (price * discount / 100);
                ticket.pnr = ticket.generatePNR();

                if (ticket.bookTicket()) {
                    JOptionPane.showMessageDialog(this, "Booked! PNR: " + ticket.pnr);
                } else {
                    JOptionPane.showMessageDialog(this, "Insert failed!");
                }

                clearForm();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private JLabel createStyledLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createReadOnlyField() {
        JTextField f = new JTextField();
        f.setEditable(false);
        return f;
    }

    private void handleSearch() {
        String fromCity = emptyToNull(fromCityField.getText());
        String fromCountry = emptyToNull(fromCountryField.getText());
        String toCity = emptyToNull(toCityField.getText());
        String toCountry = emptyToNull(toCountryField.getText());

        Date selectedDate = (Date) dateSpinner.getValue();
        java.time.LocalDate localDate = selectedDate.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();

        String day = localDate.getDayOfWeek().toString();

        Optional<List<Route>> route = Operations.findRoute(fromCity, fromCountry, toCity, toCountry);

        if (route.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Route NOT Found!");
            return;
        }

        List<FlightDetails> flights = Operations.getFlightDetails(route.get(), day);

        if (flights.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No flights available!");
            return;
        }

        AirlineSelectionDialog dialog = new AirlineSelectionDialog(this, route.get(), day, flights);
        dialog.setVisible(true);

        FlightDetails selected = dialog.getSelectedFlight();

        if (selected != null) {
            airlineCodeField.setText(selected.getAirlineCode());
            airlineNameField.setText(selected.getAirlineName());
            departureField.setText(selected.getDepartureTiming().toString());
            arrivalField.setText(selected.getArrivalTiming().toString());
            priceField.setText(String.valueOf(selected.getPrice()));
            discountField.setText(String.valueOf(selected.getDiscount()));

            flightDetailsPanel.setVisible(true);
            passengerPanel.setVisible(true);
            buttonPanel.setVisible(true);
        }
    }

    private String emptyToNull(String s) {
        return s == null || s.trim().isEmpty() ? null : s.trim();
    }

    private void clearForm() {
        fromCityField.setText("");
        fromCountryField.setText("");
        toCityField.setText("");
        toCountryField.setText("");

        airlineCodeField.setText("");
        airlineNameField.setText("");
        departureField.setText("");
        arrivalField.setText("");
        priceField.setText("");
        discountField.setText("");

        passengerNameField.setText("");
        passportNumberField.setText("");
        ageField.setText("");

        flightDetailsPanel.setVisible(false);
        passengerPanel.setVisible(false);
        buttonPanel.setVisible(false);
    }
}
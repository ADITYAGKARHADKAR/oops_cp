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

    // Flight details fields (read-only)
    private JTextField airlineCodeField, airlineNameField, departureField, arrivalField, priceField, discountField;
    private JPanel flightDetailsPanel;

    // Passenger info fields
    private JTextField passengerNameField, passportNumberField, ageField;
    private JPanel passengerPanel;
    private JPanel buttonPanel;
    private JButton bookTicketBtn, cancelTicketBtn;

    public EnhancedBookingForm(Connection con) {
        this.connection = con;
        setTitle("Ticket Booking Form");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== Route Input Fields =====
        JLabel fromCityLabel = new JLabel("From City:");
        fromCityField = new JTextField(10);
        JLabel fromCountryLabel = new JLabel("From Country:");
        fromCountryField = new JTextField(10);
        JLabel toCityLabel = new JLabel("To City:");
        toCityField = new JTextField(10);
        JLabel toCountryLabel = new JLabel("To Country:");
        toCountryField = new JTextField(10);
        JLabel travelDateLabel = new JLabel("Travel Date:");
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(fromCityLabel, gbc);
        gbc.gridx = 1; panel.add(fromCityField, gbc);
        gbc.gridx = 2; panel.add(fromCountryLabel, gbc);
        gbc.gridx = 3; panel.add(fromCountryField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(toCityLabel, gbc);
        gbc.gridx = 1; panel.add(toCityField, gbc);
        gbc.gridx = 2; panel.add(toCountryLabel, gbc);
        gbc.gridx = 3; panel.add(toCountryField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(travelDateLabel, gbc);
        gbc.gridx = 1; panel.add(dateSpinner, gbc);

        // ===== Search Buttons Panel =====
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JButton searchRoute = new JButton("Check Availability");
        JButton searchTicket = new JButton("Search Ticket");
        searchPanel.add(searchRoute);
        searchPanel.add(searchTicket);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        panel.add(searchPanel, gbc);

        // ===== Flight Details Panel (initially hidden) =====
        flightDetailsPanel = new JPanel(new GridLayout(1, 6, 10, 5));
        flightDetailsPanel.setBorder(BorderFactory.createTitledBorder("Selected Flight Details"));

        airlineCodeField = createReadOnlyField();
        airlineNameField = createReadOnlyField();
        departureField = createReadOnlyField();
        arrivalField = createReadOnlyField();
        priceField = createReadOnlyField();
        discountField = createReadOnlyField();

        flightDetailsPanel.add(new JLabel("Airline Code:"));
        flightDetailsPanel.add(airlineCodeField);
        flightDetailsPanel.add(new JLabel("Airline Name:"));
        flightDetailsPanel.add(airlineNameField);
        flightDetailsPanel.add(new JLabel("Departure:"));
        flightDetailsPanel.add(departureField);
        flightDetailsPanel.add(new JLabel("Arrival:"));
        flightDetailsPanel.add(arrivalField);
        flightDetailsPanel.add(new JLabel("Price:"));
        flightDetailsPanel.add(priceField);
        flightDetailsPanel.add(new JLabel("Discount:"));
        flightDetailsPanel.add(discountField);

        flightDetailsPanel.setVisible(false);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
        panel.add(flightDetailsPanel, gbc);

        // ===== Passenger Info Panel (initially hidden) =====
        passengerPanel = new JPanel(new GridLayout(1, 6, 10, 5));
        passengerPanel.setBorder(BorderFactory.createTitledBorder("Passenger Info"));
        passengerPanel.add(new JLabel("Name:"));
        passengerNameField = new JTextField();
        passengerPanel.add(passengerNameField);
        passengerPanel.add(new JLabel("Passport No:"));
        passportNumberField = new JTextField();
        passengerPanel.add(passportNumberField);
        passengerPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        passengerPanel.add(ageField);
        passengerPanel.setVisible(false);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 4;
        panel.add(passengerPanel, gbc);

        // ===== Book/Cancel Buttons Panel (initially hidden) =====
        buttonPanel = new JPanel(new FlowLayout());
        bookTicketBtn = new JButton("Book Ticket");
        cancelTicketBtn = new JButton("Cancel Ticket");
        buttonPanel.add(bookTicketBtn);
        buttonPanel.add(cancelTicketBtn);
        buttonPanel.setVisible(false);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 4;
        panel.add(buttonPanel, gbc);

        add(panel);

        // ===== Listeners =====
        searchRoute.addActionListener(e -> handleSearch());

        searchTicket.addActionListener(e -> {
            // Open the TicketManagementForm
            TicketManagementForm ticketForm = new TicketManagementForm(this,connection);
            ticketForm.setVisible(true);
        });

        cancelTicketBtn.addActionListener(e -> clearForm());

        bookTicketBtn.addActionListener(e -> {
            if (passengerNameField.getText().trim().isEmpty() ||
                    passportNumberField.getText().trim().isEmpty() ||
                    ageField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all passenger details!");
                return;
            }

            try {
                // ===== Passenger Info =====
                String name = passengerNameField.getText().trim();
                String passport = passportNumberField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());

                // ===== Travel Date =====
                Date travelDate = (Date) dateSpinner.getValue();

                // ===== Flight Details =====
                String airlineCode = airlineCodeField.getText();
                String airlineName = airlineNameField.getText();
                java.sql.Time departure = java.sql.Time.valueOf(departureField.getText());
                java.sql.Time arrival = java.sql.Time.valueOf(arrivalField.getText());
                double price = Double.parseDouble(priceField.getText());
                double discount = Double.parseDouble(discountField.getText());

                // ===== Confirmation Message =====
                String message = "Please confirm booking details:\n\n" +
                        "Name: " + name + "\n" +
                        "Passport: " + passport + "\n" +
                        "Age: " + age + "\n" +
                        "Travel Date: " + travelDate + "\n" +
                        "Airline Code: " + airlineCode + "\n" +
                        "Airline Name: " + airlineName + "\n" +
                        "Departure: " + departure + "\n" +
                        "Arrival: " + arrival + "\n";

                int choice = JOptionPane.showConfirmDialog(
                        this,
                        message,
                        "Confirm Ticket Booking",
                        JOptionPane.OK_CANCEL_OPTION
                );
                if (choice != JOptionPane.OK_OPTION) return;

                Ticket ticket = new Ticket(
                        name, passport, age, travelDate,
                        airlineCode, airlineName, departure, arrival,
                        price, discount, connection
                );

                ticket.fare = price - (price * discount / 100);
                ticket.pnr = ticket.generatePNR();
                boolean result = ticket.bookTicket();
                if (!result) {
                    JOptionPane.showMessageDialog(this, "Insert failed!");
                    return;
                }
                JOptionPane.showMessageDialog(this,
                        "Ticket booked successfully!\nPNR: " + ticket.pnr);

                clearForm();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! " + ex.getMessage());
            }
        });
    }

    private JTextField createReadOnlyField() {
        JTextField field = new JTextField();
        field.setEditable(false);
        return field;
    }

    private void handleSearch() {
        String fromCity = fromCityField.getText().trim();
        fromCity = fromCity.isEmpty() ? null : fromCity;

        String fromCountry = fromCountryField.getText().trim();
        fromCountry = fromCountry.isEmpty() ? null : fromCountry;

        String toCity = toCityField.getText().trim();
        toCity = toCity.isEmpty() ? null : toCity;

        String toCountry = toCountryField.getText().trim();
        toCountry = toCountry.isEmpty() ? null : toCountry;

        Date selectedDate = (Date) dateSpinner.getValue();
        java.time.LocalDate localDate = selectedDate.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        String day = localDate.getDayOfWeek()
                .getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH);

        Optional<List<Route>> route = Operations.findRoute(fromCity, fromCountry, toCity, toCountry);
        if (route.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Route NOT Found!");
            return;
        }

        List<FlightDetails> availableFlights = Operations.getFlightDetails(route.get(), day);
        if (availableFlights.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No flights available on the selected date!");
            return;
        }
        showAirlinePopup(route.get(), localDate, day, availableFlights);
    }

    private void showAirlinePopup(List<Route> route, java.time.LocalDate date, String day, List<FlightDetails> fd) {
        AirlineSelectionDialog dialog = new AirlineSelectionDialog(
                this,
                route,
                date + " (" + day + ")",
                fd
        );

        dialog.setVisible(true);
        FlightDetails selected = dialog.getSelectedFlight();

        if (selected != null) {
            airlineCodeField.setText(selected.getAirlineCode());
            airlineNameField.setText(selected.getAirlineName());
            departureField.setText(selected.getDepartureTiming().toString());
            arrivalField.setText(selected.getArrivalTiming().toString());
            priceField.setText(String.valueOf(selected.getPrice()));
            discountField.setText(selected.getDiscount() != null ? selected.getDiscount().toString() : "0");

            flightDetailsPanel.setVisible(true);
            passengerPanel.setVisible(true);
            buttonPanel.setVisible(true);
        } else {
            clearForm();
        }
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
        flightDetailsPanel.setVisible(false);

        passengerNameField.setText("");
        passportNumberField.setText("");
        ageField.setText("");
        passengerPanel.setVisible(false);

        buttonPanel.setVisible(false);
    }
}
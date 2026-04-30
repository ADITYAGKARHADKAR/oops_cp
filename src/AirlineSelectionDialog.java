import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AirlineSelectionDialog extends JDialog {

    private FlightDetails selectedFlight = null;

    // ===== Custom Color =====
    private final Color DARK_BLACK = Color.BLACK;

    public AirlineSelectionDialog(
            JFrame parent,
            List<Route> routes,
            String date,
            List<FlightDetails> flights
    ) {
        super(parent, "Select Airline", true);

        if (routes == null || routes.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "No routes available!");
            dispose();
            return;
        }

        // ===== Updated Size =====
        setSize(1200, 750);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Color.WHITE);

        // ===== Top Panel =====
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBackground(Color.WHITE);

        JTextField dateField = new JTextField("Travel Date: " + date);
        dateField.setEditable(false);
        dateField.setBackground(Color.WHITE);
        dateField.setForeground(DARK_BLACK);
        dateField.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField routeCount = new JTextField("Routes Found: " + routes.size());
        routeCount.setEditable(false);
        routeCount.setBackground(Color.WHITE);
        routeCount.setForeground(DARK_BLACK);
        routeCount.setFont(new Font("Arial", Font.BOLD, 14));

        topPanel.add(dateField);
        topPanel.add(routeCount);

        add(topPanel, BorderLayout.NORTH);

        // ===== Center Panel =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        ButtonGroup group = new ButtonGroup();

        // ===== HEADER ROW =====
        JPanel headerPanel = new JPanel(new GridLayout(1, 11, 10, 5));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        headerPanel.add(new JLabel("")); // radio column
        headerPanel.add(createHeaderLabel("From City"));
        headerPanel.add(createHeaderLabel("From Country"));
        headerPanel.add(createHeaderLabel("To City"));
        headerPanel.add(createHeaderLabel("To Country"));
        headerPanel.add(createHeaderLabel("Airline Code"));
        headerPanel.add(createHeaderLabel("Airline Name"));
        headerPanel.add(createHeaderLabel("Departure"));
        headerPanel.add(createHeaderLabel("Arrival"));
        headerPanel.add(createHeaderLabel("Price"));
        headerPanel.add(createHeaderLabel("Discount"));

        centerPanel.add(headerPanel);

        // ===== DATA ROWS =====
        for (Route route : routes) {
            for (FlightDetails fd : flights) {

                if (route.getRouteId() == fd.getRouteId()) {

                    JPanel rowPanel = new JPanel(new GridLayout(1, 11, 10, 5));
                    rowPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    rowPanel.setBackground(Color.WHITE);

                    JRadioButton radio = new JRadioButton();
                    radio.setBackground(Color.WHITE);
                    radio.addActionListener(e -> selectedFlight = fd); // action listener on radio
                    group.add(radio);

                    rowPanel.add(radio);
                    rowPanel.add(createStyledLabel(route.getFromCity()));
                    rowPanel.add(createStyledLabel(route.getFromCountry()));
                    rowPanel.add(createStyledLabel(route.getToCity()));
                    rowPanel.add(createStyledLabel(route.getToCountry()));
                    rowPanel.add(createStyledLabel(fd.getAirlineCode()));
                    rowPanel.add(createStyledLabel(fd.getAirlineName()));
                    rowPanel.add(createStyledLabel(fd.getDepartureTiming().toString()));
                    rowPanel.add(createStyledLabel(fd.getArrivalTiming().toString()));
                    rowPanel.add(createStyledLabel(String.valueOf(fd.getPrice())));
                    rowPanel.add(createStyledLabel(String.valueOf(
                            fd.getDiscount() != null ? fd.getDiscount() : 0
                    )));

                    centerPanel.add(rowPanel);
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Bottom Panel =====
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        JButton continueBtn = new JButton("Continue");
        continueBtn.setForeground(DARK_BLACK);
        continueBtn.setFont(new Font("Arial", Font.BOLD, 14));

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setForeground(DARK_BLACK);
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));

        bottomPanel.add(continueBtn);
        bottomPanel.add(cancelBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // ===== Actions =====
        continueBtn.addActionListener(e -> {
            if (selectedFlight == null) {
                JOptionPane.showMessageDialog(this, "Please select a flight!");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Selected:\n" +
                                selectedFlight.getAirlineName() +
                                "\nFrom Route ID: " + selectedFlight.getRouteId() +
                                "\nDeparture: " + selectedFlight.getDepartureTiming() +
                                "\nArrival: " + selectedFlight.getArrivalTiming() +
                                "\nPrice: " + selectedFlight.getPrice());

                dispose();
            }
        });

        cancelBtn.addActionListener(e -> {
            selectedFlight = null;
            dispose();
        });
    }

    // ===== DATA LABEL =====
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(DARK_BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

    // ===== HEADER LABEL =====
    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(DARK_BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    public FlightDetails getSelectedFlight() {
        return selectedFlight;
    }
}
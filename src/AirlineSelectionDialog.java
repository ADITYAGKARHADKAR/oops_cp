import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AirlineSelectionDialog extends JDialog {

    private FlightDetails selectedFlight = null;

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

        setSize(1200, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // ===== Top Panel =====
        JPanel topPanel = new JPanel(new GridLayout(1, 2));

        JTextField dateField = new JTextField("Travel Date: " + date);
        dateField.setEditable(false);

        JTextField routeCount = new JTextField("Routes Found: " + routes.size());
        routeCount.setEditable(false);

        topPanel.add(dateField);
        topPanel.add(routeCount);

        add(topPanel, BorderLayout.NORTH);

        // ===== Center Panel =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        ButtonGroup group = new ButtonGroup();


        for (Route route : routes) {

            for (FlightDetails fd : flights) {


                if (route.getRouteId() == fd.getRouteId()) {

                    JPanel rowPanel = new JPanel(new GridLayout(1, 11, 10, 5));
                    rowPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                    JRadioButton radio = new JRadioButton();
                    radio.addActionListener(e -> selectedFlight = fd);
                    group.add(radio);

                    rowPanel.add(radio);
                    rowPanel.add(new JLabel(route.getFromCity()));
                    rowPanel.add(new JLabel(route.getFromCountry()));
                    rowPanel.add(new JLabel(route.getToCity()));
                    rowPanel.add(new JLabel(route.getToCountry()));
                    rowPanel.add(new JLabel(fd.getAirlineCode()));
                    rowPanel.add(new JLabel(fd.getAirlineName()));
                    rowPanel.add(new JLabel(fd.getDepartureTiming().toString()));
                    rowPanel.add(new JLabel(fd.getArrivalTiming().toString()));
                    rowPanel.add(new JLabel(String.valueOf(fd.getPrice())));
                    rowPanel.add(new JLabel(String.valueOf(
                            fd.getDiscount() != null ? fd.getDiscount() : 0
                    )));

                    centerPanel.add(rowPanel);
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Bottom Panel =====
        JPanel bottomPanel = new JPanel();

        JButton continueBtn = new JButton("Continue");
        JButton cancelBtn = new JButton("Cancel");

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

    public FlightDetails getSelectedFlight() {
        return selectedFlight;
    }
}
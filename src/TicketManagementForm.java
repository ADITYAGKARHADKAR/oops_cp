import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;

public class TicketManagementForm extends JDialog {

    private Connection connection;

    private JTextField pnrField;
    private JButton searchBtn;

    private JTextField nameField, passportField, ageField, dateField,
            airlineCodeField, airlineNameField, departureField, arrivalField,
            priceField, discountField, fareField, pnrDisplayField;

    private JButton updateBtn, deleteBtn, cancelBtn;

    private final Color DARK_BLACK = Color.BLACK;

    // ===== Styled Label =====
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(DARK_BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private void clearForm() {
        nameField.setText("");
        passportField.setText("");
        ageField.setText("");
        dateField.setText("");
        airlineCodeField.setText("");
        airlineNameField.setText("");
        departureField.setText("");
        arrivalField.setText("");
        priceField.setText("");
        discountField.setText("");
        fareField.setText("");
        pnrDisplayField.setText("");

        updateBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }

    public TicketManagementForm(JFrame parent, Connection con) {
        super(parent, "Ticket Management", true);
        this.connection = con;

        setSize(1200, 750);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // ===== Background Image =====
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/airport.png"));
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== Row 1: PNR =====
        JLabel pnrLabel = createStyledLabel("Enter PNR:");
        pnrField = new JTextField(15);
        searchBtn = new JButton("Search");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(pnrLabel, gbc);
        gbc.gridx = 1; panel.add(pnrField, gbc);
        gbc.gridx = 2; panel.add(searchBtn, gbc);

        // ===== Ticket Fields =====
        String[] labels = {
                "Passenger Name", "Passport Number", "Age", "Travel Date",
                "Airline Code", "Airline Name", "Departure", "Arrival",
                "Price", "Discount", "Fare", "PNR"
        };

        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {

            JLabel label = createStyledLabel(labels[i] + ":");
            JTextField field = new JTextField(15);

            if (!labels[i].equals("Passenger Name") &&
                    !labels[i].equals("Passport Number") &&
                    !labels[i].equals("Age")) {
                field.setEditable(false);
            }

            fields[i] = field;

            gbc.gridx = (i % 4) * 2;
            gbc.gridy = 1 + i / 4;
            panel.add(label, gbc);

            gbc.gridx = (i % 4) * 2 + 1;
            panel.add(field, gbc);
        }

        nameField = fields[0];
        passportField = fields[1];
        ageField = fields[2];
        dateField = fields[3];
        airlineCodeField = fields[4];
        airlineNameField = fields[5];
        departureField = fields[6];
        arrivalField = fields[7];
        priceField = fields[8];
        discountField = fields[9];
        fareField = fields[10];
        pnrDisplayField = fields[11];

        // ===== Buttons =====
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        cancelBtn = new JButton("Cancel");

        updateBtn.setEnabled(false);
        deleteBtn.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(cancelBtn);

        gbc.gridx = 0;
        gbc.gridy = 1 + (labels.length + 3) / 4;
        gbc.gridwidth = 8;
        panel.add(buttonPanel, gbc);

        background.add(panel, BorderLayout.CENTER);

        // ===== Actions =====
        searchBtn.addActionListener(e -> {
            String pnrInput = pnrField.getText().trim();

            if (pnrInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "PNR field is mandatory!");
                return;
            }

            try {
                ResultSet rs = Operations.searchTicket(connection, pnrInput);

                if (rs != null && rs.next()) {
                    nameField.setText(rs.getString("PassengerName"));
                    passportField.setText(rs.getString("PassportNumber"));
                    ageField.setText(String.valueOf(rs.getInt("age")));
                    dateField.setText(rs.getDate("travel_date").toString());
                    airlineCodeField.setText(rs.getString("AirlineCode"));
                    airlineNameField.setText(rs.getString("AirlineName"));
                    departureField.setText(rs.getTime("Departure").toString());
                    arrivalField.setText(rs.getTime("Arrival").toString());
                    priceField.setText(String.valueOf(rs.getDouble("price")));
                    discountField.setText(String.valueOf(rs.getDouble("discount")));
                    fareField.setText(String.valueOf(rs.getDouble("fare")));
                    pnrDisplayField.setText(rs.getString("pnr"));

                    updateBtn.setEnabled(true);
                    deleteBtn.setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(this, "Ticket not found!");
                    clearForm();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                clearForm();
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                String pnr = pnrField.getText().trim();
                String name = nameField.getText();
                String passport = passportField.getText();
                int age = Integer.parseInt(ageField.getText());

                int rowsUpdated = Operations.updateTicket(connection, pnr, name, passport, age);

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Updated successfully!");
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        deleteBtn.addActionListener(e -> {
            String pnrInput = pnrField.getText().trim();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Delete ticket with PNR: " + pnrInput + "?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                int rowsDeleted = Operations.deleteTicket(connection, pnrInput);

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Deleted successfully!");
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Delete failed!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        cancelBtn.addActionListener(e -> dispose());
    }
}
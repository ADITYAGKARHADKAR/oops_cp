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
        super(parent, "Ticket Management", true); // Modal dialog
        this.connection = con;

        // ===== Maximized dialog =====
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocation(0, 0);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== Row 1: PNR search =====
        JLabel pnrLabel = new JLabel("Enter PNR:");
        pnrField = new JTextField(15);
        searchBtn = new JButton("Search");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(pnrLabel, gbc);
        gbc.gridx = 1; panel.add(pnrField, gbc);
        gbc.gridx = 2; panel.add(searchBtn, gbc);

        // ===== Row 2+: Ticket details =====
        String[] labels = {"Passenger Name", "Passport Number", "Age", "Travel Date",
                "Airline Code", "Airline Name", "Departure", "Arrival",
                "Price", "Discount", "Fare", "PNR"};

        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i] + ":");
            JTextField field = new JTextField(15);

            // Make fields read-only except Passenger Name, Passport Number, Age
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

        // ===== Row after details: Update/Delete/Cancel buttons =====
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        cancelBtn = new JButton("Cancel");

        // Disable Update/Delete initially
        updateBtn.setEnabled(false);
        deleteBtn.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(cancelBtn);

        gbc.gridx = 0;
        gbc.gridy = 1 + (labels.length + 3) / 4;
        gbc.gridwidth = 8;
        panel.add(buttonPanel, gbc);

        add(panel);

        // ===== Button actions =====
        searchBtn.addActionListener(e -> {
            String pnrInput = pnrField.getText().trim();

            if (pnrInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "PNR field is mandatory!");
                return;
            }

            try {
                ResultSet rs = Operations.searchTicket(connection, pnrInput);

                if (rs != null && rs.next()) {
                    // Populate fields
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

                    // Enable Update/Delete after successful search
                    updateBtn.setEnabled(true);
                    deleteBtn.setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(this, "Ticket not found for PNR: " + pnrInput);
                    clearForm();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error retrieving ticket: " + ex.getMessage());
                ex.printStackTrace();
                clearForm();
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                // Get values from text fields
                String pnr = pnrField.getText();
                String name = nameField.getText();
                String passport = passportField.getText();
                int age = Integer.parseInt(ageField.getText());

                // Call your updateTicket method
                int rowsUpdated = Operations.updateTicket(connection, pnr, name, passport, age);

                // Show success/failure message
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Ticket updated successfully!");
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Error updating given PNR.");
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Invalid age. Please enter a number.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating ticket: " + ex.getMessage());
            }
        });
        deleteBtn.addActionListener(e -> {
            String pnrInput = pnrField.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the ticket with PNR: " + pnrInput + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return; // user cancelled
            }

            try {
                int rowsDeleted = Operations.deleteTicket(connection, pnrInput);

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Ticket deleted successfully!");
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Ticket not found or deletion failed.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting ticket: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cancelBtn.addActionListener(e -> this.dispose()); // Close dialog
    }
}
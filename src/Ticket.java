import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.zip.CheckedOutputStream;

public class Ticket extends PassengerDetails {

    Date travel_date;
    String AirlineCode;
    String AirlineName;
    Time Departure;
    Time Arrival;
    double price;
    double discount;
    double fare;
    String pnr;
    Connection connection;

    public Ticket(String passengerName, String passportNumber, int age, Date travel_date, String airlineCode, String airlineName, Time departure, Time arrival, double price, double discount,Connection con) {
        super(passengerName,passportNumber,age);
        this.travel_date = travel_date;
        AirlineCode = airlineCode;
        AirlineName = airlineName;
        Departure = departure;
        Arrival = arrival;
        this.price = price;
        this.discount = discount;
        this.connection=con;
    }



    public String generatePNR() {
        // Format: yyyyMMddHHmmss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateTime = LocalDateTime.now().format(formatter);

        // Random 3-digit number
        int random = 100 + new Random().nextInt(900);

        return AirlineCode.toUpperCase() + dateTime + random;
    }

    public boolean bookTicket(){
        String sql = "INSERT INTO Ticket " +
                "(PassengerName, PassportNumber, age, travel_date, AirlineCode, AirlineName, " +
                "Departure, Arrival, price, discount, fare, pnr) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setString(1,this.PassengerName);
            pstmt.setString(2,this.PassportNumber);
            pstmt.setInt(3,this.age);
            pstmt.setDate(4, new java.sql.Date(this.travel_date.getTime()));
            pstmt.setString(5,this.AirlineCode);
            pstmt.setString(6,this.AirlineName);
            pstmt.setTime(7,this.Departure);
            pstmt.setTime(8,this.Arrival);
            pstmt.setDouble(9,this.price);
            pstmt.setDouble(10,this.discount);
            pstmt.setDouble(11,this.fare);
            pstmt.setString(12,this.pnr);
           int rows= pstmt.executeUpdate();

            if(rows==1){
                return true;
            }else{
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}

import javax.swing.*;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Optional;

public class    Booking {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(java.sql.DriverManager.getDrivers().hasMoreElements());
        MyConnection con = new MyConnection();
        con.getdata();
       Optional<Connection> connection=con.pullConnection();
       Operations c=new Operations();
       c.cacheRoute(connection,"Routes");
       c.cacheRoute(connection,"flightdetails");
      SwingUtilities.invokeLater(() -> new EnhancedBookingForm(connection.get()).setVisible(true));
    }
}

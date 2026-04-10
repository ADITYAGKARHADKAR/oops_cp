import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.sql.Connection;

import static java.sql.DriverManager.getConnection;


public class MyConnection {
    String URL;
    String USER;
    String PASSWORD;

    public MyConnection() {
        // constructor
    }

    public void getdata() throws FileNotFoundException {
        String filepath = "C:\\Users\\LENOVO\\eclipse-workspace\\Airline_reservation1\\src\\profile.ini";
        HashMap<String, String> credentials = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;

            // Reading file  profile.ini and getting data from file into hashmap
            while ((line = br.readLine()) != null) {
                System.out.println("Line=" + line);
                String[] data = line.split("=");
                credentials.put(data[0], data[1]);
            }
            System.out.println("Credentials:" + credentials);
            URL = credentials.get("URL");
            USER = credentials.get("USER");
            PASSWORD = credentials.get("PASSWORD");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Optional <Connection> pullConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            return Optional.of(con);
        }catch(SQLException e){
            e.printStackTrace();
            return Optional.empty();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}





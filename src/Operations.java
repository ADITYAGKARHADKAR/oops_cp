import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Operations implements ServerStart{
    public static List<Route>  route_info=new ArrayList<>();
    public static List<FlightDetails> fd=new ArrayList<>();

    public static int deleteTicket(Connection con,String pnr){
        int rows=0;
        try{
            String sql="delete from ticket where pnr=?";
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.setString(1,pnr);
            rows=pstmt.executeUpdate();
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;

    }
    public static int updateTicket(Connection con,String pnr,String name,String Passport,int age){
        int rows=0;
        String sql="Update ticket set passengername=?,passportnumber=?,age=? where pnr=?";
        try{
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.setString(2,Passport);
            pstmt.setInt(3,age);
            pstmt.setString(4,pnr);
            rows=pstmt.executeUpdate();
            return rows;

        } catch (java.sql.SQLException e){
            throw new RuntimeException("Database error while updating ticket", e);
        }catch (Exception e) {
            throw new RuntimeException("Unexpected error while updating ticket", e);
        }
        //return rows;
    }

    public static ResultSet searchTicket(Connection con,String pnr){
        try{
            String sql="Select * from ticket where pnr=?";
            PreparedStatement pstmt=con.prepareStatement(sql);
            pstmt.setString(1,pnr);
            ResultSet rs= pstmt.executeQuery();
            return rs;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Optional <List<Route>> findRoute(String fromCity, String fromCountry, String toCity, String toCountry) {

        List<Route> collect1 = Operations.route_info.stream()
                .filter(r -> fromCity == null ||
                        r.getFromCity().toLowerCase().contains(fromCity.toLowerCase()))

                .filter(r -> fromCountry == null ||
                        r.getFromCountry().toLowerCase().contains(fromCountry.toLowerCase()))

                .filter(r -> toCity == null ||
                        r.getToCity().toLowerCase().contains(toCity.toLowerCase()))

                .filter(r -> toCountry == null ||
                        r.getToCountry().toLowerCase().contains(toCountry.toLowerCase()))

                .collect(Collectors.toList());
        System.out.println("Testing");
        for(Route x :collect1){
            System.out.println(x.getRouteId()+" "+x.getFromCity()+" "+x.getFromCountry()+" "+x.getToCity()+" "+x.getToCountry());
        }
        return Optional.of(collect1);

    }


    public static List<FlightDetails> getFlightDetails(List<Route> routes, String day) {

        List<FlightDetails> available_flights =routes.stream()
                .flatMap(x -> fd.stream()
                        .filter(y ->
                                y.getRouteId() == x.getRouteId()
                                        && y.getDayOfWeek() != null
                                        && (
                                        y.getDayOfWeek().equalsIgnoreCase("Daily")
                                                || y.getDayOfWeek().toLowerCase().contains(day.toLowerCase())
                                )
                        )
                )
                .collect(Collectors.toList());
        for(FlightDetails x :available_flights){
            System.out.println(x.getAirlineCode()+" "+x.getAirlineName()+" "+x.getRouteId()+" " + x.getAirRouteId()+" "+x.getDayOfWeek());
        }
        return available_flights;
    }



    @Override
    public void cacheRoute(Optional<Connection> con, String tname) {
        if(tname.equals("Routes")&& con.isPresent()) {
            String sql = "select route_id,from_city,from_country,to_city,to_country from routes";

            try (PreparedStatement pstmt = con.get().prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Route route = new Route(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    );

                    route_info.add(route);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(tname.equalsIgnoreCase("flightdetails")&& con.isPresent()){
            String sql="select a.air_route_id,route_id,airline_code,airline_name,day_of_week,artp_id,departure_timing,arrival_timing,price,discount from airline_routes a inner join  airline_route_timings_prices b on a.air_route_id=b.air_route_id";
            try(PreparedStatement pstmt=con.get().prepareStatement(sql)){
                ResultSet rs=pstmt.executeQuery();
                while(rs.next()){
                    FlightDetails f=new FlightDetails(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getInt(6),
                            rs.getTime(7),
                            rs.getTime(8),
                            rs.getDouble(9),
                            rs.getDouble(10)
                    );
                    fd.add(f);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}


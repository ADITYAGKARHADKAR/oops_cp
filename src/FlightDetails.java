import java.sql.Time;

public class FlightDetails {
    private int airRouteId;
    private int routeId;
    private String airlineCode;
    private String airlineName;
    private String dayOfWeek;
    private int artpId;
    private Time departureTiming;
    private Time arrivalTiming;
    private double price;
    private Double discount; // nullable


    public FlightDetails(int airRouteId, int routeId, String airlineCode, String airlineName, String dayOfWeek, int artpId, Time departureTiming, Time arrivalTiming, double price, Double discount) {
        this.airRouteId = airRouteId;
        this.routeId = routeId;
        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
        this.dayOfWeek = dayOfWeek;
        this.artpId = artpId;
        this.departureTiming = departureTiming;
        this.arrivalTiming = arrivalTiming;
        this.price = price;
        this.discount = discount;
    }

    public int getAirRouteId() {
        return airRouteId;
    }

    public void setAirRouteId(int airRouteId) {
        this.airRouteId = airRouteId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getArtpId() {
        return artpId;
    }

    public void setArtpId(int artpId) {
        this.artpId = artpId;
    }

    public Time getDepartureTiming() {
        return departureTiming;
    }

    public void setDepartureTiming(Time departureTiming) {
        this.departureTiming = departureTiming;
    }

    public Time getArrivalTiming() {
        return arrivalTiming;
    }

    public void setArrivalTiming(Time arrivalTiming) {
        this.arrivalTiming = arrivalTiming;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }


}

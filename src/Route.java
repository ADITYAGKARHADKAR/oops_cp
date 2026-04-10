public class Route {

    private int routeId;
    private String fromCity;
    private String fromCountry;
    private String toCity;
    private String toCountry;


    public Route(int routeId, String fromCity, String fromCountry, String toCity, String toCountry) {
        this.routeId = routeId;
        this.fromCity = fromCity;
        this.fromCountry = fromCountry;
        this.toCity = toCity;
        this.toCountry = toCountry;
    }



    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }




}
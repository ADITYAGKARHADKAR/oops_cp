public class PassengerDetails {
    public String getPassengerName() {
        return PassengerName;
    }

    public void setPassengerName(String passengerName) {
        PassengerName = passengerName;
    }

    public String getPassportNumber() {
        return PassportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        PassportNumber = passportNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PassengerDetails(String passengerName, String passportNumber, int age) {
        PassengerName = passengerName;
        PassportNumber = passportNumber;
        this.age = age;
    }

    String PassengerName;
    String PassportNumber;
    int age;

}

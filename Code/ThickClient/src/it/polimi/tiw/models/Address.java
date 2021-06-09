package it.polimi.tiw.models;

public class Address {
    String address;
    String city;
    int    cap;
    String country;

    public Address(String address, String city, int cap, String country)
    {
        this.address = address;
        this.cap     = cap;
        this.city    = city;
        this.country = country;
    }

    public String getAddress()
    {
        return this.address;
    }

    public int getCap() {
        return cap;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}

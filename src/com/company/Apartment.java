package com.company;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class Apartment {

    private String street;
    private Integer houseNumber;
    private Integer floor;
    private Boolean hasElevator;
    private Double price;

    public Apartment(String street, Integer houseNumber, Integer floor, Boolean hasElevator, Double price) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.floor = floor;
        this.hasElevator = hasElevator;

        this.price = price;

        if (!this.hasElevator) {

            this.price = price * 0.6; //1000 -> 600
        }

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean getHasElevator() {
        return hasElevator;
    }

    public void setHasElevator(Boolean hasElevator) {
        this.hasElevator = hasElevator;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return Objects.equals(street, apartment.street) &&
                Objects.equals(houseNumber, apartment.houseNumber) &&
                Objects.equals(floor, apartment.floor) &&
                Objects.equals(hasElevator, apartment.hasElevator) &&
                Objects.equals(price, apartment.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(street, houseNumber, floor, hasElevator, price);
    }

    @Override
    public String toString() {
//        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
//        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        //Precision (scale): 0.55120012301231230123 ->  0.12
        //Rounding: DOWN

        return String.format("Street: %s, House Number: %s, Floor: %s, Has Elevator: %s, Price: %s",

                this.street,
                this.houseNumber,
                this.floor,
                this.hasElevator ? "YES" : "NO",
                this.price);
    }
}

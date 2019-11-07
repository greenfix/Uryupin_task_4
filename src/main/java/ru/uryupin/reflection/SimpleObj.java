package ru.uryupin.reflection;

public class SimpleObj {
    private boolean withMileage;
    private byte seats;
    private char serial;
    private short cylinders;
    private int price;
    private long mileage;
    private float engineVolume;
    private double rate;
    private String model;
    private Integer weight;
    private Transmission transmission;

    public void setWithMileage(boolean withMileage) {
        this.withMileage = withMileage;
    }

    public void setSeats(byte seats) {
        this.seats = seats;
    }

    public void setSerial(char serial) {
        this.serial = serial;
    }

    public void setCylinders(short cylinders) {
        this.cylinders = cylinders;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public void setEngineVolume(float engineVolume) {
        this.engineVolume = engineVolume;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public boolean isWithMileage() {
        return withMileage;
    }

    public byte getSeats() {
        return seats;
    }

    public char getSerial() {
        return serial;
    }

    public short getCylinders() {
        return cylinders;
    }

    public int getPrice() {
        return price;
    }

    public long getMileage() {
        return mileage;
    }

    public float getEngineVolume() {
        return engineVolume;
    }

    public double getRate() {
        return rate;
    }

    public String getModel() {
        return model;
    }

    public Integer getWeight() {
        return weight;
    }

    public Transmission getTransmission() {
        return transmission;
    }
}


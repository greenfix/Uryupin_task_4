package ru.uryupin.reflection;

class SimpleObj {
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

    void setWithMileage() {
        this.withMileage = true;
    }

    void setSeats() {
        this.seats = (byte) 4;
    }

    void setSerial() {
        this.serial = 'B';
    }

    void setCylinders() {
        this.cylinders = (short) 8;
    }

    void setPrice() {
        this.price = 680000;
    }

    void setMileage() {
        this.mileage = 150000;
    }

    void setEngineVolume() {
        this.engineVolume = (float) 1.995;
    }

    void setRate() {
        this.rate = 0.02458;
    }

    void setModel() {
        this.model = "Lexus IS200d";
    }

    void setWeight() {
        this.weight = 1545;
    }

    void setTransmission() {
        this.transmission = Transmission.AUTO;
    }

    boolean isWithMileage() {
        return withMileage;
    }

    byte getSeats() {
        return seats;
    }

    char getSerial() {
        return serial;
    }

    short getCylinders() {
        return cylinders;
    }

    int getPrice() {
        return price;
    }

    long getMileage() {
        return mileage;
    }

    float getEngineVolume() {
        return engineVolume;
    }

    double getRate() {
        return rate;
    }

    String getModel() {
        return model;
    }

    Integer getWeight() {
        return weight;
    }

    Transmission getTransmission() {
        return transmission;
    }
}


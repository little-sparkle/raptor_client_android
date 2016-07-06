package com.littlesparkle.growler.raptor.entity;

/**
 * Created by dell on 2016/7/5.
 */
public class PositionEntity {
    public double latitue;

    public double longitude;

    public String address;

    public String city;

    public PositionEntity() {

    }

    public PositionEntity(double latitude, double longtitude, String address, String city) {
        this.latitue = latitude;
        this.longitude = longtitude;
        this.address = address;
        this.city = city;
    }
}

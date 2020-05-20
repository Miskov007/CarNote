package com.example.carnote.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Model danych pojedyńczego tankowania
 */
public class TankUpRecord implements Serializable
{
    /**
     * Data wykonania tankowania
     */
    private Date tankUpDate;
    /**
     * Przebieg auta w momencie tankowania
     */
    private Integer mileage;
    /**
     * Dolane litry
     */
    private Integer liters;
    /**
     * Zapłacona kwota wPLN
     */
    private Integer costInPLN;

    public TankUpRecord(Date tankUpDate, Integer mileage, Integer liters, Integer costInPLN) {
        this.tankUpDate = tankUpDate;
        this.mileage = mileage;
        this.liters = liters;
        this.costInPLN = costInPLN;
    }

    public Date getTankUpDate() {
        return tankUpDate;
    }

    public void setTankUpDate(Date tankUpDate) {
        this.tankUpDate = tankUpDate;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getLiters() {
        return liters;
    }

    public void setLiters(Integer liters) {
        this.liters = liters;
    }

    public Integer getCostInPLN() {
        return costInPLN;
    }

    public void setCostInPLN(Integer costInPLN) {
        this.costInPLN = costInPLN;
    }
}

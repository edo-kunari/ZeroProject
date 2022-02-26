package com.example.zeroproject.Presentation.Repository.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "currency")
public class CurrencyDTO {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    public int id;

    @ColumnInfo
    public String convertFrom;
    @ColumnInfo
    public String convertTo;
    @ColumnInfo
    public double conversionRate;

    public CurrencyDTO(){

    }

    public CurrencyDTO(String convertFrom, String convertTo, double conversionRate){
        this.conversionRate = conversionRate;
        this.convertFrom = convertFrom;
        this.convertTo = convertTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConvertFrom() {
        return convertFrom;
    }

    public void setConvertFrom(String convertFrom) {
        this.convertFrom = convertFrom;
    }

    public String getConvertTo() {
        return convertTo;
    }

    public void setConvertTo(String convertTo) {
        this.convertTo = convertTo;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }
}

package com.example.zeroproject.Presentation.Repository.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CurrencyDAO {
    @Insert
    void addStock(CurrencyDTO stock);

    @Delete
    void deleteStock(CurrencyDTO stock);

    @Update
    void updateCurrency(CurrencyDTO stock);

    @Query("SELECT * FROM currency WHERE id = :id")
    LiveData<CurrencyDTO> getById(int id);

    @Query("SELECT * FROM currency WHERE convertFrom = :convertFrom")
    LiveData<List<CurrencyDTO>> getAllConvertToByConvertFrom(String convertFrom);

    @Query("SELECT * FROM currency")
    LiveData<List<CurrencyDTO>> getAllConvertFrom();

    @Query ("SELECT conversionRate FROM currency WHERE convertFrom = :convertFrom AND convertTo = :convertTo")
    LiveData<List<Double>> getByPair(String convertFrom, String convertTo);

    @Query ("UPDATE currency SET conversionRate = :conversionRate WHERE convertFrom = :convertFrom AND" +
            " convertTo = :convertTo")
    void updateConversionRate(double conversionRate, String convertFrom, String convertTo);

}

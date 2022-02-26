package com.example.zeroproject.Presentation.Repository.Room;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class RoomRepository {
    private CurrencyDAO currencyDAO;
    private LiveData<List<CurrencyDTO>> allDTO;

    public RoomRepository(Application application){
        RoomCurrencyDatabase database = RoomCurrencyDatabase.getDatabase(application);
        database = Room.databaseBuilder(application.getApplicationContext(), RoomCurrencyDatabase.class, "currency_database")
                .fallbackToDestructiveMigration()
                .build();
        currencyDAO = database.currencyDAO();
    }

    public void insert(CurrencyDTO currencyDTO){
        RoomCurrencyDatabase.databaseWriteExecutor.execute(() -> {
            currencyDAO.addStock(((CurrencyDTO) currencyDTO));
        });
    }

    public void updateConversionRate(double conversionRate, String convertFrom, String convertTo){
        currencyDAO.updateConversionRate( conversionRate,  convertFrom,  convertTo);
    }

    public LiveData<List<CurrencyDTO>> getAllConvertToByConvertFrom(String convertFrom){
        return currencyDAO.getAllConvertToByConvertFrom(convertFrom);
    }

    public LiveData<List<CurrencyDTO>> getAllConvertFrom(){

        return currencyDAO.getAllConvertFrom();
    }

    public LiveData<List<Double>> getByPair(String convertFrom, String convertTo){
        return currencyDAO.getByPair(convertFrom, convertTo);
    }
}

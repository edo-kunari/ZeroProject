package com.example.zeroproject.Presentation.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.zeroproject.Domain.Model.PostPojo;
import com.example.zeroproject.Presentation.Repository.Retrofit.ExChangeLogic;
import com.example.zeroproject.Presentation.Repository.Room.CurrencyDTO;
import com.example.zeroproject.Presentation.Repository.Room.RoomRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private RoomRepository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new RoomRepository(application);
    }

    public LiveData<PostPojo> getValue(String convertFrom, String convertTo){
        return new ExChangeLogic().getValue(convertFrom, convertTo);
    }

    public LiveData<List<CurrencyDTO>> getAllConvertToByConvertFrom(String convertFrom){
        return repository.getAllConvertToByConvertFrom(convertFrom);
    }

    public void insertDTO(String convertFrom, String convertTo, double conversionRate){
        repository.insert(new CurrencyDTO(convertFrom, convertTo, conversionRate));
    }

    public LiveData<List<CurrencyDTO>> getAllConvertFrom(){
        return repository.getAllConvertFrom();
    }

    public LiveData<List<Double>> getByPair(String convertFrom, String convertTo){
        return repository.getByPair(convertFrom, convertTo);
    }

    public void updateConversionRate(double conversionRate, String convertFrom, String convertTo){
        repository.updateConversionRate( conversionRate,  convertFrom,  convertTo);
    }
}

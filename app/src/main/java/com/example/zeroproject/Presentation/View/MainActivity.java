package com.example.zeroproject.Presentation.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zeroproject.Presentation.Repository.Room.CurrencyDTO;
import com.example.zeroproject.Presentation.ViewModel.MainActivityViewModel;
import com.example.zeroproject.R;
import com.example.zeroproject.databinding.ActivityMainBinding;

import java.net.InetAddress;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainViewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.spinnerConverting.setPrompt("Choose currency");
        binding.spinnerConverted.setPrompt("Choose currency");
        binding.spinnerConverting.setSelection(0);
        binding.spinnerConverted.setSelection(2);

        mainViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        if (!isNetworkConnected()) {
            setSpinnerFromDatabase();

        }
    }

    public void onConvertClick(View view) {
        Double convertFromDoub = 0.0;
        String currencyFrom = "";
        String currencyTo = "";
        try{
            checkFields(binding.etConverting);
        } catch (IllegalArgumentException e){
            binding.etConverting.setError("Empty field");
            return;
        }
        convertFromDoub = Double.parseDouble(binding.etConverting.getText().toString());
        currencyFrom = binding.spinnerConverting.getSelectedItem().toString();
        currencyTo = binding.spinnerConverted.getSelectedItem().toString();

        Double finalConvertFromDoub = convertFromDoub;
        String finalCurrencyFrom = currencyFrom;
        String finalCurrencyTo = currencyTo;

        if(isNetworkConnected()) {
            mainViewModel.getValue(currencyFrom, currencyTo).observe(this, v -> {
                double converted = finalConvertFromDoub * v.getConversion_rate();
                binding.etConverted.setText(Double.toString(converted));


                mainViewModel.getByPair(finalCurrencyFrom, finalCurrencyTo).observe(this, g ->{
                    if (g.isEmpty() || g.get(0) == null)
                        mainViewModel.insertDTO(finalCurrencyFrom, finalCurrencyTo, v.getConversion_rate());
                    else
                        new Thread(() -> mainViewModel.updateConversionRate(v.getConversion_rate(), finalCurrencyFrom, finalCurrencyTo)).start();
                });

                Toast.makeText(this, "Write to db", Toast.LENGTH_LONG).show();
            });
        } else{
            mainViewModel.getByPair(currencyFrom, currencyTo).observe(this, v -> {
                double converted = finalConvertFromDoub * v.get(0);

                binding.etConverted.setText(Double.toString(converted));
            });
        }
    }

    private void setSpinnerFromDatabase(){
        binding.spinnerConverting.setAdapter(null);
        binding.spinnerConverted.setAdapter(null);

        mainViewModel.getAllConvertFrom().observe(this, v -> {
            ArrayList<String> values = new ArrayList<>();

            for(CurrencyDTO dto : v) {
                if (!values.contains(dto.getConvertFrom()))
                    values.add(dto.getConvertFrom());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>
                    (MainActivity.this, android.R.layout.simple_spinner_item, values);

            binding.spinnerConverting.setAdapter(adapter);
        });

        binding.spinnerConverting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setConvertedSpinnerFromDatabase((String) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setConvertedSpinnerFromDatabase(String currencyFrom){
        mainViewModel.getAllConvertToByConvertFrom(currencyFrom).observe(this, v ->{
            ArrayList<String> values = new ArrayList<>();
            for(CurrencyDTO dto : v) {
                if (!values.contains(dto.getConvertTo()))
                    values.add(dto.getConvertTo());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>
                    (MainActivity.this, android.R.layout.simple_spinner_item, values);

            binding.spinnerConverted.setAdapter(adapter);
            binding.spinnerConverted.setAdapter(adapter);
        });
    }

    private void checkFields(EditText et){
        if(et.getText().toString().isEmpty())
            throw new IllegalArgumentException("Empty field");
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find views
        cityList = findViewById(R.id.city_list);
        Button addBtn = findViewById(R.id.btn_add_city);
        Button deleteBtn = findViewById(R.id.btn_delete_city);

        // starter data
        String[] cities = {"Edmonton", "Montréal", "Vancouver", "Tokyo", "New Delhi", "Calgary", "Toronto"};
        dataList = new ArrayList<>(Arrays.asList(cities));

        // adapter uses row_city.xml
        cityAdapter = new ArrayAdapter<>(this, R.layout.row_city, android.R.id.text1, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // select/highlight on tap
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                cityList.setItemChecked(position, true);
            }
        });

        // add city
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showAddCityDialog();
            }
        });

        // delete selected city
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (selectedPosition >= 0 && selectedPosition < dataList.size()) {
                    dataList.remove(selectedPosition);
                    cityAdapter.notifyDataSetChanged();
                    cityList.clearChoices();
                    selectedPosition = -1;
                } else {
                    Toast.makeText(MainActivity.this, "Tap a city to select it first.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showAddCityDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_city, null);
        final EditText input = dialogView.findViewById(R.id.input_city);

        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Add City")
                .setView(dialogView)
                .create();

        dialogView.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String name = input.getText().toString().trim();
                if (name.isEmpty()) {
                    input.setError("Please enter a city");
                    return;
                }
                dataList.add(name);
                cityAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}




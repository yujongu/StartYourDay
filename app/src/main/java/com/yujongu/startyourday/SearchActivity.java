package com.yujongu.startyourday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button addBtn;
    EditText cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initInstances();
        eventListner();
    }

    private void initInstances(){
        backBtn = findViewById(R.id.btBackArrow);
        addBtn = findViewById(R.id.btAdd);
        cityName = findViewById(R.id.etCity);
    }

    private void eventListner(){
        backBtn.setOnClickListener(listener);
        addBtn.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btBackArrow:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;

                case R.id.btAdd:
                    String city = cityName.getText().toString();

                    Intent resultIntent = getIntent();
                    resultIntent.putExtra("cityName", city);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                    break;
            }
        }
    };


}

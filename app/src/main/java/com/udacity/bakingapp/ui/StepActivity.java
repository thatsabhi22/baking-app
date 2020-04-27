package com.udacity.bakingapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.udacity.bakingapp.R;

public class StepActivity extends AppCompatActivity {

    public static final String STEP_LIST_STATE = "step_list_state";
    public static final String STEP_NUMBER_STATE = "step_number_state";
    public static final String STEP_LIST_JSON_STATE = "step_list_json_state";
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
    }
}

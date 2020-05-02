package com.udacity.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.fragments.VideoPlayerFragment;
import com.udacity.bakingapp.models.Step;

public class StepActivity extends AppCompatActivity {

    public static final String STEP_LIST_STATE = "step_list_state";
    public static final String STEP_NUMBER_STATE = "step_number_state";
    public static final String STEP_LIST_JSON_STATE = "step_list_json_state";
    VideoPlayerFragment videoPlayerFragment;
    FragmentManager fragmentManager;
    Bundle stepsBundle;
    private boolean isTablet;
    private Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        step = intent.getParcelableExtra("step");
        if (step == null) {
            closeOnError();
        }

        String stepDescription = step.getShortDescription();
        setTitle(stepDescription);

        playFirstVideo(step);
    }

    private void playFirstVideo(Step step) {
        videoPlayerFragment = new VideoPlayerFragment();
        stepsBundle = new Bundle();
        stepsBundle.putParcelable("step_single", step);
        videoPlayerFragment.setArguments(stepsBundle);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.player_container, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this,
                "Something went wrong.", Toast.LENGTH_SHORT).show();
    }
}

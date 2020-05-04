package com.udacity.bakingapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.fragments.VideoPlayerFragment;
import com.udacity.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity implements View.OnClickListener {

    VideoPlayerFragment videoPlayerFragment;
    FragmentManager fragmentManager;
    Bundle stepsBundle;
    int display_mode;
    ArrayList<Step> mStepArrayList = new ArrayList<>();

    @BindView(R.id.next_btn)
    Button mNextBtn;
    @BindView(R.id.previous_btn)
    Button mPreviousBtn;

    private Step step;
    private int mVideoNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        display_mode = getResources().getConfiguration().orientation;
        if (display_mode == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide(); //<< this
        }
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        step = intent.getParcelableExtra("step");
        if (step == null) {
            closeOnError();
        }
        mStepArrayList = intent.getParcelableArrayListExtra("allSteps");

        // If there is no saved state, instantiate fragment
        if (savedInstanceState == null) {
            playVideo(mStepArrayList.get(mVideoNumber));
        }

        mNextBtn.setOnClickListener(this);
        mPreviousBtn.setOnClickListener(this);
    }

    private void playVideo(Step step) {
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

    // Initialize fragment
    public void playVideoReplace(Step step) {
        videoPlayerFragment = new VideoPlayerFragment();
        stepsBundle = new Bundle();
        stepsBundle.putParcelable("step_single", step);
        videoPlayerFragment.setArguments(stepsBundle);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.player_container, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this,
                "Something went wrong.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        //If it's last step show cooking is over
        if (mVideoNumber == mStepArrayList.size() - 1) {
            Toast.makeText(this, R.string.steps_over, Toast.LENGTH_SHORT).show();
        } else {
            if (v.getId() == mPreviousBtn.getId()) {
                mVideoNumber--;
                if (mVideoNumber < 0) {
                    Toast.makeText(this,
                            R.string.you_better_see_next_step, Toast.LENGTH_SHORT).show();
                } else
                    playVideoReplace(mStepArrayList.get(mVideoNumber));
            } else if (v.getId() == mNextBtn.getId()) {
                mVideoNumber++;
                playVideoReplace(mStepArrayList.get(mVideoNumber));
            }
        }
    }
}

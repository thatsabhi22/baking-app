package com.udacity.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.adapters.StepsAdapter;
import com.udacity.bakingapp.fragments.VideoPlayerFragment;
import com.udacity.bakingapp.models.Dish;
import com.udacity.bakingapp.models.Ingredient;
import com.udacity.bakingapp.models.Step;
import com.udacity.bakingapp.utils.ConstantsUtil;
import com.udacity.bakingapp.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity implements StepsAdapter.OnStepClickListener {

    @BindView(R.id.ingredient_list_tv)
    TextView ingredientListTV;

    @Nullable
    @BindView(R.id.backdrop_poster_iv)
    ImageView backdropPosterIV;

    @BindView(R.id.steps_recycler_view)
    RecyclerView stepsRecyclerView;

    Dish dish;
    StepsAdapter stepsAdapter;
    VideoPlayerFragment videoPlayerFragment;
    FragmentManager fragmentManager;
    Bundle stepsBundle;
    ArrayList<Step> stepList;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        if (findViewById(R.id.recipe_tablet_layout) != null) {
            isTablet = true;
        } else {
            isTablet = false;
        }

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        dish = intent.getParcelableExtra(ConstantsUtil.SELECTED_DISH_KEY);

        if (dish == null) {
            closeOnError();
        }
        stepList = (ArrayList<Step>) dish.getSteps();
        String dishName = dish.getName();
        setTitle(dishName);

        if (isTablet) {
            playFirstVideo(dish.getSteps().get(0));
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Toast.makeText(this, ConstantsUtil.COOKING_STEP_INFO, Toast.LENGTH_SHORT).show();

            int ImageId = QueryUtils.getImageId(dishName);
            backdropPosterIV.setImageResource(ImageId);
        }

        List<Ingredient> ingredients = dish.getIngredients();

        if (ingredients != null && ingredients.size() > 0) {
            StringBuilder line = new StringBuilder();
            for (Ingredient ingredientObj : ingredients) {
                line.append(ingredientObj.getQuantity());
                line.append(ConstantsUtil.SPACE);
                line.append(ingredientObj.getMeasure());
                line.append(ConstantsUtil.NEWLINE);
                line.append(ingredientObj.getIngredient());
                line.append(ConstantsUtil.DOUBLE_NEWLINE);
            }
            ingredientListTV.setText(line.toString());
        }

        List<Step> steps = dish.getSteps();
        if (steps != null && steps.size() > 0) {
            stepsAdapter = new StepsAdapter(this, steps, this);
            stepsRecyclerView.setAdapter(stepsAdapter);
            RecyclerView.LayoutManager mLayoutManager
                    = new LinearLayoutManager(this);
            stepsRecyclerView.setLayoutManager(mLayoutManager);
            stepsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void playFirstVideo(Step step) {
        videoPlayerFragment = new VideoPlayerFragment();
        stepsBundle = new Bundle();
        stepsBundle.putParcelable(ConstantsUtil.SINGLE_STEP_KEY, step);
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
        stepsBundle.putParcelable(ConstantsUtil.SINGLE_STEP_KEY, step);
        videoPlayerFragment.setArguments(stepsBundle);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.player_container, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStepClick(int position) {
        Step currentStep = stepList.get(position);
        if (isTablet) {
            playVideoReplace(currentStep);
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(ConstantsUtil.COOKING_STEP_NUMBER_KEY, currentStep.getId());
            intent.putParcelableArrayListExtra(ConstantsUtil.ALL_STEP_KEY, stepList);
            this.startActivity(intent);
//            Toast.makeText(this,
//                    "This is the position - " + position, Toast.LENGTH_SHORT).show();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this,
                ConstantsUtil.ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
    }
}

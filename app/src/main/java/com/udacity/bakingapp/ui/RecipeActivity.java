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
import com.udacity.bakingapp.utils.QueryUtils;

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

    StepsAdapter stepsAdapter;
    private boolean isTablet;
    Dish dish;

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

        dish = intent.getParcelableExtra("dish");
        if (dish == null) {
            closeOnError();
        }

        String dishName = dish.getName();
        setTitle(dishName);

        if (isTablet) {
            playFirstVideo(dish.getSteps().get(0));
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Toast.makeText(this, "Steps below", Toast.LENGTH_SHORT).show();

            int ImageId = QueryUtils.getImageId(dishName);
            backdropPosterIV.setImageResource(ImageId);
        }

        List<Ingredient> ingredients = dish.getIngredients();

        if (ingredients != null && ingredients.size() > 0) {
            StringBuilder line = new StringBuilder();
            for (Ingredient ingredientObj : ingredients) {
                line.append(ingredientObj.getQuantity());
                line.append(getString(R.string.space));
                line.append(ingredientObj.getMeasure());
                line.append("\n");
                line.append(ingredientObj.getIngredient());
                line.append(getString(R.string.double_new_line));
            }
            ingredientListTV.setText(line.toString());
        }

        List<Step> steps = dish.getSteps();
        if (steps != null && steps.size() > 0) {
            stepsAdapter = new StepsAdapter(this, steps,this);
            stepsRecyclerView.setAdapter(stepsAdapter);
            RecyclerView.LayoutManager mLayoutManager
                    = new LinearLayoutManager(this);
            stepsRecyclerView.setLayoutManager(mLayoutManager);
            stepsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void playFirstVideo(Step step) {
        VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelable("step_single", step);
        videoPlayerFragment.setArguments(stepsBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.player_container, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    // Initialize fragment
    public void playVideoReplace(Step step){
        VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
        Bundle stepsBundle = new Bundle();
        stepsBundle.putParcelable("step_single", step);
        videoPlayerFragment.setArguments(stepsBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.player_container, videoPlayerFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStepClick(int position) {
        if (isTablet) {
            playVideoReplace(dish.getSteps().get(position));
        }
//        Toast.makeText(this,
//                "This is the position - "+position,Toast.LENGTH_SHORT).show();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this,
                "Something went wrong.", Toast.LENGTH_SHORT).show();
    }
}

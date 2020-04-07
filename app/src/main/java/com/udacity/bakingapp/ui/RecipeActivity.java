package com.udacity.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Dish;
import com.udacity.bakingapp.models.Ingredient;
import com.udacity.bakingapp.utils.QueryUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {

    @BindView(R.id.ingredient_list_tv)
    TextView ingredientListTV;

    @BindView(R.id.backdrop_poster_iv)
    ImageView backdropPosterIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        Toast.makeText(this, "Steps below", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Dish dish = intent.getParcelableExtra("dish");
        if (dish == null) {
            closeOnError();
        }

        String dishName = dish.getName();
        setTitle(dishName);
        int ImageId = QueryUtils.getImageId(dishName);
        backdropPosterIV.setImageResource(ImageId);

        List<Ingredient> ingredients = dish.getIngredients();

        if (ingredients != null && ingredients.size() > 0) {
            StringBuilder line = new StringBuilder();
            for (Ingredient ingredientObj : ingredients) {
                line.append(ingredientObj.getQuantity());
                line.append(getString(R.string.space));
                line.append(ingredientObj.getMeasure());
                line.append(getString(R.string.space));
                line.append(getString(R.string.space));
                line.append(getString(R.string.space));
                line.append(ingredientObj.getIngredient());
                line.append(getString(R.string.double_new_line));
            }
            ingredientListTV.setText(line.toString());
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this,
                "Something went wrong.", Toast.LENGTH_SHORT).show();
    }
}
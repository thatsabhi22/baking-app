package com.udacity.bakingapp.ui;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.adapters.DishesAdapter;
import com.udacity.bakingapp.models.Dish;
import com.udacity.bakingapp.utils.QueryUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishesActivity extends AppCompatActivity {

    @BindView(R.id.dish_list_recycler_view)
    RecyclerView dishListRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    DishesAdapter dishesAdapter;
    List<Dish> dishesList;

    RecyclerView.LayoutManager mLayoutManager;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);
        ButterKnife.bind(this);

        isTablet = (findViewById(R.id.dishes_phone_land) != null) || (findViewById(R.id.dishes_tablet) != null);

        AssetManager assetManager = getAssets();
        String dishesJSON = QueryUtils.mReadJsonData(assetManager, getString(R.string.json_pile_path));

        if (!TextUtils.isEmpty(dishesJSON)) {
            dishesList = QueryUtils.extractMoviesFromJson(dishesJSON);
        }

        dishesAdapter = new DishesAdapter(this, dishesList);
        dishListRecyclerView.setAdapter(dishesAdapter);

        if (isTablet) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new LinearLayoutManager(this);
        }
        dishListRecyclerView.setLayoutManager(mLayoutManager);
        dishListRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }
}

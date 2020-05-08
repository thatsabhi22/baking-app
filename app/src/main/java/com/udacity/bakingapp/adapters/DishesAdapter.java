package com.udacity.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Dish;
import com.udacity.bakingapp.ui.RecipeActivity;
import com.udacity.bakingapp.utils.QueryUtils;
import com.udacity.bakingapp.widget.BakingAppWidgetService;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class DishesAdapter extends
        RecyclerView.Adapter<DishesAdapter.DishListViewHolder> {

    private final LayoutInflater inflater;
    private final Context mContext;
    private List<Dish> dishes = Collections.emptyList();
    private String mJsonResult;

    public DishesAdapter(Context mContext, List<Dish> dishes, String jsonResult) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.dishes = dishes;
        this.mJsonResult = jsonResult;
    }

    @NonNull
    @Override
    public DishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.dishes_list_card, parent, false);
        return new DishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishListViewHolder holder, int position) {
        final Dish current = dishes.get(position);

        int imageRes = QueryUtils.getImageId(current.getName());

        holder.singleDishIV.setImageResource(imageRes);
        holder.dishNameTV.setText(current.getName());

        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, RecipeActivity.class);
                        intent.putExtra("dish", current);
                        mContext.startActivity(intent);

                        String currentJson = jsonToString(mJsonResult, holder.getAdapterPosition());
                        SharedPreferences.Editor editor = mContext
                                .getSharedPreferences("bakingapp_shared_pref", MODE_PRIVATE).edit();
                        editor.putString("json_result_extra", currentJson);
                        editor.apply();

                        if (Build.VERSION.SDK_INT > 25) {
                            //Start the widget service to update the widget
                            BakingAppWidgetService.startActionOpenRecipeO(mContext);
                        } else {
                            //Start the widget service to update the widget
                            BakingAppWidgetService.startActionOpenRecipe(mContext);
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dishes == null ? 0 : dishes.size();
    }

    // Get selected Recipe as Json String
    private String jsonToString(String jsonResult, int position) {
        JsonElement jsonElement = new JsonParser().parse(jsonResult);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonElement recipeElement = jsonArray.get(position);
        return recipeElement.toString();
    }

    public class DishListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dish_image_view)
        ImageView singleDishIV;

        @BindView(R.id.dish_name_tv)
        TextView dishNameTV;

        public DishListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

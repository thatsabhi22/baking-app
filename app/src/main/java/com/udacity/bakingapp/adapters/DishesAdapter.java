package com.udacity.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Dish;
import com.udacity.bakingapp.ui.DishesActivity;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DishesAdapter extends
        RecyclerView.Adapter<DishesAdapter.DishListViewHolder> {

    private final LayoutInflater inflater;
    private final Context mContext;
    private List<Dish> dishes = Collections.emptyList();

    public DishesAdapter(Context mContext, List<Dish> dishes) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public DishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.dishes_list_cards, parent, false);
        return new DishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishListViewHolder holder, int position) {
        final Dish current = dishes.get(position);

        int imageRes = 0;

        switch (current.getName()) {
            case "Nutella Pie":
                imageRes = R.drawable.nutella_pie;
                break;

            case "Brownies":
                imageRes = R.drawable.brownie;
                break;

            case "Yellow Cake":
                imageRes = R.drawable.yellow_cake;
                break;

            case "Cheesecake":
                imageRes = R.drawable.cheese_cake;
                break;

            default:
                imageRes = R.drawable.dish_image;
        }

        holder.singleDishIV.setImageResource(imageRes);
        holder.dishNameTV.setText(current.getName());

        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, DishesActivity.class);
//                        intent.putExtra("movie", current);
                        mContext.startActivity(intent);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dishes == null ? 0 : dishes.size();
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

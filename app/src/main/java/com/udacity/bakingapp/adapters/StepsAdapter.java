package com.udacity.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Step;
import com.udacity.bakingapp.ui.StepActivity;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends
        RecyclerView.Adapter<StepsAdapter.StepsListViewHolder> {

    private final LayoutInflater inflater;
    private final Context mContext;
    private List<Step> steps = Collections.emptyList();

    public StepsAdapter(Context mContext, List<Step> steps) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.steps = steps;
    }

    @NonNull
    @Override
    public StepsAdapter.StepsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) inflater.inflate(R.layout.steps_list_card, parent, false);
        return new StepsAdapter.StepsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.StepsListViewHolder holder, int position) {
        final Step current = steps.get(position);
        holder.stepNameTV.setText(current.getShortDescription());

        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, StepActivity.class);
                        intent.putExtra("step", current);
                        mContext.startActivity(intent);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }

    public class StepsListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_name_tv)
        TextView stepNameTV;

        public StepsListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

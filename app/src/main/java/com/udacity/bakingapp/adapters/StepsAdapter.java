package com.udacity.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Step;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Steps Adapter implementation for the StepActivity RecyclerView
 */
public class StepsAdapter extends
        RecyclerView.Adapter<StepsAdapter.StepsListViewHolder> {

    private final LayoutInflater inflater;
    private final Context mContext;
    private List<Step> steps = Collections.emptyList();
    private OnStepClickListener onStepClickListener;

    public StepsAdapter(Context mContext, List<Step> steps, OnStepClickListener onStepClickListener) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.steps = steps;
        this.onStepClickListener = onStepClickListener;
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
                        onStepClickListener.onStepClick(position);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }

    public interface OnStepClickListener {
        void onStepClick(int position);
    }

    // Defining the ViewHolder
    public class StepsListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_name_tv)
        TextView stepNameTV;

        public StepsListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

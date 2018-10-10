package com.softcap.artrosario.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softcap.artrosario.bakingapp.R;
import com.softcap.artrosario.bakingapp.model.Recipe;
import com.softcap.artrosario.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private final List<Step> stepList;
    private final int rowLayout;
    private final Context mContext;
    private final StepClickHandler mStepHandler;


    public StepsAdapter(List<Step> stepList, int rowLayout, Context mContext, StepClickHandler mStepHandler) {
        this.stepList = stepList;
        this.rowLayout = rowLayout;
        this.mContext = mContext;
        this.mStepHandler = mStepHandler;
    }
    public interface StepClickHandler {
        void onStepClick(View view, Step step);
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(rowLayout, parent, false);

        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        String stepShortDescription = stepList.get(position).stepShortDescription;
        holder.stepShortDesc.setText(stepShortDescription);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }


    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView stepShortDesc;
        public StepsViewHolder(View itemView) {
            super(itemView);
            stepShortDesc = itemView.findViewById(R.id.tv_step_short_desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Step thisStep = stepList.get(adapterPosition);
            mStepHandler.onStepClick(v, thisStep);

        }
    }
}

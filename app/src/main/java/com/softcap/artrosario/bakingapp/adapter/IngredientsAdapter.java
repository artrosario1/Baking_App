package com.softcap.artrosario.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.softcap.artrosario.bakingapp.R;
import com.softcap.artrosario.bakingapp.model.Ingredient;
import com.softcap.artrosario.bakingapp.model.Recipe;

import org.w3c.dom.Text;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private final List<Ingredient> ingredientList;
    private final Context mContext;
    private final int rowLayout;

    public IngredientsAdapter(List<Ingredient> ingredientList, int rowLayout, Context mContext){
        this.ingredientList = ingredientList;
        this.mContext = mContext;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(rowLayout, parent , false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        String ingredientName = ingredientList.get(position).ingredientName;
        String ingredientQuantity = ingredientList.get(position).quantity;
        String ingredientMeasure = ingredientList.get(position).measure;
        holder.ingredientName.setText(ingredientName);
        holder.ingredientQuantity.setText(ingredientQuantity);
        holder.ingredientMeasure.setText(ingredientMeasure);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder{
        final TextView ingredientName;
        final TextView ingredientQuantity;
        final TextView ingredientMeasure;


        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.tv_ingredientName);
            ingredientQuantity = itemView.findViewById(R.id.tv_ingredientQuanity);
            ingredientMeasure = itemView.findViewById(R.id.tv_ingredientMeasure);

        }
    }
}

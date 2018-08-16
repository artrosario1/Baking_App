package com.softcap.artrosario.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softcap.artrosario.bakingapp.R;
import com.softcap.artrosario.bakingapp.activity.IngredientsActivity;
import com.softcap.artrosario.bakingapp.model.Recipe;

import java.util.List;


public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeViewHolder> {

    private final List<Recipe> recipes;
    int[] recipeImages = {R.drawable.nutella_pie, R.drawable.brownie, R.drawable.yellow_cake, R.drawable.cheesecake};
    private final int rowLayout;
    private final Context mContext;
    private static final String URL_BASE_PATH = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private final RecipeCardClickHandler mClickHandler;


    public RecipeCardAdapter(List<Recipe> recipes, int rowLayout, Context mContext, RecipeCardClickHandler mClickHandler) {
        this.recipes = recipes;
        this.rowLayout = rowLayout;
        this.mContext = mContext;
        this.mClickHandler = mClickHandler;
    }

    public interface RecipeCardClickHandler {
        void onItemClick(View view, Recipe recipe);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(rowLayout, parent , false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        String thisRecipeName = recipes.get(position).getRecipeName();
        Log.d("NAME",thisRecipeName);
        holder.recipeName.setText(thisRecipeName);
        holder.recipeImage.setImageResource(recipeImages[position]);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView recipeName;
        final ImageView recipeImage;

        RecipeViewHolder(View view){
            super(view);
            recipeName = view.findViewById(R.id.tv_recipeName);
            recipeImage = view.findViewById(R.id.iv_recipeImage);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe thisRecipe = recipes.get(adapterPosition);
            mClickHandler.onItemClick(v, thisRecipe);
        }
    }
}

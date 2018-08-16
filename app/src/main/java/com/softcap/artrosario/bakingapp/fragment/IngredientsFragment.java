package com.softcap.artrosario.bakingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softcap.artrosario.bakingapp.adapter.IngredientsAdapter;
import com.softcap.artrosario.bakingapp.model.Ingredient;
import com.softcap.artrosario.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import com.softcap.artrosario.bakingapp.R;



public class IngredientsFragment extends Fragment {

    private Recipe thisRecipe;
    private List<Ingredient> ingredients = new ArrayList<>();
    private IngredientsAdapter ingredientsAdapter;

    public IngredientsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        thisRecipe = getActivity().getIntent().getParcelableExtra("ThisRecipe");
        ingredients = thisRecipe.getIngredientList();
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        RecyclerView ingredientsRV = rootView.findViewById(R.id.rv_fragment_ingredients);
        ingredientsAdapter = new IngredientsAdapter(ingredients,R.layout.layout_ingredients, getContext());
        ingredientsRV.setLayoutManager(linearLayoutManager);
        ingredientsRV.setAdapter(ingredientsAdapter);
        return rootView;
    }
}

package com.softcap.artrosario.bakingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.softcap.artrosario.bakingapp.R;
import com.softcap.artrosario.bakingapp.activity.IngredientsActivity;
import com.softcap.artrosario.bakingapp.activity.VideoStepsActivity;
import com.softcap.artrosario.bakingapp.adapter.RecipeCardAdapter;
import com.softcap.artrosario.bakingapp.model.Recipe;
import com.softcap.artrosario.bakingapp.rest.RecipeJson;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeCardFragment extends Fragment implements RecipeCardAdapter.RecipeCardClickHandler {

   private ArrayList<Recipe> recipeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private boolean mTwoPane;
    private ProgressBar progressBar;
    private RecipeCardAdapter.RecipeCardClickHandler mRecipeHandler;
    private static final String URL_BASE_PATH = "https://d17h27t6h515a5.cloudfront.net";

    private static Retrofit retrofit;
    Context mContext;

    public RecipeCardFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_card, container, false);
        recyclerView = rootView.findViewById(R.id.rv_fragment_recipe_card);
        progressBar = getActivity().findViewById(R.id.progressBar);
        mRecipeHandler = this;

        if(savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList("RecipeList");
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(recipeList, R.layout.layout_recipe_card, getContext(),  mRecipeHandler);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(recipeCardAdapter);
        }else{
            connectAndGetRecipes();
        }

        return rootView;
    }

    private void connectAndGetRecipes() {
        if(retrofit == null){
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
        }

        RecipeJson recipeJson = retrofit.create(RecipeJson.class);

        Call<ArrayList<Recipe>> call = recipeJson.getRecipes();
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                recipeList = response.body();
                if(recipeList != null){
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(recipeList, R.layout.layout_recipe_card, getContext(),  mRecipeHandler);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(recipeCardAdapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                showErrorMessage();
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("RecipeList", recipeList);
        Log.d("list", recipeList.toString());
        super.onSaveInstanceState(outState);
    }

    private void showErrorMessage() {
    }

    @Override
    public void onItemClick(View view, Recipe recipe) {
        Context context = getContext();

            Intent intent = new Intent(context, IngredientsActivity.class);
            intent.putExtra("ThisRecipe", recipe);
            startActivity(intent);
    }


}

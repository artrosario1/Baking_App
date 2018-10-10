package com.softcap.artrosario.bakingapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.softcap.artrosario.bakingapp.R;
import com.softcap.artrosario.bakingapp.adapter.RecipeCardAdapter;
import com.softcap.artrosario.bakingapp.model.Ingredient;
import com.softcap.artrosario.bakingapp.model.Recipe;
import com.softcap.artrosario.bakingapp.rest.RecipeJson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeCardAdapter.RecipeCardClickHandler {

    private static final String RECIPES_KEY = "Recipes";
    public static final String BUNDLE = "Bundle";
    public static final String INGREDIENTS = "Ingredients";
    private Recipe[] recipesArray;
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private boolean mTwoPane;
    private ProgressBar progressBar;
    private RecipeCardAdapter.RecipeCardClickHandler mRecipeHandler;
    private static final String URL_BASE_PATH = "https://d17h27t6h515a5.cloudfront.net";
    private static Retrofit retrofit;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressBar);
        mRecipeHandler = this;

        if(savedInstanceState == null || !savedInstanceState.containsKey("RecipeList")) {
            connectAndGetRecipes();
        }else{
            recipeList = savedInstanceState.getParcelableArrayList("RecipeList");
            recyclerView.setAdapter(new RecipeCardAdapter(recipeList, R.layout.layout_recipe_card, getApplicationContext(),  mRecipeHandler));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("RecipeList", recipeList);
        super.onSaveInstanceState(outState);
    }

    private void connectAndGetRecipes() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
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
                    RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(recipeList, R.layout.layout_recipe_card, getApplicationContext(),  mRecipeHandler);
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
    private void showErrorMessage() {
    }
    @Override
    public void onItemClick(View view, Recipe recipe) {
        Context context = getApplicationContext();

        Intent intent = goToActivity(context, recipe);
        startActivity(intent);
    }

    public static Intent goToActivity(Context context, Recipe recipe) {
        Intent intent = new Intent(context, IngredientsActivity.class);
        intent.putExtra("ThisRecipe", recipe);

        return intent;
    }

}

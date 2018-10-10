package com.softcap.artrosario.bakingapp.rest;

import com.softcap.artrosario.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeJson {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getRecipes();

}

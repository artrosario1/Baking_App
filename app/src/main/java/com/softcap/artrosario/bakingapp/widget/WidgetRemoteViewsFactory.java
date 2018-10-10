package com.softcap.artrosario.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.softcap.artrosario.bakingapp.adapter.RecipeCardAdapter;
import com.softcap.artrosario.bakingapp.model.Ingredient;
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


public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context context;
    Recipe recipe;

    public WidgetRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String recipeString = preferences.getString("RecipeWidgetKey", "");
        recipe = Recipe.convertBack(recipeString);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return recipe.getIngredientList().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
        remoteView.setTextViewText(android.R.id.text1, recipe.getIngredientList().get(position).getIngredientName());
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

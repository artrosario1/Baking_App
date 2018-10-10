package com.softcap.artrosario.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViewsService;

import com.softcap.artrosario.bakingapp.model.Recipe;

public class listViewsService extends RemoteViewsService
{
    public static void updateWidget(Context context, Recipe recipe) {
        SharedPreferences.Editor preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE).edit();
        preferences.putString("RecipeWidgetKey", recipe.convert());
        preferences.apply();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientsWidgetProvider.class));
        IngredientsWidgetProvider.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return new WidgetRemoteViewsFactory(getApplicationContext());
    }
}

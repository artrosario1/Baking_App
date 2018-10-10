package com.softcap.artrosario.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.softcap.artrosario.bakingapp.R;
import com.softcap.artrosario.bakingapp.activity.MainActivity;
import com.softcap.artrosario.bakingapp.model.Ingredient;
import com.softcap.artrosario.bakingapp.model.Recipe;

public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String recipeString = preferences.getString("RecipeWidgetKey", "");
        Recipe recipe = Recipe.convertBack(recipeString);
        if(recipe!= null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    MainActivity.goToActivity(context, recipe),
                    PendingIntent.FLAG_CANCEL_CURRENT);


            Intent intent = new Intent(context, listViewsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
            views.setTextViewText(R.id.tv_recipe_widget, recipe.getRecipeName());
            views.setOnClickPendingIntent(R.id.tv_recipe_widget, pendingIntent);
            views.setRemoteAdapter(R.id.widget_list, intent);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }


    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_list,
                new Intent(context, listViewsService.class));
    }
}


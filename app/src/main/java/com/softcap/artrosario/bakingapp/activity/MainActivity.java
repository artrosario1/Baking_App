package com.softcap.artrosario.bakingapp.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.softcap.artrosario.bakingapp.R;
import com.softcap.artrosario.bakingapp.fragment.RecipeCardFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {

            RecipeCardFragment cardFragment = new RecipeCardFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.recipeCardContainer, cardFragment)
                    .commit();
        }
    }

}

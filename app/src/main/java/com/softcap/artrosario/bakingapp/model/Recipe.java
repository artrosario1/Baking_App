package com.softcap.artrosario.bakingapp.model;

import android.graphics.Movie;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Recipe createFromParcel(Parcel source) { return new Recipe(source); }

        @Override
        public Recipe[] newArray(int size) { return new Recipe[size]; }
    };

    @SerializedName("id")
    @Expose
    public String recipeId;
    @SerializedName("name")
    @Expose
    public String recipeName;
    @SerializedName("ingredients")
    @Expose
    public ArrayList<Ingredient> ingredientList;
    @SerializedName("steps")
    @Expose
    public ArrayList<Step> stepList;
    @SerializedName("servings")
    @Expose
    public String servings;
    @SerializedName("image")
    @Expose
    public String recipeImage;

    public Recipe(String recipeId, String recipeName, ArrayList<Ingredient> ingredientList, ArrayList<Step> stepList, String servings, String recipeImage) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.ingredientList = ingredientList;
        this.stepList = stepList;
        this.servings = servings;
        this.recipeImage = recipeImage;
    }

    public Recipe(Parcel in){
        this.recipeId = in.readString();
        this.recipeName = in.readString();
        this.ingredientList = new ArrayList<Ingredient>();
        in.readList(this.ingredientList, Ingredient.class.getClassLoader());
        this.stepList = new ArrayList<Step>();
        in.readList(this.stepList, Step.class.getClassLoader());
        this.servings = in.readString();
        this.recipeImage = in.readString();
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }


    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName () {
        return recipeName ;
    }

    public void setRecipeName (String recipeName ) {
        this.recipeName  = recipeName ;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public ArrayList<Step> getStepList() {
        return stepList;
    }

    public void setStepList(ArrayList<Step> stepList) {
        this.stepList = stepList;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recipeId);
        dest.writeString(this.recipeName);
        dest.writeList(this.ingredientList);
        dest.writeList(this.stepList);
        dest.writeString(this.servings);
        dest.writeString(this.recipeImage);

    }
}


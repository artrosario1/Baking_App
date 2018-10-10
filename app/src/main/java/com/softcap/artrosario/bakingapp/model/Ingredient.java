package com.softcap.artrosario.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Ingredient implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Ingredient createFromParcel(Parcel source) { return new Ingredient(source); }

        @Override
        public Ingredient[] newArray(int size) { return new Ingredient[size]; }
    };

    @SerializedName("quantity")
    public String quantity;
    @SerializedName("measure")
    public String measure;
    @SerializedName("ingredient")
    public String ingredientName;
    public String mIngredient;

    public Ingredient(String quantity, String measure, String ingredientName) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientName = ingredientName;
    }

    public Ingredient(Parcel in){
        this.quantity = in.readString();
        this.measure = in.readString();
        this.ingredientName = in.readString();
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredient(){
        return mIngredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredientName);

    }
}

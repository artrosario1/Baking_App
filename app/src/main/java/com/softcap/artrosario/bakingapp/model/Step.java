package com.softcap.artrosario.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Step createFromParcel(Parcel source) { return new Step(source); }

        @Override
        public Step[] newArray(int size) { return new Step[size]; }
    };
    @SerializedName("id")
    public String stepId;
    @SerializedName("shortDescription")
    public String stepShortDescription;
    @SerializedName("description")
    public String stepDescription;
    @SerializedName("videoURL")
    public String videoURL;
    @SerializedName("thumbnailURL")
    public String thumbnailURL;

    public Step(String stepId, String stepShortDescription, String stepDescription, String videoURL, String thumbnailURL) {
        this.stepId = stepId;
        this.stepShortDescription = stepShortDescription;
        this.stepDescription = stepDescription;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }
    public Step(Parcel in){
        this.stepId = in.readString();
        this.stepShortDescription = in.readString();
        this.stepDescription = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getStepShortDescription() {
        return stepShortDescription;
    }

    public void setStepShortDescription(String stepShortDescription) {
        this.stepShortDescription = stepShortDescription;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stepId);
        dest.writeString(this.stepShortDescription);
        dest.writeString(this.stepDescription);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);

    }
}

package com.mgUnicorn.eh.models;

public class storageModel {

    String imageUrl,name;

    storageModel(){


    }

    public storageModel(String imageUrl,String mName) {
        this.imageUrl = imageUrl;
        this.name=mName;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

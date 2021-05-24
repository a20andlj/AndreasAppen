package com.example.andreasappen;

import com.google.gson.annotations.SerializedName;

public class Books {

    private String ID;
    private String name;
    private String type;
    private String company;
    private String location;
    private String category;;
    private Integer size;
    private Integer cost;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Integer getSize() {
        return size;
    }

    public String getCategory() {
        return category;
    }

    public Integer getCost() {
        return cost;
    }

    @Override
    public String toString() { return name; }


}

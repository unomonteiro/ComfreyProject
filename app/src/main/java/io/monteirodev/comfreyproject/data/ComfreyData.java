package io.monteirodev.comfreyproject.data;

import java.util.ArrayList;

public class ComfreyData {

    private ArrayList<Plant> plants;
    private ArrayList<Recipe> recipes;
    private GetInvolved getInvolved;
    private About about;

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public GetInvolved getGetInvolved() {
        return getInvolved;
    }

    public About getAbout() {
        return about;
    }
}

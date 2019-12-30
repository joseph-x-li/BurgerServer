package com.example.burger.burgerserver;

import java.util.ArrayList;
import java.util.List;


public class GameFunction
{
    //stores stack of ingredients on the UI game board
    public static List<Integer> ingredients = new ArrayList<>();
    //stores colors for the cheat Button
    public static List<Integer> colorWheel = new ArrayList<Integer>();
    //places random ingredient into virtual board
    public static void dropIngredient()
    {
        int next = (int)(Math.random()*4);
        ingredients.add(next);
    }
    //removes ingredient from bottom of virtual board
    public static void removeIngredient()
    {
        ingredients.remove(0);
    }
    //removes all ingredients from the virtual board
    public static void eraseIngredients()
    {
        ingredients.clear();
    }
    //initializes color wheel with colors
    public static void initializeColorWheel()
    {
        colorWheel.add(0xffE57373);
        colorWheel.add(0xffEC407A);
        colorWheel.add(0xffAB47BC);
        colorWheel.add(0xff5E35B1);
        colorWheel.add(0xff303F9F);
        colorWheel.add(0xff1565C0);
        colorWheel.add(0xff00ACC1);
        colorWheel.add(0xff00695C);
        colorWheel.add(0xff2E7D32);
        colorWheel.add(0xff9E9D24);
        colorWheel.add(0xffFF8F00);
    }
    //returns next color in colorWHeel
    public static int getNextColor()
    {
        //if not initialized, initialize
        if(colorWheel.size()<=0){
            initializeColorWheel();
        }
        //cycle through the ArrayList
        colorWheel.add(colorWheel.remove(0));
        //return the first color
        return colorWheel.get(0);
    }
}

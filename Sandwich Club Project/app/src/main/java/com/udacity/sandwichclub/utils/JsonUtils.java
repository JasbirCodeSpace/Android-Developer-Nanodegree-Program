package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getName();
    public static Sandwich parseSandwichJson(String json) {
        final String KEY_NAME = "name";
        final String KEY_MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
        final String KEY_DESCRIPTION = "description";
        final String KEY_IMAGE = "image";
        final String KEY_INGREDIENTS = "ingredients";

        String sandwichName,sandwichOrigin,sandwichDescription,sandwichImage;
        List<String> sandwichAlsoKnown,sandwichIngredients;
        try{
            JSONObject sandwichObject = new JSONObject(json);
            sandwichName = sandwichObject.getJSONObject(KEY_NAME).getString(KEY_MAIN_NAME);

            sandwichOrigin = sandwichObject.getString(KEY_PLACE_OF_ORIGIN);
            sandwichDescription = sandwichObject.getString(KEY_DESCRIPTION);
            sandwichAlsoKnown = jsonArrayToList(sandwichObject.getJSONArray(ALSO_KNOWN_AS));
            sandwichIngredients = jsonArrayToList(sandwichObject.getJSONArray(KEY_INGREDIENTS));
            sandwichImage = sandwichObject.getString(KEY_IMAGE);
            return new Sandwich(sandwichName,sandwichAlsoKnown,sandwichOrigin,sandwichDescription,sandwichImage,sandwichIngredients);
        }catch (Exception e){
            e.printStackTrace();
            Log.v(TAG,"Error while reading json data from strings.xml");
        }

    return null;
    }

    public static List<String> jsonArrayToList(JSONArray array){
        List<String> list = new ArrayList<>();
        try {
            for (int i = 0; i <array.length(); i++) {
                list.add(array.getString(i));
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }

        return list;
    }
}

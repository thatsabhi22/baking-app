package com.udacity.bakingapp.utils;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.models.Dish;
import com.udacity.bakingapp.models.Ingredient;
import com.udacity.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving movies data from Movies DB.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return an {@link Dish} object by parsing out information
     * about the first movie from the input dishJSON string.
     */
    public static ArrayList<Dish> extractMoviesFromJson(String dishJSON) {
        ArrayList<Dish> dishes = new ArrayList<>();

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(dishJSON)) {
            return null;
        }

        try {
            JSONArray baseJsonResponseArray = new JSONArray(dishJSON);

            if (baseJsonResponseArray.length() > 0) {
                for (int i = 0; i < baseJsonResponseArray.length(); i++) {
                    JSONObject dishObj = baseJsonResponseArray.getJSONObject(i);

                    JSONArray ingredientArray = dishObj.optJSONArray("ingredients");
                    List<Ingredient> ingList = new ArrayList<>();
                    if (ingredientArray != null && ingredientArray.length() > 0) {
                        for (int j = 0; j < ingredientArray.length(); j++) {
                            JSONObject ingredientObj = ingredientArray.getJSONObject(j);
                            Ingredient ingredient = new Ingredient();
                            ingredient.setIngredient(ingredientObj.optString("ingredient"));
                            ingredient.setQuantity(ingredientObj.optInt("quantity", 0));
                            ingredient.setMeasure(ingredientObj.optString("measure"));
                            ingList.add(ingredient);
                        }
                    }

                    JSONArray stepsArray = dishObj.optJSONArray("steps");
                    List<Step> stepsList = new ArrayList<>();
                    if (stepsArray != null && stepsArray.length() > 0) {
                        for (int j = 0; j < stepsArray.length(); j++) {
                            JSONObject stepObj = stepsArray.getJSONObject(j);
                            Step step = new Step();
                            step.setId(stepObj.optInt("id", 0));
                            step.setShortDescription(stepObj.optString("shortDescription"));
                            step.setDescription(stepObj.optString("description"));
                            step.setVideoURL(stepObj.optString("videoURL"));
                            step.setThumbnailURL(stepObj.optString("thumbnailURL"));
                            stepsList.add(step);
                        }
                    }

                    Dish dish = new Dish();
                    dish.setId(dishObj.optInt("id"));
                    dish.setName(dishObj.optString("name"));
                    dish.setImage(dishObj.optString("image"));
                    dish.setIngredients(ingList);
                    dish.setSteps(stepsList);
                    dish.setServings(dishObj.optInt("servings"));

                    dishes.add(dish);
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the movies JSON results", e);
        }
        return dishes;
    }

    /**
     * No Network Condition handled based on link below
     * https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
     */

    public static boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return false;
        } catch (IOException e) {
            return true;
        }
    }

    public static String mReadJsonData(AssetManager am, String filePath) {
        String text = "";
        try {
            InputStream is = am.open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

    public static int getImageId(String dishName) {
        int imageId = 0;
        if (dishName != null && !TextUtils.isEmpty(dishName)) {

            switch (dishName) {
                case "Nutella Pie":
                    imageId = R.drawable.nutella_pie;
                    break;

                case "Brownies":
                    imageId = R.drawable.brownie;
                    break;

                case "Yellow Cake":
                    imageId = R.drawable.yellow_cake;
                    break;

                case "Cheesecake":
                    imageId = R.drawable.cheese_cake;
                    break;

                default:
                    imageId = R.drawable.dish_image;
            }
        }
        return imageId;
    }

    public static class CheckOnlineStatus extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            //This is a background thread, when it finishes executing will return the result from your function.
            Boolean isOffline;
            isOffline = isOnline();
            return isOffline;
        }
    }
}
package com.udacity.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.udacity.bakingapp.models.Ingredient;
import com.udacity.bakingapp.utils.QueryUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BakingAppWidgetService extends IntentService {

    public static final String ACTION_OPEN_RECIPE =
            "com.udacity.bakingapp.widget.bakingapp_widget_service";


    public BakingAppWidgetService(String name) {
        super(name);
    }

    public BakingAppWidgetService() {
        super("BakingAppWidgetService");
    }

    // Trigger the service to perform the action
    public static void startActionOpenRecipe(Context context) {
        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(ACTION_OPEN_RECIPE);
        context.startService(intent);
    }

    // For Android O and above
    public static void startActionOpenRecipeO(Context context) {
        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(ACTION_OPEN_RECIPE);
        ContextCompat.startForegroundService(context, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@NonNull Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_OPEN_RECIPE.equals(action)) {
                handleActionOpenRecipe();
            }
        }
    }

    private void handleActionOpenRecipe() {
        SharedPreferences sharedpreferences =
                getSharedPreferences("bakingapp_shared_pref", MODE_PRIVATE);
        String jsonDish = sharedpreferences.getString("json_result_extra", "");
        StringBuilder stringBuilder = new StringBuilder();
        String dishTitle = "";
        int imgResId = 0;

        try {
            JSONObject baseDishJsonResponseObject = new JSONObject(jsonDish);
            dishTitle = baseDishJsonResponseObject.optString("name");
            imgResId = QueryUtils.getImageId(dishTitle);
            List<Ingredient> ingredientList = QueryUtils.getIngredients(baseDishJsonResponseObject);
            for (Ingredient ingredient : ingredientList) {
                String quantity = String.valueOf(ingredient.getQuantity());
                String measure = ingredient.getMeasure();
                String ingredientName = ingredient.getIngredient();
                String line = quantity + " " + measure + " " + ingredientName;
                stringBuilder.append(line + "\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String ingredientsString = stringBuilder.toString();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        BakingAppWidgetProvider.updateWidgetRecipe(this, ingredientsString, imgResId, dishTitle, appWidgetManager, appWidgetIds);
    }
}

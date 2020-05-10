package com.udacity.bakingapp.widget;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.udacity.bakingapp.models.Ingredient;
import com.udacity.bakingapp.utils.ConstantsUtil;
import com.udacity.bakingapp.utils.QueryUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BakingAppWidgetService extends IntentService {

    public BakingAppWidgetService(String name) {
        super(name);
    }

    public BakingAppWidgetService() {
        super(ConstantsUtil.BAKINGAPP_WIDGET_SERVICE);
    }

    // Trigger the service to perform the action
    public static void startActionOpenRecipe(Context context) {
        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(ConstantsUtil.ACTION_OPEN_RECIPE);
        context.startService(intent);
    }

    // For Android O and above
    public static void startActionOpenRecipeO(Context context) {
        Intent intent = new Intent(context, BakingAppWidgetService.class);
        intent.setAction(ConstantsUtil.ACTION_OPEN_RECIPE);
        ContextCompat.startForegroundService(context, intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(ConstantsUtil.CHANNEL_ID,
                    ConstantsUtil.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
                    .createNotificationChannel(channel);

            Notification notification
                    = new NotificationCompat.Builder(this, ConstantsUtil.CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }

    @Override
    protected void onHandleIntent(@NonNull Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ConstantsUtil.ACTION_OPEN_RECIPE.equals(action)) {
                handleActionOpenRecipe();
            }
        }
    }

    private void handleActionOpenRecipe() {
        SharedPreferences sharedpreferences =
                getSharedPreferences(ConstantsUtil.BAKINGAPP_SHARED_PREFERENCES, MODE_PRIVATE);
        String jsonDish = sharedpreferences.getString(ConstantsUtil.CURRENT_DISH_JSON, "");
        StringBuilder stringBuilder = new StringBuilder();
        String dishTitle = "";
        int imgResId = 0;

        try {
            JSONObject baseDishJsonResponseObject = new JSONObject(jsonDish);
            dishTitle = baseDishJsonResponseObject.optString(ConstantsUtil.NAME_KEY);
            imgResId = QueryUtils.getImageId(dishTitle);
            List<Ingredient> ingredientList = QueryUtils.getIngredients(baseDishJsonResponseObject);
            for (Ingredient ingredient : ingredientList) {
                String quantity = String.valueOf(ingredient.getQuantity());
                String measure = ingredient.getMeasure();
                String ingredientName = ingredient.getIngredient();
                stringBuilder.append(quantity)
                        .append(ConstantsUtil.SPACE)
                        .append(measure)
                        .append(ConstantsUtil.SPACE)
                        .append(ingredientName)
                        .append(ConstantsUtil.NEWLINE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String ingredientsString = stringBuilder.toString();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, BakingAppWidgetProvider.class));
        BakingAppWidgetProvider.updateWidgetRecipe(
                this, ingredientsString, imgResId, dishTitle, appWidgetManager, appWidgetIds);
    }
}

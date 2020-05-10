package com.udacity.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.udacity.bakingapp.ui.DishesActivity;
import com.udacity.bakingapp.utils.ConstantsUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class IntentTesting {

    @Rule
    public IntentsTestRule<DishesActivity> mActivityRule = new IntentsTestRule<>(
            DishesActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal()))
                .respondWith(new Instrumentation
                        .ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void intentTest() {

        // Let the UI load completely first
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Recyclerview scroll to position
        onView(withId(R.id.dish_list_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition(2));

        //Perform Recyclerview click on item at position
        onView(withId(R.id.dish_list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Check if intent (RecipeActivity to RecipeDetailsActivity) has RECIPE_INTENT_EXTRA
        intended(hasExtraWithKey(ConstantsUtil.SELECTED_DISH_KEY));
    }
}
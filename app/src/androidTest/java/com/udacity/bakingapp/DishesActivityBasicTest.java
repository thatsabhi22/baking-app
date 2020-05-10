package com.udacity.bakingapp;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.udacity.bakingapp.ui.DishesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DishesActivityBasicTest {

    @Rule
    public ActivityTestRule<DishesActivity> mDishActivityTestRule =
            new ActivityTestRule<>(DishesActivity.class);

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

}

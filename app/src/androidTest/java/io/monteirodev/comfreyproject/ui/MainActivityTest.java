package io.monteirodev.comfreyproject.ui;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.monteirodev.comfreyproject.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.monteirodev.comfreyproject.TestUtils.atPosition;
import static io.monteirodev.comfreyproject.TestUtils.getString;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void firstItem_isPlants() {

        String PLANTS = getString(R.string.plants);
        onView(withId(R.id.menu_recycler_view))
                .check(matches(atPosition(0, hasDescendant(withText(PLANTS)))));
    }

    @Test
    public void secondItem_isRecipes() {

        String RECIPES = getString(R.string.recipes);
        onView(withId(R.id.menu_recycler_view))
                .perform(scrollToPosition(1))
                .check(matches(atPosition(1, hasDescendant(withText(RECIPES)))));
    }

    @Test
    public void thirdItem_isGetInvolved() {

        String GET_INVOLVED = getString(R.string.get_involved);
        onView(withId(R.id.menu_recycler_view))
                .perform(scrollToPosition(2))
                .check(matches(atPosition(2, hasDescendant(withText(GET_INVOLVED)))));
    }

    @Test
    public void fourthItem_isAbout() {

        String ABOUT = getString(R.string.about);
        onView(withId(R.id.menu_recycler_view))
                .perform(scrollToPosition(3))
                .check(matches(atPosition(3, hasDescendant(withText(ABOUT)))));
    }
}

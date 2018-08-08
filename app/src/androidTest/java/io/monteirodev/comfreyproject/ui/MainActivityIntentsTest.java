package io.monteirodev.comfreyproject.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.ui.about.AboutActivity;
import io.monteirodev.comfreyproject.ui.getInvolved.GetInvolvedActivity;
import io.monteirodev.comfreyproject.ui.plants.PlantsActivity;
import io.monteirodev.comfreyproject.ui.recipes.RecipesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static io.monteirodev.comfreyproject.TestUtils.getString;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityIntentsTest {

    @Rule
    public IntentsTestRule<MainActivity> mIntentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void clickPlants_opensPlantsActivity() {
        String PLANTS = getString(R.string.plants);

        onView(withId(R.id.menu_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(PLANTS)), click()));
        intended(hasComponent(PlantsActivity.class.getName()));
    }

    @Test
    public void clickRecipes_opensRecipesActivity() {
        String RECIPES = getString(R.string.recipes);

        onView(withId(R.id.menu_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(RECIPES)), click()));
        intended(hasComponent(RecipesActivity.class.getName()));
    }

    @Test
    public void clickGetInvolved_opensGetInvolvedActivity() {
        String GET_INVOLVED = getString(R.string.get_involved);

        onView(withId(R.id.menu_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(GET_INVOLVED)), click()));
        intended(hasComponent(GetInvolvedActivity.class.getName()));
    }

    @Test
    public void clickAbout_opensAboutActivity() {
        String ABOUT = getString(R.string.about);


        onView(withId(R.id.menu_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(ABOUT)), click()));
        intended(hasComponent(AboutActivity.class.getName()));
    }
}

package io.monteirodev.comfreyproject;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;

public class TestUtils {

    @NonNull
    public static String getString(int stringId) {
        Resources res = getInstrumentation().getTargetContext().getResources();
        return res.getString(stringId);
    }

    /** https://stackoverflow.com/a/34795431/1329854 */
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}

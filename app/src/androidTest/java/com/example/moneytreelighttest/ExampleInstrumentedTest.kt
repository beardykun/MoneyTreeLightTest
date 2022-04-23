package com.example.moneytreelighttest

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.moneytreelighttest.accounts.AccountsAdapter
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.moneytreelighttest", appContext.packageName)
    }

    @Test
    fun openTransactionsFragment() {
        if (!recyclerViewSizeMatcher(0).matches(0))
            onView(withId(R.id.rvAccounts)).perform(
                RecyclerViewActions.actionOnItemAtPosition<AccountsAdapter.AccountsViewHolder>(
                    0,
                    click()
                )
            )
    }

    private fun recyclerViewSizeMatcher(matcherSize: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return matcherSize == recyclerView.adapter?.itemCount
            }

            override fun describeTo(description: org.hamcrest.Description?) {
                description?.appendText("with list size: $matcherSize")
            }
        }
    }
}
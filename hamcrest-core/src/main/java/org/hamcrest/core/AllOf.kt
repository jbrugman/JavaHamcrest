package org.hamcrest.core

import org.hamcrest.Description
import org.hamcrest.DiagnosingMatcher
import org.hamcrest.Matcher

import java.util.Arrays

/**
 * Calculates the logical conjunction of multiple matchers. Evaluation is shortcut, so
 * subsequent matchers are not called if an earlier matcher returns `false`.
 */
class AllOf<T>(private val matchers: Iterable<Matcher<in T>>) : DiagnosingMatcher<T>() {

    public override fun matches(actual: Any?, mismatch: Description): Boolean {
        for (matcher in matchers) {
            if (!matcher.matches(actual)) {
                mismatch.appendDescriptionOf(matcher).appendText(" ")
                matcher.describeMismatch(actual, mismatch)
                return false
            }
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendList("(", " " + "and" + " ", ")", matchers)
    }

    companion object {
        /**
         * Creates a matcher that matches if the examined object matches **ALL** of the specified matchers.
         * For example:
         * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
         */
        @JvmStatic
        fun <T> allOf(matchers: Iterable<Matcher<in T>>): Matcher<T> = AllOf(matchers)

        /**
         * Creates a matcher that matches if the examined object matches **ALL** of the specified matchers.
         * For example:
         * <pre>assertThat("myValue", allOf(startsWith("my"), containsString("Val")))</pre>
         */
        @JvmStatic
        @SafeVarargs
        fun <T> allOf(vararg matchers: Matcher<in T>): Matcher<T> = allOf(Arrays.asList(*matchers))
    }
}

fun <T> allOf(matchers: Iterable<Matcher<in T>>): Matcher<T> = AllOf(matchers)

@SafeVarargs
fun <T> allOf(vararg matchers: Matcher<in T>): Matcher<T> = allOf(Arrays.asList(*matchers))
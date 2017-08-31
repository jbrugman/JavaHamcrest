package org.hamcrest.core

import org.hamcrest.Description
import org.hamcrest.Matcher

import java.util.Arrays

/**
 * Calculates the logical disjunction of multiple matchers. Evaluation is shortcut, so
 * subsequent matchers are not called if an earlier matcher returns `true`.
 */
class AnyOf<T>(matchers: Iterable<Matcher<in T>>) : ShortcutCombination<T>(matchers) {

    override fun matches(actual: Any?): Boolean = matches(actual, true)


    override fun describeTo(description: Description) {
        describeTo(description, "or")
    }

    companion object {

        /**
         * Creates a matcher that matches if the examined object matches **ANY** of the specified matchers.
         * For example:
         * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
         */
        @JvmStatic
        fun <T> anyOf(matchers: Iterable<Matcher<in T>>): AnyOf<T> = AnyOf(matchers)


        /**
         * Creates a matcher that matches if the examined object matches **ANY** of the specified matchers.
         * For example:
         * <pre>assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))</pre>
         */
        @JvmStatic
        @SafeVarargs
        fun <T> anyOf(vararg matchers: Matcher<in T>): AnyOf<T> = anyOf(Arrays.asList(*matchers))

    }
}

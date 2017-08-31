package org.hamcrest.core

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

import org.hamcrest.core.IsEqual.Companion.equalTo


/**
 * Calculates the logical negation of a matcher.
 */
class IsNot<T>(private val matcher: Matcher<T>) : BaseMatcher<T>() {

    override fun matches(actual: Any?): Boolean = !matcher.matches(actual)

    override fun describeTo(description: Description) {
        description.appendText("not ").appendDescriptionOf(matcher)
    }

    companion object {

        /**
         * Creates a matcher that wraps an existing matcher, but inverts the logic by which
         * it will match.
         * For example:
         * <pre>assertThat(cheese, is(not(equalTo(smelly))))</pre>
         *
         * @param matcher
         * the matcher whose sense should be inverted
         */
        @JvmStatic
        fun <T> not(matcher: Matcher<T>): Matcher<T> = IsNot(matcher)

        /**
         * A shortcut to the frequently used `not(equalTo(x))`.
         * For example:
         * <pre>assertThat(cheese, is(not(smelly)))</pre>
         * instead of:
         * <pre>assertThat(cheese, is(not(equalTo(smelly))))</pre>
         *
         * @param value
         * the value that any examined object should **not** equal
         */
        @JvmStatic
        fun <T> not(value: T): Matcher<T> =  not(equalTo(value))
    }
}

package org.hamcrest.`object`

import org.hamcrest.FeatureMatcher
import org.hamcrest.Matcher

import org.hamcrest.core.IsEqual.Companion.equalTo

class HasToString<T>(toStringMatcher: Matcher<in String>) : FeatureMatcher<T, String>(toStringMatcher, "with toString()", "toString()") {

    override fun featureValueOf(actual: T) = actual.toString()

    companion object {

        /**
         * Creates a matcher that matches any examined object whose `toString` method
         * returns a value that satisfies the specified matcher.
         * For example:
         * <pre>assertThat(true, hasToString(equalTo("TRUE")))</pre>
         *
         * @param toStringMatcher
         * the matcher used to verify the toString result
         */
        @JvmStatic
        fun <T> hasToString(toStringMatcher: Matcher<in String>): Matcher<T> =
            HasToString(toStringMatcher)

        /**
         * Creates a matcher that matches any examined object whose `toString` method
         * returns a value equalTo the specified string.
         * For example:
         * <pre>assertThat(true, hasToString("TRUE"))</pre>
         *
         * @param expectedToString
         * the expected toString result
         */
        @JvmStatic
        fun <T> hasToString(expectedToString: String): Matcher<T> =
            HasToString(equalTo<Any>(expectedToString))
    }
}

fun <T> hasToString(toStringMatcher: Matcher<in String>): Matcher<T> =
    HasToString(toStringMatcher)

fun <T> hasToString(expectedToString: String): Matcher<T> =
    HasToString(equalTo<Any>(expectedToString))

package org.hamcrest.core

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


/**
 * Is the value null?
 */
class IsNull<T> : BaseMatcher<T>() {
    override fun matches(actual: Any?): Boolean = actual == null

    override fun describeTo(description: Description) {
        description.appendText("null")
    }

    companion object {

        /**
         * Creates a matcher that matches if examined object is `null`.
         * For example:
         * <pre>assertThat(cheese, is(nullValue())</pre>
         *
         */
        @JvmStatic
        fun nullValue(): Matcher<Any>  = IsNull()

        /**
         * A shortcut to the frequently used `not(nullValue())`.
         * For example:
         * <pre>assertThat(cheese, is(notNullValue()))</pre>
         * instead of:
         * <pre>assertThat(cheese, is(not(nullValue())))</pre>
         *
         */
        @JvmStatic
        fun notNullValue(): Matcher<Any> {
            return org.hamcrest.core.IsNot.not<Any>(nullValue())
        }

        /**
         * Creates a matcher that matches if examined object is `null`. Accepts a
         * single dummy argument to facilitate type inference.
         * For example:
         * <pre>assertThat(cheese, is(nullValue(Cheese.class))</pre>
         *
         * @param type
         * dummy parameter used to infer the generic type of the returned matcher
         */
        @JvmStatic
        fun <T> nullValue(@Suppress("UNUSED_PARAMETER") type: Class<T>): Matcher<T> {
            return IsNull()
        }

        /**
         * A shortcut to the frequently used `not(nullValue(X.class)). Accepts a
         * single dummy argument to facilitate type inference.`.
         * For example:
         * <pre>assertThat(cheese, is(notNullValue(X.class)))</pre>
         * instead of:
         * <pre>assertThat(cheese, is(not(nullValue(X.class))))</pre>
         *
         * @param type
         * dummy parameter used to infer the generic type of the returned matcher
         */
        @JvmStatic
        fun <T> notNullValue(type: Class<T>): Matcher<T> {
            return org.hamcrest.core.IsNot.not<T>(nullValue(type))
        }
    }
}


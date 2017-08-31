package org.hamcrest.core

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

import org.hamcrest.core.IsEqual.Companion.equalTo

/**
 * Decorates another Matcher, retaining the behaviour but allowing tests
 * to be slightly more expressive.
 *
 * For example:  assertThat(cheese, equalTo(smelly))
 * vs.  assertThat(cheese, is(equalTo(smelly)))
 */
class Is<T>(private val matcher: Matcher<T>) : BaseMatcher<T>() {

    override fun matches(actual: Any?) = matcher.matches(actual)

    override fun describeTo(description: Description) {
        description.appendText("is ").appendDescriptionOf(matcher)
    }

    override fun describeMismatch(actual: Any?, mismatchDescription: Description) {
        matcher.describeMismatch(actual, mismatchDescription)
    }

    companion object {

        /**
         * Decorates another Matcher, retaining its behaviour, but allowing tests
         * to be slightly more expressive.
         * For example:
         * <pre>assertThat(cheese, is(equalTo(smelly)))</pre>
         * instead of:
         * <pre>assertThat(cheese, equalTo(smelly))</pre>
         *
         */
        @JvmStatic
        fun <T> `is`(matcher: Matcher<T>): Matcher<T> {
            return Is(matcher)
        }

        /**
         * A shortcut to the frequently used `is(equalTo(x))`.
         * For example:
         * <pre>assertThat(cheese, is(smelly))</pre>
         * instead of:
         * <pre>assertThat(cheese, is(equalTo(smelly)))</pre>
         *
         */
        @JvmStatic
        fun <T> `is`(value: T): Matcher<T> {
            return `is`(equalTo(value))
        }

        /**
         * A shortcut to the frequently used `is(instanceOf(SomeClass.class))`.
         * For example:
         * <pre>assertThat(cheese, isA(Cheddar.class))</pre>
         * instead of:
         * <pre>assertThat(cheese, is(instanceOf(Cheddar.class)))</pre>
         *
         */
        @JvmStatic
        fun <T> isA(type: Class<*>): Matcher<T> {
            return `is`(IsInstanceOf.instanceOf(type))
        }
    }
}

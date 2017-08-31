package org.hamcrest.core

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeDiagnosingMatcher

import java.util.ArrayList

class CombinableMatcher<T>(private val matcher: Matcher<in T>) : TypeSafeDiagnosingMatcher<T>() {

    override fun matchesSafely(item: T, mismatch: Description): Boolean {
        if (!matcher.matches(item)) {
            matcher.describeMismatch(item, mismatch)
            return false
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendDescriptionOf(matcher)
    }

    fun and(other: Matcher<in T>): CombinableMatcher<T> {
        return CombinableMatcher(AllOf(templatedListWith(other)))
    }

    fun or(other: Matcher<in T>): CombinableMatcher<T> {
        return CombinableMatcher(AnyOf(templatedListWith(other)))
    }

    private fun templatedListWith(other: Matcher<in T>): ArrayList<Matcher<in T>> {
        val matchers = ArrayList<Matcher<in T>>()
        matchers.add(matcher)
        matchers.add(other)
        return matchers
    }

    class CombinableBothMatcher<X>(private val first: Matcher<in X>) {
        fun and(other: Matcher<in X>): CombinableMatcher<X> {
            return CombinableMatcher(first).and(other)
        }
    }

    class CombinableEitherMatcher<X>(private val first: Matcher<in X>) {
        fun or(other: Matcher<in X>): CombinableMatcher<X> {
            return CombinableMatcher(first).or(other)
        }
    }

    companion object {

        /**
         * Creates a matcher that matches when both of the specified matchers match the examined object.
         * For example:
         * <pre>assertThat("fab", both(containsString("a")).and(containsString("b")))</pre>
         */
        @JvmStatic
        fun <LHS> both(matcher: Matcher<in LHS>): CombinableBothMatcher<LHS> = CombinableBothMatcher(matcher)

        /**
         * Creates a matcher that matches when either of the specified matchers match the examined object.
         * For example:
         * <pre>assertThat("fan", either(containsString("a")).or(containsString("b")))</pre>
         */
        @JvmStatic
        fun <LHS> either(matcher: Matcher<in LHS>): CombinableEitherMatcher<LHS> = CombinableEitherMatcher(matcher)

    }
}

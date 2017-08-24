package org.hamcrest.core

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeDiagnosingMatcher

class Every<T>(private val matcher: Matcher<in T>) : TypeSafeDiagnosingMatcher<Iterable<T>>() {

    public override fun matchesSafely(actual: Iterable<T>, mismatchDescription: Description): Boolean =
        actual.findMismatch()?.let { mismatch ->
            mismatchDescription.appendText("an item ")
            matcher.describeMismatch(mismatch, mismatchDescription)
            false
        } ?: true

    override fun describeTo(description: Description) {
        description.appendText("every item is ").appendDescriptionOf(matcher)
    }

    private fun Iterable<T>.findMismatch() = find { !matcher.matches(it) }

    companion object {

        /**
         * Creates a matcher for [Iterable]s that only matches when a single pass over the
         * examined [Iterable] yields items that are all matched by the specified
         * `itemMatcher`.
         * For example:
         * <pre>assertThat(Arrays.asList("bar", "baz"), everyItem(startsWith("ba")))</pre>
         *
         * @param itemMatcher
         * the matcher to apply to every item provided by the examined [Iterable]
         */
        @JvmStatic
        fun <U> everyItem(itemMatcher: Matcher<U>): Matcher<Iterable<@JvmWildcard U>> {
            return Every(itemMatcher)
        }
    }
}

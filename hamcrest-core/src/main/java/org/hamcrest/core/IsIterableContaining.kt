package org.hamcrest.core

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeDiagnosingMatcher

import java.util.ArrayList

import org.hamcrest.core.AllOf
import org.hamcrest.core.IsEqual.Companion.equalTo

class IsIterableContaining<T>(private val elementMatcher: Matcher<out T>) : TypeSafeDiagnosingMatcher<Iterable<T>>() {

    override fun matchesSafely(collection: Iterable<T>, mismatchDescription: Description): Boolean {
        if (isEmpty(collection)) {
            mismatchDescription.appendText("was empty")
            return false
        }

        for (item in collection) {
            if (elementMatcher.matches(item)) {
                return true
            }
        }

        mismatchDescription.appendText("mismatches were: [")
        var isPastFirst = false
        for (item in collection) {
            if (isPastFirst) {
                mismatchDescription.appendText(", ")
            }
            elementMatcher.describeMismatch(item, mismatchDescription)
            isPastFirst = true
        }
        mismatchDescription.appendText("]")
        return false
    }

    private fun isEmpty(iterable: Iterable<T>): Boolean {
        return !iterable.iterator().hasNext()
    }

    override fun describeTo(description: Description) {
        description
                .appendText("a collection containing ")
                .appendDescriptionOf(elementMatcher)
    }

    companion object {


        /**
         * Creates a matcher for [Iterable]s that only matches when a single pass over the
         * examined [Iterable] yields at least one item that is matched by the specified
         * `itemMatcher`.  Whilst matching, the traversal of the examined [Iterable]
         * will stop as soon as a matching item is found.
         * For example:
         * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem(startsWith("ba")))</pre>
         *
         * @param itemMatcher
         * the matcher to apply to items provided by the examined [Iterable]
         */
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T> hasItem(itemMatcher: Matcher<in T>): Matcher<Iterable<T>> =
                IsIterableContaining(itemMatcher) as Matcher<Iterable<T>>

        /**
         * Creates a matcher for [Iterable]s that only matches when a single pass over the
         * examined [Iterable] yields at least one item that is equal to the specified
         * `item`.  Whilst matching, the traversal of the examined [Iterable]
         * will stop as soon as a matching item is found.
         * For example:
         * <pre>assertThat(Arrays.asList("foo", "bar"), hasItem("bar"))</pre>
         *
         * @param item
         * the item to compare against the items provided by the examined [Iterable]
         */
        @JvmStatic
        fun <T> hasItem(item: T): Matcher<Iterable<T>> = IsIterableContaining(equalTo(item))

        /**
         * Creates a matcher for [Iterable]s that matches when consecutive passes over the
         * examined [Iterable] yield at least one item that is matched by the corresponding
         * matcher from the specified `itemMatchers`.  Whilst matching, each traversal of
         * the examined [Iterable] will stop as soon as a matching item is found.
         * For example:
         * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems(endsWith("z"), endsWith("o")))</pre>
         *
         * @param itemMatchers
         * the matchers to apply to items provided by the examined [Iterable]
         */
        @JvmStatic
        @SafeVarargs
        fun <T> hasItems(vararg itemMatchers: Matcher<in T>): Matcher<Iterable<T>> {
            val all = ArrayList<Matcher<in Iterable<T>>>(itemMatchers.size)

            for (elementMatcher in itemMatchers) {
                // Doesn't forward to hasItem() method so compiler can sort out generics.
                all.add(IsIterableContaining(elementMatcher) as Matcher<in Iterable<T>>)
            }

            return allOf(all)
        }

        /**
         * Creates a matcher for [Iterable]s that matches when consecutive passes over the
         * examined [Iterable] yield at least one item that is equal to the corresponding
         * item from the specified `items`.  Whilst matching, each traversal of the
         * examined [Iterable] will stop as soon as a matching item is found.
         * For example:
         * <pre>assertThat(Arrays.asList("foo", "bar", "baz"), hasItems("baz", "foo"))</pre>
         *
         * @param items
         * the items to compare against the items provided by the examined [Iterable]
         */
        @JvmStatic
        @SafeVarargs
        fun <T> hasItems(vararg items: T): Matcher<Iterable<T>> {
            val all = ArrayList<Matcher<in Iterable<T>>>(items.size)
            items.forEach { all.add(hasItem(it)) }
            return allOf(all)
        }
    }

}
@Suppress("UNCHECKED_CAST")
fun <T> hasItem(itemMatcher: Matcher<in T>): Matcher<Iterable<T>> =  IsIterableContaining(itemMatcher) as Matcher<Iterable<T>>

fun <T> hasItem(item: T): Matcher<Iterable<T>> = IsIterableContaining(equalTo(item))

@SafeVarargs
fun <T> hasItems(vararg itemMatchers: Matcher<in T>): Matcher<Iterable<T>> {
    val all = ArrayList<Matcher<in Iterable<T>>>(itemMatchers.size)

    for (elementMatcher in itemMatchers) {
        // Doesn't forward to hasItem() method so compiler can sort out generics.
        all.add(IsIterableContaining(elementMatcher) as Matcher<in Iterable<T>>)
    }

    return allOf(all)
}
@SafeVarargs
fun <T> hasItems(vararg items: T): Matcher<Iterable<T>> {
    val all = ArrayList<Matcher<in Iterable<T>>>(items.size)
    items.forEach { all.add(IsIterableContaining.hasItem(it)) }
    return allOf(all)
}
package org.hamcrest.collection

import org.hamcrest.FeatureMatcher
import org.hamcrest.Matcher

import org.hamcrest.core.IsEqual.Companion.equalTo

class IsIterableWithSize<E>(sizeMatcher: Matcher<in Int>) : FeatureMatcher<Iterable<E>, Int>(sizeMatcher, "an iterable with size", "iterable size") {


    override fun featureValueOf(actual: Iterable<E>) = actual.count()

    companion object {

        /**
         * Creates a matcher for [Iterable]s that matches when a single pass over the
         * examined [Iterable] yields an item count that satisfies the specified
         * matcher.
         * For example:
         * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(equalTo(2)))</pre>
         *
         * @param sizeMatcher
         * a matcher for the number of items that should be yielded by an examined [Iterable]
         */
        @JvmStatic
        fun <E> iterableWithSize(sizeMatcher: Matcher<in Int>): Matcher<Iterable<E>> =
            IsIterableWithSize(sizeMatcher)

        /**
         * Creates a matcher for [Iterable]s that matches when a single pass over the
         * examined [Iterable] yields an item count that is equal to the specified
         * `size` argument.
         * For example:
         * <pre>assertThat(Arrays.asList("foo", "bar"), iterableWithSize(2))</pre>
         *
         * @param size
         * the number of items that should be yielded by an examined [Iterable]
         */
        @JvmStatic
        fun <E> iterableWithSize(size: Int): Matcher<Iterable<E>> = iterableWithSize(equalTo<Any>(size))
    }
}

fun <E> iterableWithSize(sizeMatcher: Matcher<in Int>): Matcher<Iterable<E>> =
    IsIterableWithSize(sizeMatcher)
fun <E> iterableWithSize(size: Int): Matcher<Iterable<E>> = iterableWithSize(equalTo<Any>(size))

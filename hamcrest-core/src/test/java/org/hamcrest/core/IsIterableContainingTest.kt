package org.hamcrest.core

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeDiagnosingMatcher
import org.junit.Test

import java.util.ArrayList
import java.util.HashSet

import java.util.Arrays.asList
import org.hamcrest.AbstractMatcherTest.*
import org.hamcrest.core.IsEqual.Companion.equalTo

class IsIterableContainingTest {

    @Test
    fun copesWithNullsAndUnknownTypes() {
        val matcher = hasItem<String>(equalTo("irrelevant"))

        assertNullSafe(matcher)
        assertUnknownTypeSafe(matcher)
    }

    @Test
    fun matchesACollectionThatContainsAnElementForTheGivenMatcher() {
        val itemMatcher = hasItem(equalTo("a"))

        assertMatches<Iterable<String>>("list containing 'a'", itemMatcher, asList("a", "b", "c"))
    }

    @Test
    fun doesNotMatchCollectionWithoutAnElementForGivenMatcher() {
        val matcher = hasItem(mismatchable("a"))

        assertMismatchDescription("mismatches were: [mismatched: b, mismatched: c]", matcher, asList("b", "c"))
        assertMismatchDescription("was empty", matcher, ArrayList<String>())
    }

    @Test
    fun doesNotMatchNull() {
        assertDoesNotMatch<Iterable<String>>("doesn't match null", hasItem<String>(equalTo("a")), null)
    }

    @Test
    fun hasAReadableDescription() {
        assertDescription("a collection containing mismatchable: a", hasItem<String>(mismatchable("a")))
    }

    @Test
    fun canMatchItemWhenCollectionHoldsSuperclass() { // Issue 24
        val s = HashSet<Number>()
        s.add(2)

        assertMatches(IsIterableContaining(IsEqual<Number>(2)), s)
        assertMatches(hasItem<Number>(2), s)
    }

    @Test
    fun matchesMultipleItemsInCollection() {
        val matcher1 = hasItems<String>(equalTo("a"), equalTo("b"), equalTo("c"))
        assertMatches<Iterable<String>>("list containing all items", matcher1, asList("a", "b", "c"))

        val matcher2 = hasItems<String>("a", "b", "c")
        assertMatches<Iterable<String>>("list containing all items (without matchers)", matcher2, asList("a", "b", "c"))

        val matcher3 = hasItems<String>(equalTo("a"), equalTo("b"), equalTo("c"))
        assertMatches<Iterable<String>>("list containing all items in any order", matcher3, asList("c", "b", "a"))

        val matcher4 = hasItems<String>(equalTo("a"), equalTo("b"), equalTo("c"))
        assertMatches<Iterable<String>>("list containing all items plus others", matcher4, asList("e", "c", "b", "a", "d"))

        val matcher5 = hasItems<String>(equalTo("a"), equalTo("b"), equalTo("c"))
        assertDoesNotMatch("not match list unless it contains all items", matcher5, asList("e", "c", "b", "d")) // 'a' missing
    }

    @Test
    fun reportsMismatchWithAReadableDescriptionForMultipleItems() {
        val matcher = hasItems<Int>(3, 4)

        assertMismatchDescription("a collection containing <4> mismatches were: [was <1>, was <2>, was <3>]",
                matcher, asList(1, 2, 3))
    }

    private fun mismatchable(string: String): Matcher<in String> {
        return object : TypeSafeDiagnosingMatcher<String>() {
            override fun matchesSafely(item: String, mismatchDescription: Description): Boolean {
                if (string == item)
                    return true

                mismatchDescription.appendText("mismatched: " + item)
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("mismatchable: " + string)
            }
        }
    }
}


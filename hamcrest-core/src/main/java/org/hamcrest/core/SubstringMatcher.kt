package org.hamcrest.core

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

abstract class SubstringMatcher protected constructor(
    private val relationship: String,
    private val ignoringCase: Boolean,
    protected val substring: String
) : TypeSafeMatcher<String>() {
    public override fun matchesSafely(item: String): Boolean =
        evalSubstringOf(if (ignoringCase) item.toLowerCase() else item)

    public override fun describeMismatchSafely(item: String, mismatchDescription: Description) {
        mismatchDescription
            .appendText("was \"")
            .appendText(item)
            .appendText("\"")
    }

    override fun describeTo(description: Description) {
        description.appendText("a string ")
            .appendText(relationship)
            .appendText(" ")
            .appendValue(substring)
        if (ignoringCase) {
            description.appendText(" ignoring case")
        }
    }

    protected fun converted(arg: String) =
        if (ignoringCase) arg.toLowerCase() else arg

    protected abstract fun evalSubstringOf(string: String): Boolean

}

package org.hamcrest.`object`

import org.hamcrest.Description
import org.hamcrest.DiagnosingMatcher
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeDiagnosingMatcher
import org.hamcrest.core.IsEqual

import java.lang.reflect.Field

import java.lang.String.format

class HasEqualValues<T : Any>(private val expectedObject: T) : TypeSafeDiagnosingMatcher<T>(expectedObject.javaClass) {
    private val fieldMatchers: List<FieldMatcher>

    init {
        this.fieldMatchers = fieldMatchers(expectedObject)
    }

    override fun matchesSafely(actual: T, mismatchDescription: Description) =
        fieldMatchers.all { it.matches(actual, mismatchDescription) }

    override fun describeTo(description: Description) {
        description.appendText(expectedObject.javaClass.simpleName)
            .appendText(" has values ")
            .appendList("[", ", ", "]", fieldMatchers)
    }

    private class FieldMatcher(private val field: Field, expectedObject: Any) : DiagnosingMatcher<Any>() {
        private val matcher: Matcher<Any?>

        init {
            this.matcher = IsEqual.equalTo(uncheckedGet(field, expectedObject))
        }

        public override fun matches(item: Any, mismatch: Description): Boolean =
            uncheckedGet(field, item).let {
                return if (matcher.matches(it)) {
                    true
                } else {
                    mismatch.appendText("'").appendText(field.name).appendText("' ")
                    matcher.describeMismatch(it, mismatch)
                    false
                }
            }

        override fun describeTo(description: Description) {
            description.appendText(field.name)
                .appendText(": ")
                .appendDescriptionOf(matcher)
        }
    }

    companion object {
        private fun fieldMatchers(expectedObject: Any): List<FieldMatcher> =
            expectedObject.javaClass.fields.map { FieldMatcher(it, expectedObject) }

        private fun uncheckedGet(field: Field, item: Any): Any? {
            try {
                return field.get(item)
            } catch (e: Exception) {
                throw AssertionError(format("IllegalAccess, reading field '%s' from %s", field.name, item))
            }
        }
    }
}

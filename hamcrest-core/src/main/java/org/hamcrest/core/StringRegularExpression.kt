/**
 *
 */
package org.hamcrest.core

import java.util.regex.Pattern

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeDiagnosingMatcher

/**
 * @author borettim
 * @author sf105
 */
class StringRegularExpression protected constructor(private val pattern: Pattern) : TypeSafeDiagnosingMatcher<String>() {

    override fun describeTo(description: Description) {
        description.appendText("a string matching the pattern ").appendValue(pattern)
    }

    override fun matchesSafely(actual: String, mismatchDescription: Description): Boolean {
        if (!pattern.matcher(actual).matches()) {
            mismatchDescription.appendText("the string was ").appendValue(actual)
            return false
        }
        return true
    }

    companion object {

        /**
         * Validate a string with a [java.util.regex.Pattern].
         *
         * <pre>
         * assertThat(&quot;abc&quot;, matchesRegex(Pattern.compile(&quot;[a-z]$&quot;));
        </pre> *
         *
         * @param pattern
         * the pattern to be used.
         * @return The matcher.
         */
        @JvmStatic
        fun matchesRegex(pattern: Pattern): Matcher<String> {
            return StringRegularExpression(pattern)
        }

        /**
         * Validate a string with a regex.
         *
         * <pre>
         * assertThat(&quot;abc&quot;, matchesRegex(&quot;[a-z]+$&quot;));
        </pre> *
         *
         * @param regex
         * The regex to be used for the validation.
         * @return The matcher.
         */
        @JvmStatic
        fun matchesRegex(regex: String): Matcher<String> {
            return matchesRegex(Pattern.compile(regex))
        }
    }
}

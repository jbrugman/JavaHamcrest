package org.hamcrest.core

import org.hamcrest.Matcher

/**
 * Tests if the argument is a string that contains a substring.
 */
class StringEndsWith(ignoringCase: Boolean, substring: String) : SubstringMatcher("ending with", ignoringCase, substring) {

    override fun evalSubstringOf(string: String): Boolean =  converted(string).endsWith(converted(substring))


    companion object {

        /**
         * Creates a matcher that matches if the examined [String] ends with the specified
         * [String].
         * For example:
         * <pre>assertThat("myStringOfNote", endsWith("Note"))</pre>
         *
         * @param suffix
         * the substring that the returned matcher will expect at the end of any examined string
         */
        @JvmStatic
        fun endsWith(suffix: String): Matcher<String> = StringEndsWith(false, suffix)

        /**
         * Creates a matcher that matches if the examined [String] ends with the specified
         * [String], ignoring case.
         * For example:
         * <pre>assertThat("myStringOfNote", endsWithIgnoringCase("note"))</pre>
         *
         * @param suffix
         * the substring that the returned matcher will expect at the end of any examined string
         */
        @JvmStatic
        fun endsWithIgnoringCase(suffix: String): Matcher<String> = StringEndsWith(true, suffix)

    }

}

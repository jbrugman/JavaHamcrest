package org.hamcrest.core

/**
 * Tests if the argument is a string that contains a substring.
 */
class StringContains(ignoringCase: Boolean, substring: String) : SubstringMatcher("containing", ignoringCase, substring) {

    override fun evalSubstringOf(string: String) =
        converted(string).contains(converted(substring))

    companion object {

        /**
         * Creates a matcher that matches if the examined [String] contains the specified
         * [String] anywhere.
         * For example:
         * <pre>assertThat("myStringOfNote", containsString("ring"))</pre>
         *
         * @param substring
         * the substring that the returned matcher will expect to find within any examined string
         */
        @JvmStatic
        fun containsString(substring: String) =
            StringContains(false, substring)

        /**
         * Creates a matcher that matches if the examined [String] contains the specified
         * [String] anywhere, ignoring case.
         * For example:
         * <pre>assertThat("myStringOfNote", containsStringIgnoringCase("Ring"))</pre>
         *
         * @param substring
         * the substring that the returned matcher will expect to find within any examined string
         */
        @JvmStatic
        fun containsStringIgnoringCase(substring: String) =
            StringContains(true, substring)
    }

}

fun containsString(substring: String) =
    StringContains(false, substring)

fun containsStringIgnoringCase(substring: String) =
    StringContains(true, substring)
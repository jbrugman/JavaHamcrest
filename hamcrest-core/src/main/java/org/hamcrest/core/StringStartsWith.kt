package org.hamcrest.core

/**
 * Tests if the argument is a string that contains a substring.
 */
class StringStartsWith(ignoringCase: Boolean, substring: String) : SubstringMatcher("starting with", ignoringCase, substring) {

    override fun evalSubstringOf(string: String) =
        converted(string).startsWith(converted(substring))

    companion object {

        /**
         *
         *
         * Creates a matcher that matches if the examined [String] starts with the specified
         * [String].
         *
         * For example:
         * <pre>assertThat("myStringOfNote", startsWith("my"))</pre>
         *
         * @param prefix
         * the substring that the returned matcher will expect at the start of any examined string
         */
        @JvmStatic
        fun startsWith(prefix: String) = StringStartsWith(false, prefix)

        /**
         *
         *
         * Creates a matcher that matches if the examined [String] starts with the specified
         * [String], ignoring case
         *
         * For example:
         * <pre>assertThat("myStringOfNote", startsWithIgnoringCase("My"))</pre>
         *
         * @param prefix
         * the substring that the returned matcher will expect at the start of any examined string
         */
        @JvmStatic
        fun startsWithIgnoringCase(prefix: String) = StringStartsWith(true, prefix)
    }

}

fun startsWith(prefix: String) = StringStartsWith(false, prefix)
fun startsWithIgnoringCase(prefix: String) = StringStartsWith(true, prefix)

package org.hamcrest.core

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


/**
 * A matcher that always returns `true`.
 */
class IsAnything<T> @JvmOverloads constructor(private val message: String = "ANYTHING") : BaseMatcher<T>() {

    override fun matches(actual: Any?): Boolean = true


    override fun describeTo(description: Description) {
        description.appendText(message)
    }

    companion object {

        /**
         * Creates a matcher that always matches, regardless of the examined object.
         */
        @JvmStatic
        fun anything(): Matcher<Any> = IsAnything()

        /**
         * Creates a matcher that always matches, regardless of the examined object, but describes
         * itself with the specified [String].
         *
         * @param description
         * a meaningful [String] used when describing itself
         */
        @JvmStatic
        fun anything(description: String): Matcher<Any> = IsAnything(description)

    }
}

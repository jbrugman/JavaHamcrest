package org.hamcrest.core

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


/**
 * Is the value the same object as another value?
 */
class IsSame<T>(private val `object`: T) : BaseMatcher<T>() {

    override fun matches(actual: Any?): Boolean = actual === `object`

    override fun describeTo(description: Description) {
        description.appendText("sameInstance(")
                .appendValue(`object`)
                .appendText(")")
    }

    companion object {

        /**
         * Creates a matcher that matches only when the examined object is the same instance as
         * the specified target object.
         *
         * @param target
         * the target instance against which others should be assessed
         */
        @JvmStatic
        fun <T> sameInstance(target: T): Matcher<T> =  IsSame(target)

        /**
         * Creates a matcher that matches only when the examined object is the same instance as
         * the specified target object.
         *
         * @param target
         * the target instance against which others should be assessed
         */
        @JvmStatic
        fun <T> theInstance(target: T): Matcher<T> = IsSame(target)
    }
}

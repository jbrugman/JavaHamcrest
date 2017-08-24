package org.hamcrest.`object`

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class IsCompatibleType<T>(private val type: Class<T>) : TypeSafeMatcher<Class<*>>() {

    public override fun matchesSafely(item: Class<*>) = type.isAssignableFrom(item)

    public override fun describeMismatchSafely(cls: Class<*>, mismatchDescription: Description) {
        mismatchDescription.appendValue(cls.name)
    }

    override fun describeTo(description: Description) {
        description.appendText("type < ").appendText(type.name)
    }

    companion object {

        /**
         * Creates a matcher of [Class] that matches when the specified baseType is
         * assignable from the examined class.
         * For example:
         * <pre>assertThat(Integer.class, typeCompatibleWith(Number.class))</pre>
         *
         * @param baseType
         * the base class to examine classes against
         */
        @JvmStatic
        fun <T> typeCompatibleWith(baseType: Class<T>): Matcher<Class<*>> =
            IsCompatibleType(baseType)
    }
}

fun <T> typeCompatibleWith(baseType: Class<T>): Matcher<Class<*>> =
    IsCompatibleType(baseType)

package org.hamcrest.core

import org.hamcrest.Description
import org.hamcrest.DiagnosingMatcher
import org.hamcrest.Matcher


/**
 * Tests whether the value is an instance of a class.
 * Classes of basic types will be converted to the relevant "Object" classes
 */
class IsInstanceOf
/**
 * Creates a new instance of IsInstanceOf
 *
 * @param expectedClass The predicate evaluates to true for instances of this class
 * or one of its subclasses.
 */
(private val expectedClass: Class<*>) : DiagnosingMatcher<Any>() {
    private val matchableClass: Class<*>

    init {
        this.matchableClass = matchableClass(expectedClass)
    }

    override fun matches(item: Any?, mismatch: Description): Boolean {
        if (null == item) {
            mismatch.appendText("null")
            return false
        }

        if (!matchableClass.isInstance(item)) {
            mismatch.appendValue(item).appendText(" is a " + item.javaClass.name)
            return false
        }

        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("an instance of ").appendText(expectedClass.name)
    }

    companion object {
        @JvmStatic
        private fun matchableClass(expectedClass: Class<*>): Class<*> {
            if (Boolean::class.javaPrimitiveType == expectedClass) return Boolean::class.javaObjectType
            if (Byte::class.javaPrimitiveType == expectedClass) return Byte::class.javaObjectType
            if (Char::class.javaPrimitiveType == expectedClass) return Char::class.javaObjectType
            if (Double::class.javaPrimitiveType == expectedClass) return Double::class.javaObjectType
            if (Float::class.javaPrimitiveType == expectedClass) return Float::class.javaObjectType
            if (Int::class.javaPrimitiveType == expectedClass) return Int::class.javaObjectType
            if (Long::class.javaPrimitiveType == expectedClass) return Long::class.javaObjectType
            return if (Short::class.javaPrimitiveType == expectedClass) Short::class.javaObjectType else expectedClass
        }

        /**
         * Creates a matcher that matches when the examined object is an instance of the specified `type`,
         * as determined by calling the [java.lang.Class.isInstance] method on that type, passing the
         * the examined object.
         *
         *
         * The created matcher assumes no relationship between specified type and the examined object.
         * For example:
         * <pre>assertThat(new Canoe(), instanceOf(Paddlable.class));</pre>
         *
         */
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T> instanceOf(type: Class<*>): Matcher<T> = IsInstanceOf(type) as Matcher<T>


        /**
         * Creates a matcher that matches when the examined object is an instance of the specified `type`,
         * as determined by calling the [java.lang.Class.isInstance] method on that type, passing the
         * the examined object.
         *
         *
         * The created matcher forces a relationship between specified type and the examined object, and should be
         * used when it is necessary to make generics conform, for example in the JMock clause
         * `with(any(Thing.class))`
         * For example:
         * <pre>assertThat(new Canoe(), instanceOf(Canoe.class));</pre>
         *
         */
        @JvmStatic
        @Suppress("UNCHECKED_CAST")
        fun <T> any(type: Class<T>): Matcher<T> = IsInstanceOf(type) as Matcher<T>
    }

}
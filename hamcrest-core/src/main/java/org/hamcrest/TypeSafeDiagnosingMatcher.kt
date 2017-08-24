package org.hamcrest

import org.hamcrest.internal.ReflectiveTypeFinder


/**
 * Convenient base class for Matchers that require a non-null value of a specific type
 * and that will report why the received value has been rejected.
 * This implements the null check, checks the type and then casts.
 * To use, implement <pre>matchesSafely()</pre>.
 *
 * @param <T>
 * @author Neil Dunn
 * @author Nat Pryce
 * @author Steve Freeman
</T> */
abstract class TypeSafeDiagnosingMatcher<T> : BaseMatcher<T> {

    companion object {
        private val TYPE_FINDER = ReflectiveTypeFinder("matchesSafely", 2, 0)
    }

    private val expectedType: Class<*>

    /**
     * Subclasses should implement this. The item will already have been checked
     * for the specific type and will never be null.
     */
    protected abstract fun matchesSafely(actual: T, mismatchDescription: Description): Boolean

    /**
     * Use this constructor if the subclass that implements `matchesSafely`
     * is *not* the class that binds &lt;T&gt; to a type.
     * @param expectedType The expectedType of the actual value.
     */
    protected constructor(expectedType: Class<*>) {
        this.expectedType = expectedType
    }

    /**
     * Use this constructor if the subclass that implements `matchesSafely`
     * is *not* the class that binds &lt;T&gt; to a type.
     * @param typeFinder A type finder to extract the type
     */
    @JvmOverloads protected constructor(typeFinder: ReflectiveTypeFinder = TYPE_FINDER) {
        expectedType = typeFinder.findExpectedType(javaClass)
    }

    @Suppress("UNCHECKED_CAST")
    override fun matches(actual: Any?) =
        actual != null
            && expectedType.isInstance(actual)
            && matchesSafely(actual as T, Description.NullDescription())

    override fun describeMismatch(actual: Any?, mismatchDescription: Description) {
        when {
            actual == null ->
                mismatchDescription.appendText("was null")
            !expectedType.isInstance(actual) ->
                mismatchDescription.appendText("was ")
                    .appendText(actual.javaClass.simpleName)
                    .appendText(" ")
                    .appendValue(actual)
            else ->
                @Suppress("UNCHECKED_CAST")
                matchesSafely(actual as T, mismatchDescription)
        }
    }
}

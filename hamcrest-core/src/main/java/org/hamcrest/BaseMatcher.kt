package org.hamcrest

/**
 * BaseClass for all Matcher implementations.
 *
 * @see Matcher
 */
abstract class BaseMatcher<T> : Matcher<T> {

    /**
     * @see Matcher._dont_implement_Matcher___instead_extend_BaseMatcher_
     */
    @Deprecated("")
    override fun _dont_implement_Matcher___instead_extend_BaseMatcher_() {
        // See Matcher interface for an explanation of this method.
    }

    override fun describeMismatch(actual: Any?, mismatchDescription: Description) {
        mismatchDescription.appendText("was ").appendValue(actual)
    }

    override fun toString(): String = StringDescription.toString(this)

    companion object {

        /**
         * Useful null-check method. Writes a mismatch description if the actual object is null
         * @param actual the object to check
         * @param mismatch where to write the mismatch description, if any
         * @return false iff the actual object is null
         */
        @JvmStatic
        protected fun isNotNull(actual: Any?, mismatch: Description) =
            if (actual == null) {
                mismatch.appendText("was null")
                false
            } else {
                true
            }
    }
}

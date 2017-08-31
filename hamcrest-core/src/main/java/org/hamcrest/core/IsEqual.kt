package org.hamcrest.core

import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import java.lang.reflect.Array


/**
 * Is the value equal to another value, as tested by the
 * [java.lang.Object.equals] invokedMethod?
 */
open class IsEqual<T>(private val equalArg: T?) : BaseMatcher<T>() {

    override fun matches(actual: Any?) = areEqual(actual, equalArg)

    override fun describeTo(description: Description) {
        description.appendValue(equalArg)
    }

    companion object {

        /**
         * Creates a matcher that matches when the examined object is logically equal to the specified
         * `operand`, as determined by calling the [java.lang.Object.equals] method on
         * the **examined** object.
         *
         *
         * If the specified operand is `null` then the created matcher will only match if
         * the examined object's `equals` method returns `true` when passed a
         * `null` (which would be a violation of the `equals` contract), unless the
         * examined object itself is `null`, in which case the matcher will return a positive
         * match.
         *
         *
         * The created matcher provides a special behaviour when examining `Array`s, whereby
         * it will match if both the operand and the examined object are arrays of the same length and
         * contain items that are equal to each other (according to the above rules) **in the same
         * indexes**.
         * For example:
         * <pre>
         * assertThat("foo", equalTo("foo"));
         * assertThat(new String[] {"foo", "bar"}, equalTo(new String[] {"foo", "bar"}));
        </pre> *
         *
         */
        @JvmStatic
        fun <T> equalTo(operand: T) = IsEqual(operand)

        /**
         * Creates an [org.hamcrest.core.IsEqual] matcher that does not enforce the values being
         * compared to be of the same static type.
         */
        @JvmStatic
        fun equalToObject(operand: Any)= IsEqual(operand)

    }
}

private fun areEqual(actual: Any?, expected: Any?): Boolean =
        if (actual.isArray() && expected.isArray()) {
            expected.isArray() && areArraysEqual(actual!!, expected!!)
        } else {
            actual == expected
        }

private fun areArraysEqual(actualArray: Any, expectedArray: Any) =
        areArrayLengthsEqual(actualArray, expectedArray) && areArrayElementsEqual(actualArray, expectedArray)

private fun areArrayLengthsEqual(actualArray: Any, expectedArray: Any) =
        actualArray.arrayLength() == expectedArray.arrayLength()

private fun areArrayElementsEqual(actualArray: Any, expectedArray: Any) =
        (0 until actualArray.arrayLength()).all {
            areEqual(actualArray[it], expectedArray[it])
        }

private fun Any?.isArray() = this?.javaClass?.isArray ?: false
private fun Any.arrayLength() = Array.getLength(this)
private operator fun Any.get(index: Int) = Array.get(this, index)
fun <T> equalTo(operand: T) = IsEqual(operand)
fun equalToObject(operand: Any) = IsEqual(operand)
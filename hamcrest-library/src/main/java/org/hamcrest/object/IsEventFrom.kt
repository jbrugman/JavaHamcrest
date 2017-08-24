package org.hamcrest.`object`

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeDiagnosingMatcher

import java.util.EventObject


/**
 * Tests if the value is an event announced by a specific object.
 */
class IsEventFrom(private val eventClass: Class<*>, private val source: Any?) : TypeSafeDiagnosingMatcher<EventObject>() {

    public override fun matchesSafely(actual: EventObject, mismatchDescription: Description) = when {
        actual.isNotSameClass() -> {
            mismatchDescription.appendText("item type was ${actual.javaClass.name}")
            false
        }
        actual.hasDifferentSource() -> {
            mismatchDescription.appendText("source was ").appendValue(actual.source)
            false
        }
        else -> true
    }

    private fun EventObject.isNotSameClass() = !eventClass.isInstance(this)

    private fun EventObject.hasDifferentSource() = source !== this@IsEventFrom.source

    override fun describeTo(description: Description) {
        description.appendText("an event of type ")
            .appendText(eventClass.name)
            .appendText(" from ")
            .appendValue(source)
    }

    companion object {

        /**
         * Creates a matcher of [java.util.EventObject] that matches any object
         * derived from <var>eventClass</var> announced by <var>source</var>.
         * For example:
         * <pre>assertThat(myEvent, is(eventFrom(PropertyChangeEvent.class, myBean)))</pre>
         *
         * @param eventClass
         * the class of the event to match on
         * @param source
         * the source of the event
         */
        @JvmStatic
        fun eventFrom(eventClass: Class<out EventObject>, source: Any?): Matcher<EventObject> =
            IsEventFrom(eventClass, source)

        /**
         * Creates a matcher of [java.util.EventObject] that matches any EventObject
         * announced by <var>source</var>.
         * For example:
         * <pre>assertThat(myEvent, is(eventFrom(myBean)))</pre>
         *
         * @param source
         * the source of the event
         */
        @JvmStatic
        fun eventFrom(source: Any?): Matcher<EventObject> =
            eventFrom(EventObject::class.java, source)
    }
}

package es.iessaladillo.pedrojoya.stroop.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

}

inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner, crossinline onEventUnhandledContent: (T) -> Unit
) {
    observe(owner, Observer { it.getContentIfNotHandled()?.let(onEventUnhandledContent) })
}
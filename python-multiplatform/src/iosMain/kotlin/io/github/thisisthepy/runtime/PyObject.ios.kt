package io.github.thisisthepy.runtime

import co.touchlab.stately.collections.ConcurrentMutableMap
import kotlin.native.ref.WeakReference
import kotlin.experimental.ExperimentalNativeApi


/*
 * Python Object Cache
 */
@OptIn(ExperimentalNativeApi::class)
private val pyobjCache: ConcurrentMutableMap<Long, WeakReference<PyObject>> = ConcurrentMutableMap()


/*
 * Obtain a reference to a specific pointer address from the object cache
 */
@OptIn(ExperimentalNativeApi::class)
fun PyObject.getInstance(pointer: Long): PyObject? {
    if (pointer == 0L) return null
    val wr: WeakReference<PyObject>? = pyobjCache[pointer]
    if (wr != null) {
        // wr.get() returns null if the PyObject is unreachable, but it has not yet been removed from the cache.
        val obj: PyObject? = wr.get()
        if (obj != null) return obj
    }
    val obj = PyObject(pointer)
    pyobjCache[pointer] = WeakReference(obj)
    return obj
}

/*
 * Delete PyObject from the object cache
 */
@OptIn(ExperimentalNativeApi::class)
fun PyObject.destroyInstance() {
    if (this.pointer == 0L) return
    val wr: WeakReference<PyObject>? = pyobjCache.remove(pointer)
    if (wr != null) {
        val obj: PyObject? = wr.get()
        if (obj != null && obj !== this) {
            pyobjCache[pointer] = wr
        }
    }
    this.pointer = 0
}

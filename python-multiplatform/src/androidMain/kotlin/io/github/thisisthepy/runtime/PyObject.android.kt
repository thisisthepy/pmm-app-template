package io.github.thisisthepy.runtime

import java.lang.ref.WeakReference


/*
 * Python Object Cache
 */
private val pyobjCache: MutableMap<Long, WeakReference<PyObject>> = HashMap()


/*
 * Obtain a reference to a specific pointer address from the object cache
 */
fun PyObject.getInstance(pointer: Long): PyObject? {
    if (pointer == 0L) return null
    synchronized(pyobjCache) {
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
}

/*
 * Delete PyObject from the object cache
 */
fun PyObject.destroyInstance() {
    if (this.pointer == 0L) return
    synchronized(pyobjCache) {
        val wr: WeakReference<PyObject>? = pyobjCache.remove(pointer)
        if (wr != null) {
            val obj: PyObject? = wr.get()
            if (obj != null && obj !== this) {
                pyobjCache[pointer] = wr
            }
        }
    }
    this.pointer = 0
}

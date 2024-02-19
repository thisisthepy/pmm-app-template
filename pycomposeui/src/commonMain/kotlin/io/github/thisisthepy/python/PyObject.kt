package io.github.thisisthepy.python


expect class WeakReference<T : Any>(referred: T) {
    fun clear()
    fun get(): T?
}

class PyObject private constructor(val address: Long) {
    companion object {
        private val cache: MutableMap<Long, WeakReference<PyObject>> = HashMap()

        internal fun notifyPythonKilled() {
            cache.clear()
        }

        fun fromAddress(address: Long): PyObject? {
            if (address == 0L) return null
            val ref: WeakReference<PyObject>? = cache[address]
            if (ref != null) {
                val obj: PyObject? = ref.get()
                if (obj != null) {
                    return obj
                } else {  // ref is null when the address is already garbage collected but not removed from cache.
                    cache.remove(address)
                }
            }
            val obj = PyObject(address)
            cache[address] = WeakReference(obj)
            return obj
        }
    }
}

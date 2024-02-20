package io.github.thisisthepy.python


expect class WeakReference<T : Any>(referred: T) {
    fun clear()
    fun get(): T?
}

class PyObject private constructor(val address: Long) {
    companion object {
        private val cache: MutableMap<Long, WeakReference<PyObject>> = HashMap()

        private var _nativeFFI: Python.PyFFI? = null

        internal fun notifyPythonStarted(nativeFFI: Python.PyFFI) {
            _nativeFFI = nativeFFI
        }

        internal fun notifyPythonKilled() {
            cache.clear()
            _nativeFFI = null
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

    fun getAttribute(attr: String): PyObject? {
        return fromAddress(_nativeFFI!!.pyObjectGetAttrString(this.address, attr))
    }

    fun call(): PyObject? {
        return fromAddress(_nativeFFI!!.pyObjectCallObject(this.address, 0L))
    }
}

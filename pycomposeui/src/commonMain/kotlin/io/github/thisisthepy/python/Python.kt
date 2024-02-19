package io.github.thisisthepy.python


object Python {
    class PyFFI(
        val pyInitialize: () -> Unit = { },
        val pyFinalize: () -> Unit = { },
        val pyImport: (String) -> Long = { _ -> 0 },
        val pyObjectGetAttrString: (Long, String) -> Long = { _, _ -> 0 },
        val pyObjectCallObject: (Long, Long) -> Long = { _, _ -> 0 }
    )

    private var _nativeFFI: PyFFI? = null

    val isStarted: Boolean
        get() = _nativeFFI != null

    fun start(nativeFFI: PyFFI) {
        if (isStarted) {
            throw IllegalStateException("ERROR: Python interpreter is already started.")
        } else {
            try {
                nativeFFI.pyInitialize()
                _nativeFFI = nativeFFI
                println("INFO: Python interpreter is started.")
            } catch (e: Throwable) {
                e.printStackTrace()
                throw IllegalStateException("ERROR: Something went wrong during initialization of Python interpreter.")
            }
        }
    }

    fun kill() {
        if (_nativeFFI != null) {
            _nativeFFI!!.pyFinalize()
            _nativeFFI = null
            PyObject.notifyPythonKilled()
        }
    }

    fun import(module: String): PyObject {
        if (_nativeFFI != null) {
            val pModule = PyObject.fromAddress(_nativeFFI!!.pyImport(module))
            if (pModule != null) {
                return pModule
            } else {
                throw ModuleNotFoundException("ERROR: Could not find Python module named $module.")
            }
        } else {
            throw IllegalStateException("ERROR: Python interpreter is not running.")
        }
    }

    val builtins: PyObject
        get() = import("builtins")

    val os: PyObject
        get() = import("os")

    val sys: PyObject
        get() = import("sys")

    //val version = sys

}
open class ModuleNotFoundException: NoSuchElementException {
    constructor()
    constructor(message: String?)
}

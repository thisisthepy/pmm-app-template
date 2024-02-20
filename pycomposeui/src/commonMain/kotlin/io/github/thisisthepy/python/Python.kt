package io.github.thisisthepy.python

import io.github.thisisthepy.python.PyObject.Companion.fromAddress


object Python {
    class PyFFI(
        val pyInitialize: () -> Unit = { },
        val pyFinalize: () -> Unit = { },
        val pyImport: (String) -> Long = { _ -> 0 },
        val pyErrorOccurred: () -> Boolean = { false },
        val pyErrorPrint: () -> Unit = { },
        val pyObjectGetAttrString: (Long, String) -> Long = { _, _ -> 0 },
        val pyObjectCallObject: (Long, Long) -> Long = { _, _ -> 0 }
    )

    private var _nativeFFI: PyFFI? = null
        set(nativeFFI) {
            field = nativeFFI
            if (nativeFFI == null) {
                PyObject.notifyPythonKilled()
            } else {
                PyObject.notifyPythonStarted(nativeFFI)
            }
        }

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
        }
    }

    fun import(module: String): PyObject {
        if (_nativeFFI != null) {
            val pModule = fromAddress(_nativeFFI!!.pyImport(module))
            if (pModule != null) {
                return pModule
            } else {
                if (_nativeFFI!!.pyErrorOccurred()) {
                    println("ERROR: Python Error occurred.")
                }
                _nativeFFI!!.pyErrorPrint()
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

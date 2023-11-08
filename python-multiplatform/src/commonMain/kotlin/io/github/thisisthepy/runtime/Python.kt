package io.github.thisisthepy.runtime


/*
 * Python Interpreter
 */
object Python {
    var platform: Platform
    var

    /**
     *
     */
    fun getModule(name: String): PyObject {
        return requireNonNull(PyObject.getInstance(getModulePointerByName(name)))
    }
}

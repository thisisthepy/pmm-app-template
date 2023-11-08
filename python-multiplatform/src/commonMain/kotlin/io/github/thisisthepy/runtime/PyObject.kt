package io.github.thisisthepy.runtime


/*
 * Obtain a reference to a specific pointer address from the object cache
 */
expect fun PyObject.getInstance(pointer: Long): PyObject?

/*
 * Delete PyObject from the object cache
 */
expect fun PyObject.destroyInstance()


/*
 * Python Object Binding
 */
class PyObject(var pointer: Long) {


    fun call(vararg args: Object?): PyObject? {
        return try {
            callThrows(*args)
        } catch (e: PyException) {
            throw e
        } catch (e: Throwable) {
            throw PyException(e)
        }
    }

    fun toBoolean(): Boolean

    fun toByte(): Byte

    fun toChar(): Char

    fun toShort(): Short

    fun toInt(): Int

    fun toLong(): Long

    fun toFloat(): Float

    fun toDouble(): Double

    fun toString(): String

    fun asList(): List<PyObject>

    fun asMap(): Map<PyObject, PyObject>

    fun asSet(): Set<PyObject>

    fun hasAttr(key: String): Boolean

    fun setAttr(key: String, value: PyObject)

    @throws IllegalStateException
    fun removeAttr(key: String)

    fun repr(): String

    fun is(compareWith: PyObject): Boolean

    fun equals(other: PyObject): Boolean

    fun type(): PyType

    fun isNone(): Boolean

    fun isTrue(): Boolean

    fun isFalse(): Boolean

    fun size(): Int

    fun fromKt(obj: Objects): PyObject

    fun toKt(): Objects

    @Override
    protected fun finalize() {

    }
}

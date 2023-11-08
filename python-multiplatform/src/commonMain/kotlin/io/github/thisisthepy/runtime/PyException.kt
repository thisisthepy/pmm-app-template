package io.github.thisisthepy.runtime


/*
 * Python Exception Class
 */
class PyException: RuntimeException {
    constructor()
    constructor(s: String?) : super(s)
    constructor(s: String?, throwable: Throwable?) : super(s, throwable)
    constructor(throwable: Throwable?) : super(throwable)
}

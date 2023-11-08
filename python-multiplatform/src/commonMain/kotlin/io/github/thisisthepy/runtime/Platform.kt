package io.github.thisisthepy.runtime


/*
 * Platform Object containing target system's information
 */
expect object Platform() {
    fun getModulePointerByName(): Long
}

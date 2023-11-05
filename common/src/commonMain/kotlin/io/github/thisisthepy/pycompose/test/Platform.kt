package io.github.thisisthepy.pycompose.test

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
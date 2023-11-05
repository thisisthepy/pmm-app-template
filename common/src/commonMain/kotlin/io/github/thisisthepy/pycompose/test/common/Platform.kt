package io.github.thisisthepy.pycompose.test.common

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform